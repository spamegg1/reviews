package wikigraph.testing

import wikigraph.WikiResult
import wikigraph.errors.*
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Try, Success, Failure}
import scala.concurrent.duration.Duration
import org.scalacheck.Prop
import org.scalacheck.Prop.propBoolean

extension [A] (el: WikiResult[A])(using ExecutionContext)
  def errors: Seq[WikiError] =
    Try(Await.result(el.value, timeout)) match
       case Success(Left(errs)) => errs
       case _ => List.empty
  def failure: Option[Throwable] =
    Try(Await.result(el.value, timeout)) match
      case Failure(f) => Some(f)
      case _ => None
  def extractUnsafe: A =
    Try(Await.result(el.value, timeout)) match
      case Success(Right(a)) => a
      case Success(Left(errors)) =>
        throw Exception(s"Expecting a successful WikiResult but obtained some" +
                        s" business error(s): ${errors.mkString("\n")}")
      case Failure(exception) =>
        throw Exception(s"Expecting a successful WikiResult but obtained a " +
                        s"system failure: ${exception}")

def blockAndCompare[A](expected: WikiResult[A],
                       obtained: WikiResult[A],
                       msg: String = ""): Prop =
  val expectedTry = Try(Await.result(expected.value, timeout))
  val obtainedTry = Try(Await.result(obtained.value, timeout))

  (expectedTry, obtainedTry) match
    case (Success(expectedRes), Success(obtainedRes))
      if expectedRes != obtainedRes =>
      false :| s"$msg\n - Expected value: $expectedRes\n - " +
               s"Obtained value: $obtainedRes"
    case (Failure(expectedFail), Failure(obtainedFail))
      if expectedFail != obtainedFail =>
      false :| s"$msg\n - Expected failure: $expectedFail\n - " +
               s"Obtained failure: $obtainedFail"
    case (Success(expectedRes), Failure(obtainedFail)) =>
      false :| s"$msg\nThe value $expectedRes was expected but I " +
               s"obtained the exception $obtainedFail"
    case (Failure(expectedFail), Success(obtainedRes)) =>
      false :| s"$msg\nThe exception $expectedFail was expected but I " +
               s"obtained the value $obtainedRes"
    case _ => expectedTry == obtainedTry

def sameErrorMessage(op1: Option[Throwable], op2: Option[Throwable]): Boolean =
  (op1, op2) match
    case (Some(w1: WikiException), Some(w2: WikiException)) => w1 == w2
    case (Some(t1), Some(t2)) => t1.getMessage == t2.getMessage
    case (None, None) => true
    case _ => false

private val timeout: Duration =  Duration(10, "s")
