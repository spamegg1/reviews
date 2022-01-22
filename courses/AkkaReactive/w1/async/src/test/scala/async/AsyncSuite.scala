package async

import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise, TimeoutException}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success, Try}

class AsyncSuite extends munit.FunSuite:

  import Async.*

  /**
    * Returns a function that performs an asynchronous
    * computation returning the given result after 50 milliseconds.
    */
  def delay[A](result: Try[A]): () => Future[A] =
    val t = java.util.Timer()
    () => {
      val p = Promise[A]()
      val task = new java.util.TimerTask:
        def run(): Unit =
          p.complete(result)
          ()
      t.schedule(task, 400 /* milliseconds */)
      p.future
    }

  test("transformSuccess should transform successful computations") {
    val x = Random.nextInt()
    val eventuallyResult =
      transformSuccess(Future.successful(x))
    val result =
      Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(result, Success(x % 2 == 0))
  }

  test("transformSuccess should propagate the failure of a failed computation") {
    val failure = Exception("Failed asynchronous computation")
    val eventuallyResult =
      transformSuccess(Future.failed(failure))
    val result =
      Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(result, Failure(failure))
  }

  test("recoverFailure should recover from failed computations") {
    val eventuallyResult =
      recoverFailure(Future.failed(Exception()))
    val result = Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(result, Success(-1))
  }

  test("recoverFailure should propagate successful computations") {
    val x = Random.nextInt()
    val eventuallyResult =
      recoverFailure(Future.successful(x))
    val result = Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(result, Success(x))
  }

  test("sequenceComputations should start the second computation after the first has completed (2pts)") {
    try
      val eventuallyResult =
        sequenceComputations(delay(Success(1)), delay(Success(2)))
      Await.ready(eventuallyResult, 500.milliseconds)
      fail("Asynchronous computations finished too early")
    catch
      case _: TimeoutException =>
        ()
  }

  test("sequenceComputations should not start the second computation if the first has failed (2pts)") {
    val counter = AtomicInteger(0)
    val eventuallyResult =
      sequenceComputations(
        () => Future.failed(Exception()),
        () => Future.successful { counter.incrementAndGet(); () }
      )
    Await.ready(eventuallyResult, 100.milliseconds)
    assertEquals(counter.get(), 0)
  }

  test("concurrentComputations should start both computations independently of each other’s completion (2pts)") {
    try
      val eventuallyResult =
        concurrentComputations(delay(Success(1)), delay(Success(2)))
      Await.ready(eventuallyResult, 700.milliseconds)
      ()
    catch
      case _: TimeoutException =>
        fail("Asynchronous computations took too much time")
  }

  test("insist should not retry successful computations (3pts)") {
    val counter = AtomicInteger(0)
    val eventuallyResult =
      insist(() => Future { counter.incrementAndGet() }, maxAttempts = 3)
    Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(counter.get(), 1)
  }

  test("insist should retry failed computations (3pts)") {
    val counter = AtomicInteger(0)
    val eventuallyResult =
      insist(
        () => Future { counter.incrementAndGet(); throw Exception() },
        maxAttempts = 3
      )
    Await.ready(eventuallyResult, 200.milliseconds).value.get
    assertEquals(counter.get(), 3)
  }

  test("futurize should handle successful computation results (3pts)") {
    val success = Success(Random.nextInt())
    val succeeding = new CallbackBasedApi:
      def computeIntAsync(continuation: Try[Int] => Unit): Unit = continuation(success)
    val eventuallyInt = futurize(succeeding).computeIntAsync()
    Await.ready(eventuallyInt, 200.milliseconds)
    assertEquals(eventuallyInt.value.get, success)
  }

  test("futurize should handle failed computation results (3pts)") {
    val failure = Failure(Exception("Oops"))
    val failing = new CallbackBasedApi:
      def computeIntAsync(continuation: Try[Int] => Unit): Unit = continuation(failure)
    val eventuallyInt = futurize(failing).computeIntAsync()
    Await.ready(eventuallyInt, 200.milliseconds)
    assertEquals(eventuallyInt.value.get, failure)
  }
