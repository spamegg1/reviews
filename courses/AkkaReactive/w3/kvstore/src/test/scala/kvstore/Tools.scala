package kvstore

import akka.actor.ActorSystem

import scala.concurrent.duration.{DurationInt, FiniteDuration}
import akka.testkit.TestProbe
import akka.actor.{Actor, ActorRef}
import akka.actor.Props
import kvstore.Replicator.{Snapshot, SnapshotAck}

object Tools:
  class TestRefWrappingActor(val probe: TestProbe) extends Actor:
    def receive = { case msg => probe.ref forward msg }

/**
 * This is a utility to mix into your tests which provides convenient access
 * to a provided replica. It will keep track of requested updates and allow
 * simple verification. See e.g. Step 1 for how it can be used.
 */
trait Tools:

  import Tools.*

  def probeProps(probe: TestProbe): Props = Props(classOf[TestRefWrappingActor], probe)

  class Session(val probe: TestProbe, val replica: ActorRef):
    import Replica.*

    @volatile private var seq = 0L
    private def nextSeq: Long =
      val next = seq
      seq += 1
      next

    @volatile private var referenceMap = Map.empty[String, String]

    def waitAck(s: Long): Unit =
      probe.expectMsg(OperationAck(s))
      ()

    def waitFailed(s: Long): Unit =
      probe.expectMsg(OperationFailed(s))
      ()

    def set(key: String, value: String): Long =
      referenceMap += key -> value
      val s = nextSeq
      probe.send(replica, Insert(key, value, s))
      s

    def setAcked(key: String, value: String): Unit = waitAck(set(key, value))

    def remove(key: String): Long =
      referenceMap -= key
      val s = nextSeq
      probe.send(replica, Remove(key, s))
      s

    def removeAcked(key: String): Unit = waitAck(remove(key))

    def getAndVerify(key: String): Unit =
      val s = nextSeq
      probe.send(replica, Get(key, s))
      probe.expectMsg(GetResult(key, referenceMap.get(key), s))
      ()

    def get(key: String): Option[String] =
      val s = nextSeq
      probe.send(replica, Get(key, s))
      probe.expectMsgType[GetResult](10.seconds).valueOption

    def nothingHappens(duration: FiniteDuration): Unit = probe.expectNoMessage(duration)

  def session(replica: ActorRef)(using ActorSystem) = Session(TestProbe(), replica)

  /**
    * Assert that at least one `Snapshot` message has been received by `probe` with the given
    * `key`, `value` and `seq` values.
    * Subsequent occurrences of that message are discarded.
    * This method also handles the logic of the `probe` replying to the message with a
    * `SnapshotAck` message.
    */
  def expectAtLeastOneSnapshot(probe: TestProbe)(key: String, value: Option[String], seq: Long): Unit =
    probe.expectMsg(Snapshot(key, value, seq))
    // Ignore subsequent messages
    probe.ignoreMsg({ case Snapshot(_, _, `seq`) => true })
    // Reply before we clear the queue
    probe.reply(SnapshotAck(key, seq))
    // Clear any previously accumulated messages
    probe.receiveWhile() { case Snapshot(_, _, `seq`) => () }
    ()

