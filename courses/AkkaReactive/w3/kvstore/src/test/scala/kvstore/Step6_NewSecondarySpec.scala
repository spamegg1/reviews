package kvstore

import akka.testkit.TestProbe
import Arbiter.*
import Replicator.*

trait Step6_NewSecondarySpec { this: KVStoreSuite =>

  test("Step6-case1: Primary must start replication to new replicas") {
    val arbiter = TestProbe()
        val primary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step6-case1-primary")
        val user = session(primary)
    val secondary = TestProbe()

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)

    user.setAcked("k1", "v1")
    arbiter.send(primary, Replicas(Set(primary, secondary.ref)))

    expectAtLeastOneSnapshot(secondary)("k1", Some("v1"), 0L)

    val ack1 = user.set("k1", "v2")
    expectAtLeastOneSnapshot(secondary)("k1", Some("v2"), 1L)
    user.waitAck(ack1)

    val ack2 = user.remove("k1")
    expectAtLeastOneSnapshot(secondary)("k1", None, 2L)
    user.waitAck(ack2)
  }

  test("Step6-case2: Primary must stop replication to removed replicas and stop Replicator") {
    val probe = TestProbe()
    val arbiter = TestProbe()
        val primary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step6-case2-primary")
        val user = session(primary)
    val secondary = TestProbe()

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)
    arbiter.send(primary, Replicas(Set(primary, secondary.ref)))

    val ack1 = user.set("k1", "v1")
    secondary.expectMsg(Snapshot("k1", Some("v1"), 0L))
    val replicator = secondary.lastSender
    secondary.reply(SnapshotAck("k1", 0L))
    user.waitAck(ack1)

    probe.watch(replicator)
    arbiter.send(primary, Replicas(Set(primary)))
    probe.expectTerminated(replicator)
    ()
  }

  test("Step6-case3: Primary must stop replication to removed replicas and waive their outstanding acknowledgements") {
    val arbiter = TestProbe()
        val primary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step6-case3-primary")
        val user = session(primary)
    val secondary = TestProbe()

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)
    arbiter.send(primary, Replicas(Set(primary, secondary.ref)))

    val ack1 = user.set("k1", "v1")
    secondary.expectMsg(Snapshot("k1", Some("v1"), 0L))
    secondary.reply(SnapshotAck("k1", 0L))
    user.waitAck(ack1)

    val ack2 = user.set("k1", "v2")
    secondary.expectMsg(Snapshot("k1", Some("v2"), 1L))
    arbiter.send(primary, Replicas(Set(primary)))
    user.waitAck(ack2)
  }

}
