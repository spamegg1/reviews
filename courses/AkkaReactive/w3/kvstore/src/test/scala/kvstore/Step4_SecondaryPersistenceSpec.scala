package kvstore

import akka.testkit.TestProbe

import scala.concurrent.duration.*
import Arbiter.*
import Persistence.*

trait Step4_SecondaryPersistenceSpec { this: KVStoreSuite =>

  test("Step4-case1: Secondary should not acknowledge snapshots until persisted") {
    import Replicator.*

    val arbiter = TestProbe()
    val persistence = TestProbe()
    val replicator = TestProbe()
    val secondary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step4-case1-secondary")
    val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", Some("v1"), 0L))
    val persistId = persistence.expectMsgPF() {
      case Persist("k1", Some("v1"), id) => id
    }

    assertEquals(client.get("k1"), Some("v1"), "secondary replica should already serve the received update while waiting for persistence: ")

    replicator.expectNoMessage(500.milliseconds)

    persistence.reply(Persisted("k1", persistId))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), Some("v1"))
  }

  test("Step4-case2: Secondary should retry persistence in every 100 milliseconds") {
    import Replicator.*

    val arbiter = TestProbe()
    val persistence = TestProbe()
    val replicator = TestProbe()
    val secondary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step4-case2-secondary")
    val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(client.get("k1"), None)

    replicator.send(secondary, Snapshot("k1", Some("v1"), 0L))
    val persistId = persistence.expectMsgPF() {
      case Persist("k1", Some("v1"), id) => id
    }

    assertEquals(client.get("k1"), Some("v1"), "secondary replica should already serve the received update while waiting for persistence: ")

    // Persistence should be retried
    persistence.expectMsg(300.milliseconds, Persist("k1", Some("v1"), persistId))
    persistence.expectMsg(300.milliseconds, Persist("k1", Some("v1"), persistId))

    replicator.expectNoMessage(500.milliseconds)

    persistence.reply(Persisted("k1", persistId))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(client.get("k1"), Some("v1"))
  }

}
