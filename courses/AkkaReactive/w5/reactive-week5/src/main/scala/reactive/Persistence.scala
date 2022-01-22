package reactive

import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.actor.typed.scaladsl._
import akka.persistence.typed.scaladsl._
import akka.persistence.typed.{PersistenceId, RecoveryCompleted}
import akka.persistence.typed.scaladsl.EventSourcedBehavior.CommandHandler

object PersistenceDemo {
    sealed trait Ledger
    case class Debit(account: String, amount: BigDecimal, replyTo: ActorRef[Result]) extends Ledger
    case class Credit(account: String, amount: BigDecimal, replyTo: ActorRef[Result]) extends Ledger

    sealed trait Result
    case object Success extends Result
    case object Failure extends Result

    sealed trait Command
    case object DebitSuccess extends Command
    case object DebitFailure extends Command
    case object CreditSuccess extends Command
    case object CreditFailure extends Command
    case object Stop extends Command

    case class TransferConfig(ledger: ActorRef[Ledger], result: ActorRef[Result], amount: BigDecimal, from: String, to: String)

    sealed trait Event
    case object Aborted extends Event
    case object DebitDone extends Event
    case object CreditDone extends Event
    case object RollbackStarted extends Event
    case object RollbackFailed extends Event
    case object RollbackFinished extends Event

    sealed trait State
    case class AwaitingDebit(config: TransferConfig) extends State
    case class AwaitingCredit(config: TransferConfig) extends State
    case class AwaitingRollback(config: TransferConfig) extends State
    case class Finished(result: ActorRef[Result]) extends State
    case class Failed(result: ActorRef[Result]) extends State

    val creditResult: Result => Command = {
        case Success => CreditSuccess
        case Failure => CreditFailure
    }

    def adapter[T](ctx: ActorContext[Command], f: T => Command): ActorRef[T] =
        ctx.spawnAnonymous(Behaviors.receiveMessage[T] { msg =>
            ctx.self ! f(msg)
            Behaviors.stopped
        })
    
    def commandHandler(ctx: ActorContext[Command]): CommandHandler[Command, Event, State] =
        (state, command) => state match {
            case _: AwaitingDebit    => awaitingDebit(ctx)(state, command)
            case _: AwaitingCredit   => awaitingCredit(ctx)(state, command)
            case _: AwaitingRollback => awaitingRollback(state, command)
            case _                   => Effect.stop
        }

    val eventHandler: (State, Event) => State = { (state, event) =>
        println(s"in state $state receiving event $event")
        (state, event) match {
            case (AwaitingDebit(tc), DebitDone) => AwaitingCredit(tc)
            case (AwaitingDebit(tc), Aborted) => Failed(tc.result)
            case (AwaitingCredit(tc), CreditDone) => Finished(tc.result)
            case (AwaitingCredit(tc), RollbackStarted) => AwaitingRollback(tc)
            case (AwaitingRollback(tc), RollbackFinished | RollbackFailed) => Failed(tc.result)
            case x => throw new IllegalStateException(x.toString)
        }
    }

    // Note: the `CommandHandler` type shown in the slides has a different signature:
    // it does not anymore provide an `ActorContext`, that’s why we take it as a
    // parameter here, see https://github.com/akka/akka/pull/25654
    def awaitingDebit(ctx: ActorContext[Command]): CommandHandler[Command, Event, State] = {
        case (AwaitingDebit(tc), DebitSuccess) =>
            // Note: the `andThen` method shown in the slides has been
            // renamed to `thenRun`, see https://github.com/akka/akka/pull/25357
            Effect.persist(DebitDone).thenRun { state =>
                tc.ledger ! Credit(tc.to, tc.amount, adapter(ctx, creditResult))
            }
        case (AwaitingDebit(tc), DebitFailure) =>
            Effect.persist(Aborted)
                  .thenRun((state: State) => tc.result ! Failure)
                  .thenStop
        case x => throw new IllegalStateException(x.toString)
    }

    def awaitingCredit(ctx: ActorContext[Command]): CommandHandler[Command, Event, State] = {
        case (AwaitingCredit(tc), CreditSuccess) =>
            Effect.persist(CreditDone)
                  .thenRun((state: State) => tc.result ! Success)
                  .thenStop
        case (AwaitingCredit(tc), CreditFailure) =>
            Effect.persist(RollbackStarted).thenRun { state =>
                tc.ledger ! Credit(tc.from, tc.amount, adapter(ctx, creditResult))
            }
        case x => throw new IllegalStateException(x.toString)
    }

    val awaitingRollback: CommandHandler[Command, Event, State] = {
        case (AwaitingRollback(tc), CreditSuccess) =>
            Effect.persist(RollbackFinished)
                  .thenRun((state: State) => tc.result ! Failure)
                  .thenStop
        case (AwaitingRollback(tc), CreditFailure) =>
            Effect.persist(RollbackFailed)
                  .thenRun((state: State) => tc.result ! Failure)
                  .thenStop
        case x => throw new IllegalStateException(x.toString)
    }

    val happyLedger = Behaviors.receiveMessage[Ledger] {
        case Debit(_, _, replyTo) => replyTo ! Success; Behaviors.same
        case Credit(_, _, replyTo) => replyTo ! Success; Behaviors.same
    }

    def command(config: TransferConfig, ledger: ActorRef[Ledger]) = Behaviors.setup[Command] { ctx =>
        EventSourcedBehavior(
            persistenceId = PersistenceId.ofUniqueId("transfer-1"),
            emptyState = AwaitingDebit(config),
            commandHandler = commandHandler(ctx),
            eventHandler = eventHandler
        )
          // Note: the `onRecoveryCompleted` method has been removed in favor of using
          // `receiveSignal`, see https://github.com/akka/akka/pull/26484
          .receiveSignal {
            case (AwaitingDebit(tc), RecoveryCompleted) =>
                println("resuming debit")
                ledger ! Debit(tc.from, tc.amount, adapter(ctx, { case Success => DebitSuccess case Failure => DebitFailure }))
            case (AwaitingCredit(tc), RecoveryCompleted) =>
                println("resuming credit")
                ledger ! Credit(tc.to, tc.amount, adapter(ctx, creditResult))
            case (AwaitingRollback(tc), RecoveryCompleted) =>
                println("resuming rollback")
                ledger ! Credit(tc.from, tc.amount, adapter(ctx, creditResult))
            case (Finished(result), RecoveryCompleted) =>
                println("still finished")
                ctx.self ! Stop
                result ! Success
            case (Failed(result), RecoveryCompleted) =>
                println("still failed")
                ctx.self ! Stop
                result ! Failure
        }
    }

    def main(args: Array[String]): Unit = {
        ActorSystem(Behaviors.setup[Result] { ctx =>
            val ledger = ctx.spawn(happyLedger, "ledger")
            val config = TransferConfig(ledger, ctx.self, 1000.00, "Alice", "Bob")
            ctx.spawn[Command](command(config, ledger), "transfer")
            Behaviors.receiveMessage { _ =>
                println("saga done")
                Behaviors.stopped
            }
        }, "Persistence")
    }
}