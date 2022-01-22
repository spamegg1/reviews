package reactive

import akka.actor.typed.{ActorSystem, Behavior, ExtensibleBehavior, Signal, SupervisorStrategy, TypedActorContext}
import akka.actor.typed.scaladsl._

object SupervisionDemo {
    val actor = Behaviors.receiveMessage[String] {
        case "okay" => Behaviors.same
        case "divide" => throw new ArithmeticException
        // anything else results in a MatchError
    }

    val supervisor = Behaviors.setup[Any] { ctx =>
        ctx.spawnAnonymous(
            Behaviors.supervise(actor)
                .onFailure[ArithmeticException](SupervisorStrategy.restart))

        Behaviors.empty
    }

    val supervisor2 = Behaviors.setup[String] { ctx =>
        val ref = ctx.spawnAnonymous(actor)
        ctx.watchWith(ref, "actor died")
        Behaviors.receiveMessage {
            case "do it" => // do the thing
                Behaviors.same
            case "actor died" => // deal with it
                Behaviors.stopped
        }
    }

    def supervise[T](behavior: Behavior[T]): Behavior[T] =
        new Restarter(behavior, behavior)

    private class Restarter[T](initial: Behavior[T], behavior: Behavior[T]) extends ExtensibleBehavior[T] {
        def receive(ctx: TypedActorContext[T], msg: T): Behavior[T] = {
            try {
                val started = Behavior.validateAsInitial(Behavior.start(behavior, ctx))
                val next = Behavior.interpretMessage(started, ctx, msg)
                new Restarter(initial, Behavior.canonicalize(next, started, ctx))
            } catch {
                case _: ArithmeticException => new Restarter(initial, Behavior.validateAsInitial(Behavior.start(initial, ctx)))
            }
        }

        def receiveSignal(ctx: TypedActorContext[T], msg: Signal): Behavior[T] =
            Behavior.interpretSignal(behavior, ctx, msg)
    }
}
