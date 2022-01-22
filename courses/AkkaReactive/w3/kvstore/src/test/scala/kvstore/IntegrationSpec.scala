/**
 * Copyright (C) 2013-2015 Typesafe Inc. <http://www.typesafe.com>
 */
package kvstore

import akka.actor.Props
import akka.testkit.TestProbe

trait IntegrationSpec { this: KVStoreSuite =>

  import Arbiter.*

  test("Integration-case1: Primary and secondaries must work in concert when persistence is unreliable (35pts)") {
    integrate(true, false, 1)
  }

  test("Integration-case2: Primary and secondaries must work in concert when communication to secondaries is unreliable (35pts)") {
    integrate(false, true, 2)
  }

  test("Integration-case3: Primary and secondaries must work in concert when both persistence and communication to secondaries are unreliable (35 pts)") {
    integrate(true, true, 3)
  }

  def integrate(flaky: Boolean, lossy: Boolean, nr: Int): Unit =
    val arbiterProbe = TestProbe()
    val arbiter = system.actorOf(Props(classOf[provided.Arbiter], lossy, arbiterProbe.ref))
    val primary = system.actorOf(Replica.props(arbiter, provided.Persistence.props(flaky)), s"integration-case$nr-primary")
    val client1 = session(primary)
    try arbiterProbe.expectMsg(JoinedPrimary)
    catch
      case _: AssertionError => fail("primary replica did not join the Arbiter within 3 seconds")

    val secondary1 = system.actorOf(Replica.props(arbiter, provided.Persistence.props(flaky)), s"integration-case$nr-secondary1")
    val client2 = session(secondary1)

    client1.getAndVerify("k1")
    client1.setAcked("k1", "v1")
    client1.getAndVerify("k1")
    client1.setAcked("k1", "v11")
    client1.getAndVerify("k2")
    client1.setAcked("k2", "v2")
    client1.getAndVerify("k2")

    arbiterProbe.awaitAssert {
      assertEquals(client2.get("k1"), Some("v11"))
      assertEquals(client2.get("k2"), Some("v2"))
    }

    client1.removeAcked("k1")
    client1.getAndVerify("k1")

    arbiterProbe.awaitAssert {
      assertEquals(client2.get("k2"), Some("v2"))
    }

    // Join a replica later
    val secondary2 = system.actorOf(Replica.props(arbiter, provided.Persistence.props(flaky)), s"integration-case$nr-secondary2")
    val client3 = session(secondary2)

    // Wait for replication...
    arbiterProbe.awaitAssert {
      assertEquals(client3.get("k2"), Some("v2"))
      assertEquals(client3.get("k1"), None)
    }

    client1.setAcked("k1", "v111")
    client1.setAcked("k3", "v3")
    client1.removeAcked("k2")

    // Wait for replication...
    arbiterProbe.awaitAssert {
      assertEquals(client2.get("k2"), None)
      assertEquals(client2.get("k1"), Some("v111"))
      assertEquals(client2.get("k3"), Some("v3"))
    }
    arbiterProbe.awaitAssert {
      assertEquals(client3.get("k2"), None)
      assertEquals(client3.get("k1"), Some("v111"))
      assertEquals(client3.get("k3"), Some("v3"))
    }
}
