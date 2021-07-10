package wikigraph

import scala.concurrent.{ ExecutionContext, Future }
import wikigraph.errors.WikiError
import wikigraph.errors.WikiException

/**
  * The result of an asynchronous computation which may fail.
  * This class is wrapper of Future[Either[Seq[WikiError], A]]
  *
  * @param A the type of the result of a successful computation
  * @param value the future to use to create this WikiResult. Checkout
  *        the companion object to find other ways to create a WikiResult
  * 
  * A WikiResult, after it completes, can be in one of the following states:
  * 
  *  - [[Success(Right(result))]]
  *  - [[Failure(exception)]]
  *  - [[Success(Left(Seq(error)))]]
  */
case class WikiResult[A](val value: Future[Either[Seq[WikiError], A]]):

  /**
    * If the computation fails, use the provided value as a result
    * 
    * @param solution the value to use in case of failure
    */
  def orElse(solution: A)(using ExecutionContext): WikiResult[A] =
    val f = value.map {
      case Left(_) => Right(solution)
      case nofail@Right(_)=> nofail
    }
    WikiResult(f)

  /**
    * If the computation fails, use the result of the provided computation
    * 
    * @param b the computation to use in case of failure
    */
  def fallbackTo(that: => WikiResult[A])(using ExecutionContext): WikiResult[A] =
    val f = value.flatMap {
      case Left(_) => that.value
      case Right(_) => value
    }
    WikiResult(f)

  /**
    * Transform the result of the computation
    * 
    * @param f the function used to transform the result of the computation
    * 
    * Hint: Both Either and Future have a similar method
    */
  def map[B](f: A => B)(using ExecutionContext): WikiResult[B] =
    ???

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
    val futureB: Future[Either[Seq[WikiError], B]] = value.flatMap {
      ???
    }
    WikiResult(futureB)

  /**
    * Retrieve the results of two computations and produce a result containing the
    * two results in a pair. If one of the computations fails, its errors are propagated.
    * If both fail, the errors of the two are propagated
    * 
    * @param that the second computation
    * 
    * Hint: The async part has been handled for you. You need to zip the two Either 
    */
  def zip[B](that: WikiResult[B])(using ExecutionContext): WikiResult[(A, B)] =
    def zipEithersAcc(a: Either[Seq[WikiError], A], b: Either[Seq[WikiError], B]): Either[Seq[WikiError], (A, B)] =
      ???
    WikiResult(this.value.flatMap { thisEither =>
      that.value.map { thatEither =>
        zipEithersAcc(thisEither, thatEither)
      }
    })

object WikiResult:
  /**
    * Creates a WikiResult which succeeds with the provided value
    * 
    * @param A the type of the result
    * @param a the value of the result
    */
  def successful[A](a: A): WikiResult[A] = WikiResult(Future.successful(Right[Seq[WikiError], A](a)))

  /**
    * Creates a WikiResult which fails with the provided domain error
    * 
    * @param e the domain error which causes the failure of the computation
    */
  def domainError[A](e: WikiError): WikiResult[A] = WikiResult(Future.successful(Left[Seq[WikiError], A](Seq(e))))

  /**
    * Creates a WikiResult which fails with the provided system exception
    * 
    * @param t the throwable exception causing the failure of the computation
    */
  def systemFailure[A](t: WikiException): WikiResult[A] = WikiResult(Future.failed(t))

  /**
    * Starts running the provided block asynchronously in the contextual execution context
    * 
    * @param block the computation to execute
    */
  def start(block: => Unit)(using ExecutionContext): WikiResult[Unit] = WikiResult(Future(Right(block)))

  /**
    * Asynchronously and non-blockingly transforms a `Seq[A]` into a WikiResult[Seq[B]]
    * using the provided `A => WikiResult[B]`.
    * 
    * The results are collected in the Seq as the computation progresses without blocking. 
    * 
    * Note: the order is preserved
    * 
    * Hint: Use WikiResult.zip
    */
  def traverse[A, B](ls: Seq[A])(f: A => WikiResult[B])(using ExecutionContext): WikiResult[Seq[B]] =
    ???

end WikiResult
