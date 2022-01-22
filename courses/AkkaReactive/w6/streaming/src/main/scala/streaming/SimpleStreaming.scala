package streaming

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import scala.concurrent.Future

/**
 * Implement the streaming elements that the test-suite will verify
 *
 * Notice that in order to implement all those operations you do not need
 * any reference to an `ActorSystem` or `Materializer`. This is since the Flows / Sinks / Sources are "descriptions",
 * and actual execution happens only later.
 */
object SimpleStreaming extends ExtraStreamOps with SimpleStreamingInterface:

  /** Change each of the streamed elements to their String values */
  def mapToStrings(ints: Source[Int, NotUsed]): Source[String, NotUsed] =
    ???

  /** Filter elements which are even (use the modulo operator: `%`) */
  def filterEvenValues: Flow[Int, Int, NotUsed] =
    ???

  /**
   * Rather than re-using operations as `operation(source): Source`,
   * let's re-use the previously built Flow[Int, Int].
   *
   * Similar to function composition, Flows can be composed thanks to the `via` operator,
   * try to implement this method by composing the previous two.
   */
  def filterUsingPreviousFilterFlowAndMapToStrings(ints: Source[Int, NotUsed]): Source[String, NotUsed] =
    ???

  /**
   * You likely noticed that the `via` composition style reads more nicely since it is possible to read it
   * from left to right the same way the functions will be applied to the elements.
   *
   * Let's now re-use the passed in toStringFlow to re-implement the previous source semantics,
   * however by chaining multiple Flows with each-other.
   */
  def filterUsingPreviousFlowAndMapToStringsUsingTwoVias(ints: Source[Int, NotUsed], toString: Flow[Int, String, _]): Source[String, NotUsed] =
    ???

  /**
   * You can also "trim" a stream, by taking a number of elements (or by predicate).
   * In this method, take the first element only -- the stream should be then completed once the first element has arrived.
   */
  def firstElementSource(ints: Source[Int, NotUsed]): Source[Int, NotUsed] =
    ???

  /**
   * This time we will actually *run* the stream.
   * Hint: This is also the first time we will use an "Materialized Value"; Look at Sink.head's type signature.
   *
   * Notes: Compare the signatures of `run` and `runWith`
   */
  def firstElementFuture(ints: Source[Int, NotUsed])(using Materializer): Future[Int] =
    ???

  // --- failure handling ---

  /**
   * Recover [[IllegalStateException]] values to a -1 value
   */
  def recoverSingleElement(ints: Source[Int, NotUsed]): Source[Int, NotUsed] =
    ???

  /**
   * Recover [[IllegalStateException]] values to the provided fallback Source
   *
   */
  def recoverToAlternateSource(ints: Source[Int, NotUsed], fallback: Source[Int, NotUsed]): Source[Int, NotUsed] =
    ???

  // working with rate

  /**
   * Provide a Flow that will be able to continue receiving elements from its upstream Source
   * and "sum up" values while the downstream (the Sink to which this Flow will be attached).
   *
   * In this way we are able to keep the upstream running it its nominal rate, while accumulating
   * all information. The downstream will then consume the accumulated values also at its nominal rate,
   * which in this case we know / expect to be slower than the upstream.
   *
   * If the downstream would happen to be faster than the upstream, no such aggregation is needed,
   * and the elements can be passed through directly.
   *
   * If you'd like to see the exact events happening you can call `.logAllEvents` on the Flow you are returning here
   */
  def sumUntilBackpressureGoesAway: Flow[Int, Int, _] =
    ???

  /**
   * A faster downstream wants to consume elements, yet the upstream is slow at providing them.
   * Provide a Flow that is able to extrapolate "invent" values by repeating the previously emitted value.
   *
   * This could be seen as a "keepLast", where the stage keeps the last observed value from upstream.
   *
   * If you'd like to see the exact events happening you can call `.logAllEvents` on the Flow you are returning here
   *
   * See also [[Iterator.continually]]
   */
  def keepRepeatingLastObservedValue: Flow[Int, Int, _] =
    ???

