package kvstore

import akka.testkit.TestProbe

import scala.concurrent.duration.*
import kvstore.Replicator.{Replicate, Snapshot, SnapshotAck}

trait Step3_ReplicatorSpec { this: KVStoreSuite =>

  test("Step3-case1: Replicator should send snapshots when asked to replicate") {
    val probe = TestProbe()
    val secondary = TestProbe()
    val replicator = system.actorOf(Replicator.props(secondary.ref), "step3-case1-replicator")

    probe.send(replicator, Replicate("k1", Some("v1"), 0L))
    expectAtLeastOneSnapshot(secondary)("k1", Some("v1"), 0L)
    probe.expectMsg(Replicator.Replicated("k1", 0L))

    probe.send(replicator, Replicate("k1", Some("v2"), 1L))
    expectAtLeastOneSnapshot(secondary)("k1", Some("v2"), 1L)
    probe.expectMsg(Replicator.Replicated("k1", 1L))

    probe.send(replicator, Replicate("k2", Some("v1"), 2L))
    expectAtLeastOneSnapshot(secondary)("k2", Some("v1"), 2L)
    probe.expectMsg(Replicator.Replicated("k2", 2L))

    probe.send(replicator, Replicate("k1", None, 3L))
    expectAtLeastOneSnapshot(secondary)("k1", None, 3L)
    probe.expectMsg(Replicator.Replicated("k1", 3L))
  }

  test("Step3-case2: Replicator should retry until acknowledged by secondary") {
    val probe = TestProbe()
    val secondary = TestProbe()
    val replicator = system.actorOf(Replicator.props(secondary.ref), "step3-case2-replicator")

    probe.send(replicator, Replicate("k1", Some("v1"), 0L))
    secondary.expectMsg(Snapshot("k1", Some("v1"), 0L))
    secondary.expectMsg(300.milliseconds, Snapshot("k1", Some("v1"), 0L))
    secondary.expectMsg(300.milliseconds, Snapshot("k1", Some("v1"), 0L))

    secondary.reply(SnapshotAck("k1", 0L))
  }

}
