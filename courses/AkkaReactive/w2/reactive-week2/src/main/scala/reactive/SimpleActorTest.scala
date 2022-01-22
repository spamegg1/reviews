package reactive

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestProbe
import scala.concurrent.duration._
import akka.testkit.TestKit
import akka.testkit.ImplicitSender

class Toggle extends Actor {
  def happy: Receive = {
    case "How are you?" ⇒
      sender() ! "happy"
      context become sad
  }
  def sad: Receive = {
    case "How are you?" ⇒
      sender() ! "sad"
      context become happy
  }
  def receive = happy
}

object SimpleActorTest extends App {

  // running a TestProbe from the outside
  implicit val system = ActorSystem("TestSys")
  val toggle = system.actorOf(Props[Toggle])
  val p = TestProbe()
  p.send(toggle, "How are you?")
  p.expectMsg("happy")
  p.send(toggle, "How are you?")
  p.expectMsg("sad")
  p.send(toggle, "unknown")
  p.expectNoMessage(1.second)
  system.terminate()

  // running inside a TestKit
  new TestKit(ActorSystem("TestSys")) with ImplicitSender {
    val toggle = system.actorOf(Props[Toggle])
    toggle ! "How are you?"
    expectMsg("happy")
    toggle ! "How are you?"
    expectMsg("sad")
    toggle ! "unknown"
    expectNoMessage(1.second)
    system.terminate()
  }

  println("done")
  
}