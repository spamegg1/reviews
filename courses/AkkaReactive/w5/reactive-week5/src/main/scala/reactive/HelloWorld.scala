package reactive

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior }

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import akka.util.Timeout
import akka.Done

object Hello extends App {
    val greeter: Behavior[String] =
        Behaviors.receiveMessage[String] { whom =>
            println(s"Hello $whom!")
            Behaviors.stopped
        }

    // start a system with this primitive guardian
    val system: ActorSystem[String] = ActorSystem(greeter, "helloworld")

    // send a message to the guardian
    system ! "world"

    // system stops when guardian stops
}

object HelloADT extends App {
    sealed trait Command
    final case class Greet(whom: String) extends Command
    final case object Stop extends Command

    val greeter: Behavior[Command] =
        Behaviors.receiveMessage[Command] {
            case Greet(whom) =>
                println(s"Hello $whom!")
                Behaviors.same
            case Stop =>
                println("shutting down ...")
                Behaviors.stopped
        }

    ActorSystem(Behaviors.setup[Any] { ctx =>
        val greeterRef = ctx.spawn(greeter, "greeter")
        ctx.watch(greeterRef) // sign death pact
    
        greeterRef ! Greet("world")
        greeterRef ! Stop

        Behaviors.empty
    }, "helloworld")
}

object HelloGuardian {
    sealed trait Command
    final case class Greet(whom: String, replyTo: ActorRef[Done]) extends Command
    final case object Stop extends Command

    val greeter: Behavior[Command] =
        Behaviors.receiveMessage[Command] {
            case Greet(whom, replyTo) =>
                println(s"Hello $whom!")
                replyTo ! Done
                Behaviors.same
            case Stop =>
                println("shutting down ...")
                Behaviors.stopped
        }

    sealed trait Guardian
    final case class NewGreeter(replyTo: ActorRef[ActorRef[Command]]) extends Guardian
    final case object Shutdown extends Guardian

    val guardian = Behaviors.receive[Guardian] {
        case (ctx, NewGreeter(replyTo)) =>
            val ref = ctx.spawnAnonymous(greeter)
            replyTo ! ref
            Behaviors.same
        case (_, Shutdown) =>
            Behaviors.stopped
    }

    def main(args: Array[String]): Unit = {
        val system: ActorSystem[Guardian] = ActorSystem(guardian, "helloworld")
    
        import akka.actor.typed.scaladsl.AskPattern._
        implicit val ec = ExecutionContext.global
        implicit val timeout = Timeout(3.seconds)
        implicit val scheduler = system.scheduler
    
        for {
            greeter <- system ? NewGreeter
            _ = greeter ! Greet("world", system.deadLetters)
            another <- system ? NewGreeter
            _ = another ! Greet("there", system.deadLetters)
            _ = another ! Stop
            _ = greeter ! Greet("again", system.deadLetters)
        } system ! Shutdown
    }
}
