package kvstore

import akka.testkit.TestProbe

import scala.concurrent.duration.*
import kvstore.Arbiter.{Join, JoinedSecondary}

import scala.util.Random
import scala.util.control.NonFatal

trait Step2_SecondarySpec { this: KVStoreSuite =>

  test("Step2-case1: Secondary (in isolation) should properly register itself to the provided Arbiter") {
    val arbiter = TestProbe()
        system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case1-secondary")
    
    arbiter.expectMsg(Join)
    ()
  }

  test("Step2-case2: Secondary (in isolation) must handle Snapshots") {
    import Replicator.*

    val arbiter = TestProbe()
    val replicator = TestProbe()
        val secondary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case2-secondary")
        val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", None, 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", Some("v1"), 1L))
    replicator.expectMsg(SnapshotAck("k1", 1L))
    assertEquals(client.get("k1"), Some("v1"))

    replicator.send(secondary, Snapshot("k1", None, 2L))
    replicator.expectMsg(SnapshotAck("k1", 2L))
    assertEquals(client.get("k1"), None)
  }

  test("Step2-case3: Secondary should drop and immediately ack snapshots with older sequence numbers") {
    import Replicator.*

    val arbiter = TestProbe()
    val replicator = TestProbe()
        val secondary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case3-secondary")
        val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", Some("v1"), 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), Some("v1"))

    replicator.send(secondary, Snapshot("k1", None, 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), Some("v1"))

    replicator.send(secondary, Snapshot("k1", Some("v2"), 1L))
    replicator.expectMsg(SnapshotAck("k1", 1L))
    assertEquals(client.get("k1"), Some("v2"))

    replicator.send(secondary, Snapshot("k1", None, 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), Some("v2"))
  }

  test("Step2-case4: Secondary should drop snapshots with future sequence numbers") {
    import Replicator.*

    val arbiter = TestProbe()
    val replicator = TestProbe()
        val secondary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case4-secondary")
        val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", Some("v1"), 1L))
    replicator.expectNoMessage(300.milliseconds)
    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", Some("v2"), 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), Some("v2"))
  }

  
}
