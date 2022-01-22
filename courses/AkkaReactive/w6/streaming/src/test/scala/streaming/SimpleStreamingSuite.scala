package streaming

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.testkit.scaladsl.TestSource
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.*

class SimpleStreamingSuite extends munit.FunSuite:
  // This Impl workaround is necessary because in Scala 3 a lazy val
  // cannot override a val. So we cannot override lazy val system
  // which breaks TestKitBase
  Impl()
  class Impl extends TestKit(ActorSystem("SimpleStreaming")):
    import SimpleStreaming.*

    // Convenient method that blocks until a Future value is completed
    def await[A](futureValue: Future[A]): A =
      Await.result(futureValue, 500.milliseconds)

    val ints: Source[Int, NotUsed] = Source
      .fromIterator(() => Iterator.from(1))
      .take(100) // safety limit, to avoid infinite ones
    def numbers(to: Int): List[Int] = Iterator.from(1).take(to).toList

    test("map simple values to strings") {
      val n = 10

      val tenStrings = mapToStrings(ints).take(n)
      val eventualStrings = tenStrings.runWith(Sink.seq)
      val s = await(eventualStrings)
      assertEquals(s, numbers(n).map(_.toString))
    }

    test("filter even values") {
      val n = 10

      val tenStrings = ints.take(n).via(filterEvenValues)
      val eventualStrings = tenStrings.runWith(Sink.seq)
      val s = await(eventualStrings)
      assertEquals(s, numbers(n).filter(_ % 2 == 0))
    }

    test("provide a Flow that can filter out even numbers") {
      val n = 100

      val it =
        filterUsingPreviousFilterFlowAndMapToStrings(ints).runWith(Sink.seq)
      val s = await(it)
      assertEquals(s, numbers(n).filter(_ % 2 == 0).map(_.toString))
    }

    test(
      "provide a Flow that can filter out even numbers by reusing previous flows"
    ) {
      val n = 100

      val toString = Flow[Int].map(_.toString)
      val it =
        filterUsingPreviousFlowAndMapToStringsUsingTwoVias(ints, toString)
          .runWith(Sink.seq)
      val s = await(it)
      assertEquals(s, numbers(n).filter(_ % 2 == 0).map(_.toString))
    }

    test("firstElementSource should only emit one element") {
      val p = firstElementSource(ints.drop(12)).runWith(TestSink.probe)
      p.ensureSubscription()
      p.request(100)
      p.expectNext(13)
      p.expectComplete()
      p.expectNoMessage(300.millis)
    }

    test("firstElementFuture should only emit one element") {
      val p = await(firstElementFuture(ints.drop(12)))
      assertEquals(p, 13)
    }

    // failure handling

    test("recover from a failure into a backup element") {
      val (p, source) = TestSource.probe[Int](system).preMaterialize()
      val r = recoverSingleElement(source)
      val s = r.runWith(TestSink.probe)

      s.request(10)
      p.ensureSubscription()
      p.expectRequest()

      p.sendNext(1)
      p.sendNext(2)

      s.expectNext(1)
      s.expectNext(2)

      val ex = IllegalStateException("Source failed for some reason, oops!")

      p.sendError(ex)
      s.expectNext(-1)
      s.expectComplete()
    }

    test("recover from a failure into a backup stream") {
      val (p, source) = TestSource.probe[Int](system).preMaterialize()
      val fallback = Source(List(10, 11))
      val r = recoverToAlternateSource(source, fallback)
      val s = r.runWith(TestSink.probe)

      s.request(10)
      p.ensureSubscription()
      p.expectRequest()

      p.sendNext(1)
      p.sendNext(2)

      s.expectNext(1)
      s.expectNext(2)

      val ex = IllegalStateException("Source failed for some reason, oops!")

      p.sendError(ex)
      s.expectNext(10)
        .expectNext(11)
    }

    // working with rate

    test("sum until back-pressure goes away") {
      val (sourceProbe, sinkProbe) =
        TestSource
          .probe[Int]
          .via(sumUntilBackpressureGoesAway)
          .toMat(TestSink.probe)(Keep.both)
          .run()

      sourceProbe.ensureSubscription()
      sourceProbe.expectRequest()

      sourceProbe.sendNext(1)
      sinkProbe.request(1)
      sinkProbe.expectNext(1)

      sourceProbe.sendNext(2)
      sourceProbe.sendNext(3)
      sinkProbe.request(1)
      sinkProbe.expectNext(5)

      sourceProbe.sendComplete()
    }

    test("keep repeating last observed value") {
      val (sourceProbe, sinkProbe) =
        TestSource
          .probe[Int]
          .via(keepRepeatingLastObservedValue)
          .toMat(TestSink.probe)(Keep.both)
          .run()

      sourceProbe.ensureSubscription()
      sourceProbe.expectRequest()

      sourceProbe.sendNext(1)
      sinkProbe.request(1)
      sinkProbe.expectNext(1)
      sinkProbe.request(1)
      sinkProbe.expectNext(1)

      sourceProbe.sendNext(22)
      sinkProbe.request(3)
      sinkProbe.expectNext(22)
      sinkProbe.expectNext(22)
      sinkProbe.expectNext(22)

      sourceProbe.sendComplete()
    }
