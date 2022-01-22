package reactive

import akka.Done
import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.actor.typed.scaladsl._
import akka.actor.typed.receptionist._
import scala.concurrent.duration._

object ReceptionistDemo {
    import HelloGuardian.{ Guardian => Greeter, NewGreeter, Command, Greet, guardian }
    import Receptionist.{ Find, Listing }

    val key = ServiceKey[Greeter]("greeter")

    sealed trait FriendlyCommand
    case class Intro(friend: String) extends FriendlyCommand
    case class SetGreeter(listing: Listing) extends FriendlyCommand

    val friendly = Behaviors.setup[FriendlyCommand] { ctx =>

        val receptionist = ctx.system.receptionist
        val listingRef = ctx.messageAdapter(SetGreeter)
        receptionist ! Find(key, listingRef)

        Behaviors.withStash[FriendlyCommand](100) { buffer =>
            Behaviors.receiveMessage {
                case SetGreeter(key.Listing(refs)) if refs.isEmpty =>
                    ctx.scheduleOnce(3.seconds, receptionist, Find(key, listingRef))
                    Behaviors.same
                case SetGreeter(key.Listing(refs)) =>
                    buffer.unstashAll(friendlyRunning(refs.head))
                case other =>
                    buffer.stash(other)
                    Behaviors.same
            }
        }
    }

    private def friendlyRunning(greeter: ActorRef[Greeter]) = Behaviors.receive[FriendlyCommand] {
        case (ctx, Intro(friend)) =>
            ctx.spawnAnonymous(doGreet(greeter, friend))
            Behaviors.same
        case _ => Behaviors.same
    }

    private def doGreet(greeter: ActorRef[Greeter], friend: String) =
        Behaviors.setup[ActorRef[Command]] { ctx =>
            greeter ! NewGreeter(ctx.self)
            Behaviors.receiveMessage { session =>
                session ! Greet(friend, ctx.system.deadLetters)
                Behaviors.stopped
            }
        }

    def main(args: Array[String]): Unit = {
        ActorSystem(Behaviors.setup[Done] { ctx =>
            val greeter = ctx.spawn(guardian, "greeter")
            ctx.scheduleOnce(1.second, ctx.system.receptionist, Receptionist.Register(key, greeter))
            val friendlyRef = ctx.spawn(friendly, "friendly")

            friendlyRef ! Intro("World")
            friendlyRef ! Intro("someone else")

            ctx.scheduleOnce(10.seconds, ctx.self, Done)

            Behaviors.receiveMessage(_ => Behaviors.stopped)
        }, "Receptionist")
    }
}
