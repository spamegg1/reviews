package kvstore

import akka.testkit.TestProbe

import scala.util.Random
import scala.util.control.NonFatal

trait Step1_PrimarySpec { this: KVStoreSuite =>

  import Arbiter.*

  test("Step1-case1: Primary (in isolation) should properly register itself to the provided Arbiter") {
    val arbiter = TestProbe()
        system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step1-case1-primary")
    
    arbiter.expectMsg(Join)
    ()
  }

  test("Step1-case2: Primary (in isolation) should react properly to Insert, Remove, Get") {
    val arbiter = TestProbe()
        val primary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step1-case2-primary")
        val client = session(primary)

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)

    client.getAndVerify("k1")
    client.setAcked("k1", "v1")
    client.getAndVerify("k1")
    client.getAndVerify("k2")
    client.setAcked("k2", "v2")
    client.getAndVerify("k2")
    client.removeAcked("k1")
    client.getAndVerify("k1")
  }

  
}
