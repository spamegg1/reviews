package wikigraph

import scala.concurrent.{ ExecutionContext, Future }
import wikigraph.errors.WikiError
import wikigraph.errors.WikiException

/**
  * The result of an asynchronous computation which may fail.
  * This class is wrapper of `Future[Either[Seq[WikiError], A]]`.
  *
  * @tparam A the type of the result of a successful computation
  * @param value the future to use to create this WikiResult. Checkout
  *        the companion object to find other ways to create a WikiResult
  * 
  * A WikiResult, after it completes, can be in one of the following states:
  * 
  *  - success: `Success(Right(result))`
  *  - system failure: `Failure(exception)`
  *  - domain error: `Success(Left(Seq(error)))`
  */
case class WikiResult[A](value: Future[Either[Seq[WikiError], A]]):

  /**
    * Recovers from domain errors by returning the 
    * provided successful `solution` value instead.
    * If this WikiResult is a system failure, that failure is not recovered.
    *
    * @param solution the value to use in case of failure
    */
  def orElse(solution: A)(using ExecutionContext): WikiResult[A] =
    val f = value map {
      case Left(_)           => Right(solution)
      case nofail @ Right(_) => nofail
    }
    WikiResult(f)

  /**
    * Tries to recover from domain errors by using `that` WikiResult instead.
    * If both `this` and `that` are domain errors, only the errors of `that`
    * are returned.
    * If this WikiResult is a system failure, that failure is not recovered.
    *
    * @param b the computation to use in case of failure
    */
  def fallbackTo(that: => WikiResult[A])
                (using ExecutionContext): WikiResult[A] =
    val f = this.value flatMap {
      case Left (_) => that.value
      case Right(_) => this.value }
    WikiResult(f)

  /**
    * Transform the successful result of this computation
    * 
    * @param f the function used to transform the result of the computation
    * 
    * Hint: Both Either and Future have a similar method
    */
  def map[B](f: A => B)(using ExecutionContext): WikiResult[B] =
    WikiResult(value map (_ map f))

  /**
    * Use the result of this computation as an input for another asynchronous
    * computation
    * 
    * @param f the next computation to run
    * 
    * Hint: Future has a similar method. If the first computation fails, its
    *       error should be propagated
    */
  def flatMap[B](f: A => WikiResult[B])(using ExecutionContext): WikiResult[B] =
    val futureB: Future[Either[Seq[WikiError], B]] = value flatMap {     // TODO
      case Left (seqA) => Future(Left(seqA))
      case Right(a)    => f(a).value }
    WikiResult(futureB)

  /**
    * Retrieve the results of two computations and produce a result containing
    * the two results in a pair. If one of the computations fails, its errors
    * are propagated. If both fail, the errors of the two are propagated.
    * 
    * @param that the second computation
    * 
    * Hint: The async part has been handled for you. 
    * You need to zip the two Either.
    */
  def zip[B](that: WikiResult[B])(using ExecutionContext): WikiResult[(A, B)] =
    def zipEithersAcc(a: Either[Seq[WikiError], A],
                      b: Either[Seq[WikiError], B])
                       : Either[Seq[WikiError], (A, B)] =                // TODO
      (a, b) match
        case (Left (seqA), Left (seqB)) => Left (seqA ++ seqB)
        case (Left (seqA), Right(  _ )) => Left (seqA)
        case (Right( _  ), Left (seqB)) => Left (seqB)
        case (Right( x  ), Right(  y )) => Right((x, y))

    WikiResult(
      for
        thisEither <- this.value
        thatEither <- that.value
      yield
        zipEithersAcc(thisEither, thatEither))

object WikiResult:
  /**
    * Creates a WikiResult which succeeds with the provided value
    * 
    * @param A the type of the result
    * @param a the value of the result
    */
  def successful[A](a: A): WikiResult[A] =
    WikiResult(Future.successful(Right[Seq[WikiError], A](a)))

  /**
    * Creates a WikiResult which fails with the provided domain error
    * 
    * @param e the domain error which causes the failure of the computation
    */
  def domainError[A](e: WikiError): WikiResult[A] =
    WikiResult(Future.successful(Left[Seq[WikiError], A](Seq(e))))

  /**
    * Creates a WikiResult which fails with the provided system exception
    * 
    * @param t the throwable exception causing the failure of the computation
    */
  def systemFailure[A](t: WikiException): WikiResult[A] =
    WikiResult(Future.failed(t))

  /**
    * Starts running the provided block asynchronously
    * in the contextual execution context
    * @param block the computation to execute
    */
  def start(block: => Unit)(using ExecutionContext): WikiResult[Unit] =
    WikiResult(Future(Right(block)))

  /**
    * Asynchronously and non-blockingly transforms a `Seq[A]` into a
    * WikiResult[Seq[B]] using the provided function `A => WikiResult[B]`.
    *
    * Applies the function `f` to every element of the sequence `as`,
    * concurrently computing values of type `B`.
    *
    *   - If the function `f` always eventually returns successful values of
    *     type `B`, returns a successful result containing the sequence of 
    *     values (note: order is preserved).
    *   - If the function `f` eventually returns some domain errors,
    *     returns a failed result containing all the domain errors.
    *   - If the function `f` eventually returns some system failure,
    *     returns a failed result containing one of the system failures.
    *
    * Hint: iterate over the input sequence with `foldLeft` or `foldRight`,
    * combine all the `WikiResult` values with the operation `zip` and `map`.
    * If the input sequence is empty, return a successful empty sequence.
    * Hint: this method is very similar to the method `validateEach` shown in
    * the lecture “Manipulating Validated Values”.
    */
  def traverse[A, B](as: Seq[A])(f: A => WikiResult[B])
                    (using ExecutionContext): WikiResult[Seq[B]] =       // TODO
    val init: WikiResult[Seq[B]] = WikiResult.successful(Seq.empty)
    def foldFun(seqB: WikiResult[Seq[B]], a: A): WikiResult[Seq[B]] =
      seqB
        .zip(f(a))
        .map((bs, b) => bs :+ b)
    as.foldLeft(init)(foldFun)

end WikiResult
