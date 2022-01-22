package async

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
import scala.util.control.NonFatal
import scala.util.{Success, Failure}                                     // TODO

object Async extends AsyncInterface:

  /**
    * Transforms a successful asynchronous `Int` computation
    * into a `Boolean` indicating whether the number was even or not.
    * In case the given `Future` value failed, this method
    * should return a failed `Future` with the same error.
    */
  def transformSuccess(eventuallyX: Future[Int]): Future[Boolean] =      // TODO
    for
      int <- eventuallyX
    yield
      int % 2 == 0
    // eventuallyX map (_ % 2 == 0)                                // also works

  /**
    * Transforms a failed asynchronous `Int` computation into a
    * successful one returning `-1`.
    * Any non-fatal failure should be recovered.
    * In case the given `Future` value was successful, this method
    * should return a successful `Future` with the same value.
    */
  def recoverFailure(eventuallyX: Future[Int]): Future[Int] =            // TODO
    eventuallyX fallbackTo Future(-1)
    //eventuallyX recover { case _ => -1 }                         // also works
    //eventuallyX recoverWith { case _ => Future(-1) }             // also works

  /**
    * Perform two asynchronous computation, one after the other.
    * `makeAsyncComputation2` should start ''after'' the `Future` returned by
    * `makeAsyncComputation1` has completed.
    * In case the first asynchronous computation failed, the second one should
    * not even be started.
    * The returned `Future` value should contain the successful result of the
    * first and second asynchronous computations, paired together.
    */
  def sequenceComputations[A, B](
    makeAsyncComputation1: () => Future[A],
    makeAsyncComputation2: () => Future[B]
  ): Future[(A, B)] =                                                    // TODO
    for
      a <- makeAsyncComputation1()
      b <- makeAsyncComputation2()
    yield
      (a, b)

  /**
    * Concurrently perform two asynchronous computations and pair their
    * successful result together.
    * The two computations should be started independently of each other.
    * If one of them fails, this method should return the failure.
    */
  def concurrentComputations[A, B](
    makeAsyncComputation1: () => Future[A],
    makeAsyncComputation2: () => Future[B]
  ): Future[(A, B)] =                                                    // TODO
    makeAsyncComputation1() zip makeAsyncComputation2()
    // another way to do it is:
    // val fa: Future[A] = makeAsyncComputation1()
    // val fb: Future[B] = makeAsyncComputation2()  // this will start them both
    // for
    //   a <- fa
    //   b <- fb
    // yield
    //   (a, b)

  /**
    * Attempt to perform an asynchronous computation.
    * In case of failure this method should try again to make
    * the asynchronous computation so that at most `maxAttempts`
    * are eventually performed.
    */
  def insist[A](makeAsyncComputation: () => Future[A],
                maxAttempts: Int): Future[A] =                           // TODO
    if maxAttempts == 1 then
      makeAsyncComputation()
    else
      makeAsyncComputation() recoverWith
        { case _ => insist(makeAsyncComputation, maxAttempts - 1) }

  /**
    * Turns a callback-based API into a Future-based API
    * @return A `FutureBasedApi` that forwards calls to
    * `computeIntAsync` to the `callbackBasedApi`
    *         and returns its result in a `Future` value
    *
    * Hint: Use a `Promise`
    */
  def futurize(callbackBasedApi: CallbackBasedApi): FutureBasedApi =     // TODO
    () =>
      val p = Promise[Int]()                                     // Promise[Int]

      def continuation(t: Try[Int]): Unit = t match
        case Success(value) => p.trySuccess(value)                       // Unit
        case Failure(excep) => p.tryFailure(excep)                       // Unit

      callbackBasedApi.computeIntAsync(continuation)                     // Unit
      p.future                                                    // Future[Int]


/**
  * Dummy example of a callback-based API
  */
trait CallbackBasedApi:
  def computeIntAsync(continuation: Try[Int] => Unit): Unit

/**
  * API similar to [[CallbackBasedApi]], but based on `Future` instead
  */
trait FutureBasedApi:
  def computeIntAsync(): Future[Int]
