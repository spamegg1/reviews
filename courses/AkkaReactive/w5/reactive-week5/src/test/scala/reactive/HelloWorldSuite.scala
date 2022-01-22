package reactive

import akka.Done
import akka.actor.typed._
import akka.actor.testkit.typed.scaladsl.{BehaviorTestKit, TestInbox}
import org.scalatest.{FunSuite, MustMatchers}

class HelloWorldSuite extends FunSuite with MustMatchers {

  import HelloGuardian._

  test("A Greeter Guardian must create working greeters") {
    val guardianKit = BehaviorTestKit(guardian)

    // prepare reception of new session
    val sessionInbox = TestInbox[ActorRef[Command]]()

    // inject NewGreeter request to observe reaction
    guardianKit.ref ! NewGreeter(sessionInbox.ref)
    guardianKit.runOne()
    val sessionRef = sessionInbox.receiveMessage()
    sessionInbox.hasMessages must be(false)

    // retrieve child actor’s behavior testkit
    val sessionKit = guardianKit.childTestKit(sessionRef)

    // test a greeting
    val doneInbox = TestInbox[Done]()
    sessionRef ! Greet("World", doneInbox.ref)
    sessionKit.runOne()
    doneInbox.receiveAll() must be(Seq(Done))

    // test shutting down the greeter
    sessionRef ! Stop
    sessionKit.runOne()
    sessionKit.isAlive must be(false)

    // test shutting down the guardian
    guardianKit.ref ! Shutdown
    guardianKit.runOne()
    guardianKit.isAlive must be(false)
  }

}
