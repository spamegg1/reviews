package wikigraph.testing

import wikigraph.WikiResult
import wikigraph.errors.*
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.util.{Try, Success, Failure}
import scala.concurrent.duration.Duration

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
      case _ => throw Exception("impossible")

def blockAndCompare[A](a: WikiResult[A], b: WikiResult[A]): Boolean =
  (Try(Await.result(a.value, timeout)) == Try(Await.result(b.value, timeout)))

def sameErrorMessage(op1: Option[Throwable], op2: Option[Throwable]): Boolean =
  (op1, op2) match
    case (Some(w1: WikiException), Some(w2: WikiException)) => w1 == w2
    case (Some(t1), Some(t2)) => t1.getMessage == t2.getMessage
    case (None, None) => true
    case _ => false
  
private val timeout: Duration =  Duration(10, "s")
