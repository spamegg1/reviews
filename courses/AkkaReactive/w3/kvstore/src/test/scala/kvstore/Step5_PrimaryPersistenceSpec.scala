package kvstore

import scala.concurrent.duration.*
import akka.testkit.TestProbe
import Arbiter.*
import Persistence.*
import Replicator.*

trait Step5_PrimaryPersistenceSpec { this: KVStoreSuite =>

  test("Step5-case1: Primary does not acknowledge updates which have not been persisted") {
    val arbiter = TestProbe()
    val persistence = TestProbe()
    val primary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step5-case1-primary")
    val client = session(primary)

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)

    val setId = client.set("foo", "bar")
    val persistId = persistence.expectMsgPF() {
      case Persist("foo", Some("bar"), id) => id
    }

    client.nothingHappens(100.milliseconds)
    persistence.reply(Persisted("foo", persistId))
    client.waitAck(setId)
  }

  test("Step5-case2: Primary retries persistence every 100 milliseconds") {
    val arbiter = TestProbe()
    val persistence = TestProbe()
    val primary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step5-case2-primary")
    val client = session(primary)

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)

    val setId = client.set("foo", "bar")
    val persistId = persistence.expectMsgPF() {
      case Persist("foo", Some("bar"), id) => id
    }
    // Retries form above
    persistence.expectMsg(200.milliseconds, Persist("foo", Some("bar"), persistId))
    persistence.expectMsg(200.milliseconds, Persist("foo", Some("bar"), persistId))

    client.nothingHappens(100.milliseconds)
    persistence.reply(Persisted("foo", persistId))
    client.waitAck(setId)
  }

  test("Step5-case3: Primary generates failure after 1 second if persistence fails") {
    val arbiter = TestProbe()
    val persistence = TestProbe()
    val primary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step5-case3-primary")
    val client = session(primary)

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)

    val setId = client.set("foo", "bar")
    persistence.expectMsgType[Persist]
    client.nothingHappens(800.milliseconds) // Should not fail too early
    client.waitFailed(setId)
  }

  test("Step5-case4: Primary generates failure after 1 second if global acknowledgement fails") {
    val arbiter = TestProbe()
        val primary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step5-case4-primary")
        val secondary = TestProbe()
    val client = session(primary)

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)
    arbiter.send(primary, Replicas(Set(primary, secondary.ref)))

    client.probe.within(1.second, 2.seconds) {
      val setId = client.set("foo", "bar")
      secondary.expectMsgType[Snapshot](200.millis)
      client.waitFailed(setId)
    }
  }

  test("Step5-case5: Primary acknowledges only after persistence and global acknowledgement") {
    val arbiter = TestProbe()
        val primary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step5-case5-primary")
        val secondaryA, secondaryB = TestProbe()
    val client = session(primary)

    arbiter.expectMsg(Join)
    arbiter.send(primary, JoinedPrimary)
    arbiter.send(primary, Replicas(Set(primary, secondaryA.ref, secondaryB.ref)))

    val setId = client.set("foo", "bar")
    val seqA = secondaryA.expectMsgType[Snapshot].seq
    val seqB = secondaryB.expectMsgType[Snapshot].seq
    client.nothingHappens(300.milliseconds)
    secondaryA.reply(SnapshotAck("foo", seqA))
    client.nothingHappens(300.milliseconds)
    secondaryB.reply(SnapshotAck("foo", seqB))
    client.waitAck(setId)
  }

}
