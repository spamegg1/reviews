package streaming

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Source}
import scala.concurrent.Future

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait SimpleStreamingInterface:
  def mapToStrings(ints: Source[Int, NotUsed]): Source[String, NotUsed]
  def filterEvenValues: Flow[Int, Int, NotUsed]
  def filterUsingPreviousFilterFlowAndMapToStrings(ints: Source[Int, NotUsed]): Source[String, NotUsed]
  def filterUsingPreviousFlowAndMapToStringsUsingTwoVias(ints: Source[Int, NotUsed], toString: Flow[Int, String, _]): Source[String, NotUsed]
  def firstElementSource(ints: Source[Int, NotUsed]): Source[Int, NotUsed]
  def firstElementFuture(ints: Source[Int, NotUsed])(using Materializer): Future[Int]
  def recoverSingleElement(ints: Source[Int, NotUsed]): Source[Int, NotUsed]
  def recoverToAlternateSource(ints: Source[Int, NotUsed], fallback: Source[Int, NotUsed]): Source[Int, NotUsed]
  def sumUntilBackpressureGoesAway: Flow[Int, Int, _]
  def keepRepeatingLastObservedValue: Flow[Int, Int, _]
