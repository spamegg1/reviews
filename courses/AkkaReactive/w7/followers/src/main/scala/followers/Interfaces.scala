package followers

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString
import followers.model.{Event, Followers, Identity}
import scala.concurrent.Future

// Interfaces used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.

trait ServerInterface:
  def broadcastOut: Source[(Event, Followers), NotUsed]
  def clientFlow(): Flow[ByteString, ByteString, NotUsed]
  def inboundSink: Sink[ByteString, NotUsed]
  def outgoingFlow(userId: Int): Source[ByteString, NotUsed]
  val eventsFlow: Flow[ByteString, Nothing, NotUsed]

trait ServerModuleInterface:
  def reframedFlow: Flow[ByteString, String, NotUsed]
  def eventParserFlow: Flow[ByteString, Event, NotUsed]
  def identityParserSink: Sink[ByteString, Future[Identity]]
  def reintroduceOrdering: Flow[Event, Event, NotUsed]
  def followersFlow: Flow[Event, (Event, Followers), NotUsed]
  def isNotified(userId: Int)(eventAndFollowers: (Event, Followers)): Boolean
