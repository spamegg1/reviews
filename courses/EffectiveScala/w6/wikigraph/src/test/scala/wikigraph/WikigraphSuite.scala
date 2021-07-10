package wikigraph

import org.scalacheck.*
import Prop.{forAll, propBoolean}
import wikigraph.WikiResult
import wikigraph.WikiResult.*
import wikigraph.Articles.*
import wikigraph.implementations.InMemory
import wikigraph.errors.WikiError
import wikigraph.errors.WikiError.*
    
import scala.util.{Try, Success => TSucc, Failure => TFail}
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.given
import errors.*

class WikigraphSuite extends munit.FunSuite:
  given Gen[Int] = Arbitrary.arbitrary[Int]
  given Gen[String] = Arbitrary.arbitrary[String]
  given idGen: Gen[ArticleId] =
    Gen.posNum[Int].map(ArticleId(_))
  given artNotFoundGen: Gen[ArticleNotFound] =
    idGen.map(ArticleNotFound(_))
  given wikiErrorGen: Gen[WikiError] =
    for
      id <- idGen
      func <- Gen.oneOf(ArticleNotFound(_), TitleNotFound(_))
    yield func(id)

  given wikiExceptionGen: Gen[WikiException] =
    Gen.oneOf(WikiException.Timeout, WikiException.TooManyRequests, WikiException.ResourceNotFound("test"))

  def wikiResultGen[A](withErrors: Boolean, withFailure: Boolean)(using errGen: Gen[WikiError], resGen: Gen[A]): Gen[WikiResult[A]] =
    val eitherGen: Gen[Either[Seq[WikiError], A]] = 
      val success: Gen[Either[Seq[WikiError], A]] = resGen.map(res => Right(res))
      if !withErrors then success
      else Gen.containerOf[List, WikiError](errGen).flatMap { errors =>
        if errors.nonEmpty then errGen.map(err => Left(errors))
        else success
      }
    
    val success = eitherGen.map(ei => WikiResult(Future.successful(ei)))
    if !withFailure then success
    else Gen.oneOf(true, false).flatMap { isFailed =>
        if isFailed then wikiExceptionGen.map(err => WikiResult(Future.failed(err)))
        else success
      }

  val timeout = Duration(5, "s")

  property("zip without errors") {
    val genA = wikiResultGen[Int](false, false)
    val genB = wikiResultGen[String](false, false)
    val genAB = genA.flatMap(l => genB.map(r => l -> r))
    forAll(genAB) { (a: WikiResult[Int], b: WikiResult[String]) =>
      val obtained = a.zip(b).value
      val expected = a.value.flatMap(eiL => b.value.map(eiR => eiL.flatMap(l => eiR.map(r => l -> r)))) 

      Await.result(obtained, timeout) == Await.result(expected, timeout)
    }
  }

  property("zip with domain errors") {
    val genA = wikiResultGen[Int](true, false)
    val genB = wikiResultGen[String](true, false)
    val genAB = genA.flatMap(l => genB.map(r => l -> r))

    forAll(genAB) { (a: WikiResult[Int], b: WikiResult[String]) =>
      val obtained = Await.result(a.zip(b).value, timeout)

      val ar = Await.result(a.value, timeout)
      val br = Await.result(b.value, timeout)

      val errs: Seq[WikiError] = ar.swap.getOrElse(Seq.empty) ++ br.swap.getOrElse(Seq.empty)

      if errs.nonEmpty then
        (errs == obtained.swap.getOrElse(Seq.empty)) :| "Errors are not accumulated correctly"
      else 
        (ar.toOption.flatMap(l => br.toOption.map(r => l->r)) == obtained.toOption) :| "Result values are not zipped correctly"
    }
  }

  property("zip with domain errors and failures") {
    val genA = wikiResultGen[Int](true, true)
    val genB = wikiResultGen[String](true, true)
    val genAB = genA.flatMap(l => genB.map(r => l -> r))

    forAll(genAB) { (a: WikiResult[Int], b: WikiResult[String]) =>
      val obtained = Try(Await.result(a.zip(b).value, timeout))

      val ar = Try(Await.result(a.value, timeout))
      val br = Try(Await.result(b.value, timeout))


      if ar.isFailure || br.isFailure then
        (List(ar, br).find(_.isFailure).get == obtained) :| "System failure is not reported correctly"
      else 
        val errs: Seq[WikiError] = ar.get.swap.getOrElse(Seq.empty) ++ br.get.swap.getOrElse(Seq.empty)
        if errs.nonEmpty then 
          (errs == obtained.get.swap.getOrElse(Seq.empty)) :| "Errors are not accumulated correctly"
        else (ar.get.toOption.flatMap(l => br.get.toOption.map(r => l->r)) == obtained.get.toOption) :| "Results are not accumulated correctly"
    }
  }

  property("traverse without errors") {
    forAll(Gen.containerOf[List, Int](summon[Gen[Int]])) { ls =>
      val pure: (Int => String) = x => (x + 42).toString
      val toResult: (Int => WikiResult[String]) = x => WikiResult.successful(pure(x))

      val obtained = Await.result(WikiResult.traverse(ls)(toResult).value, timeout)
      val expected: Either[Seq[WikiError], List[String]] = Right(ls.map(pure))

      expected == obtained
    }
  }

  property("traverse with domain errors") {
    forAll(Gen.containerOf[List, WikiResult[Int]](wikiResultGen[Int](true, false))) { ls =>
      val pure: (Int => String) = x => (x + 42).toString

      val obtained: Either[Seq[WikiError], Seq[String]] = 
        val result: WikiResult[Seq[String]] = WikiResult.traverse(ls)(_.map(pure))
        Await.result(result.value, timeout)

      val eits = ls.map(w => Await.result(w.value, timeout).map(pure))
      val expErrors: Seq[Seq[WikiError]] = eits.collect { case Left(domainError) => domainError }

      if expErrors.nonEmpty then (expErrors.flatten == obtained.swap.getOrElse(Seq.empty)) :| "Errors are not reported correctly"
      else (eits.collect { case Right(value) => value } == obtained.getOrElse(Seq.empty)) :| "Result sequence is incorrect"
    }
  }

  property("traverse with domain errors and failures") {
    forAll(Gen.containerOf[List, WikiResult[Int]](wikiResultGen[Int](true, true))) { ls =>
      val pure: (Int => String) = x => (x + 42).toString

      val obtained: Try[Either[Seq[WikiError], Seq[String]]] = 
        val result: WikiResult[Seq[String]] = WikiResult.traverse(ls)(_.map(pure))
        Try(Await.result(result.value, timeout))

      val eits: Seq[Try[Either[Seq[WikiError], String]]] =
        ls.map(w => Try(Await.result(w.value, timeout).map(pure)))

      eits.collectFirst { case TFail(ex) => ex } match
        case Some(ex) => (TFail(ex) == obtained) :| "System failure is reported correctly"
        case None =>
          val expErrors: Seq[Seq[WikiError]] = eits.collect { case TSucc(Left(domainError)) => domainError }
          if expErrors.nonEmpty then 
            (expErrors.flatten == obtained.get.swap.getOrElse(Seq.empty)) :| "Errors are reported correctly"
          else 
            (eits.collect { case TSucc(Right(value)) => value } == obtained.get.getOrElse(Seq.empty)) :| "Result sequence is correct"
    }
  }

  property("map") {
    forAll(wikiResultGen[Int](true, true)) { (w: WikiResult[Int]) =>
      val obtained = Try(Await.result(w.map(_ + 1).map(_.toString).value, timeout))

      Try(Await.result(w.value, timeout)) match
        case TSucc(Right(x)) => (TSucc(Right((x + 1).toString)) == obtained) :| "map on successfull WikiResult is correct"
        case domainErr@TSucc(Left(_)) => (domainErr == obtained) :| "map on a WikiResult failed with a domain error is correct propagates the domain error"
        case failure@TFail(_) => (failure == obtained) :| "map on a WikiResult failed with a system failure propagates the failure"
    }
  }

  property("flatMap with a successful function") {
    forAll(wikiResultGen[Int](true, true)) { (w: WikiResult[Int]) =>
      def f(x: Int): WikiResult[String] = WikiResult(Future.successful(Right((x + 2).toString)))
      val obtained = Try(Await.result(w.flatMap(f).value, timeout))


      Try(Await.result(w.value, timeout)) match
        case TSucc(Right(x)) => (TSucc(Right((x+2).toString)) == obtained) :| "flatMap on successfull WikiResult with a successful function is correct"
        case domainErr@TSucc(Left(_)) => (domainErr == obtained) :| "flatMap on a WikiResult failed with a domain error with a successful function propagates the error"
        case err@TFail(_) => (err == obtained) :| "flatMap on a WikiResult failed with a system failure propagates the failure"
    }
  }

  property("flatMap with a function producing a domain error") {
    forAll(wikiResultGen[Int](true, true)) { (w: WikiResult[Int]) =>
      val err = ArticleNotFound(ArticleId(42))
      def f(x: Int): WikiResult[String] = WikiResult.domainError(err)
      val origin = Try(Await.result(w.value, timeout))
      val obtained = Try(Await.result(w.flatMap(f).value, timeout))

      origin match
        case failure@TFail(_) => (failure == obtained) :| "flatMap on a WikiResult failed with a system failure is correct"
        case TSucc(Right(_)) => (obtained == TSucc(Left(Seq(err)))) :| "flatMap on a successfull WikiResult with a failing function produces a failed WikiResult with the correct domain error"
        case oldErr@TSucc(Left(_)) => (obtained == oldErr) :| "flatMap on a WikiResult with domain error using a failing function propagates the failure"
    }
  }

  property("flatMap with a function producing a system failure") {
    forAll(wikiResultGen[Int](true, true)) { (w: WikiResult[Int]) =>
      val ex = wikigraph.errors.WikiException.Timeout
      def f(x: Int): WikiResult[String] = WikiResult.systemFailure(ex)

      val origin = Try(Await.result(w.value, timeout))
      val obtained = Try(Await.result(w.flatMap(f).value, timeout))

      origin match
        case failure@TFail(_) => (failure == obtained) :| "flatMap on a WikiResult failed with a system failure is correct"
        case TSucc(Right(_)) => (obtained == TFail(ex)) :| "flatMap on a successfull WikiResult with a failing function produces a failed WikiResult with the correct system failure"
        case oldErr@TSucc(Left(_)) => (obtained == oldErr) :| "flatMap on a WikiResult with domain error using a failing function propagates the old error"
    }
  }

  test("breadthFirstSearch finds unique path") {
    val g: Map[ArticleId, Set[ArticleId]] = Map(
      ArticleId(1) -> Set(2 ,3).map(ArticleId(_)),
      ArticleId(2) -> Set(4).map(ArticleId(_)),
      ArticleId(3) -> Set(5).map(ArticleId(_)),
      ArticleId(5) -> Set(6).map(ArticleId(_))
    )
    val res = Await.result(Wikigraph(InMemory(g)).breadthFirstSearch(ArticleId(1), ArticleId(6), 10).value, timeout)

    assertEquals(res, Right(Some(3)), "correct distance")
  }

  test("breadthFirstSearch finds shortest path path") {
    val g: Map[ArticleId, Set[ArticleId]] = Map(
      ArticleId(1) -> Set(2 ,3).map(ArticleId(_)),
      ArticleId(2) -> Set(4).map(ArticleId(_)),
      ArticleId(3) -> Set(5).map(ArticleId(_)),
      ArticleId(5) -> Set(6).map(ArticleId(_)),
      ArticleId(6) -> Set(4).map(ArticleId(_))
    )

    val res = Await.result(Wikigraph(InMemory(g)).breadthFirstSearch(ArticleId(1), ArticleId(4), 10).value, timeout)

    assertEquals(res, Right(Some(2)), "correct distance")
  }

  test("breadthFirstSearch exits when maxDepth is reached") {
    val g: Map[ArticleId, Set[ArticleId]] = Map(
      ArticleId(1) -> Set(2).map(ArticleId(_)),
      ArticleId(2) -> Set(3).map(ArticleId(_)),
      ArticleId(3) -> Set(4).map(ArticleId(_)),
      ArticleId(4) -> Set(5).map(ArticleId(_)),
    )
    val res = Await.result(Wikigraph(InMemory(g)).breadthFirstSearch(ArticleId(1), ArticleId(5), 2).value, timeout)

    assertEquals(res, Right(None))
  }

  test("breadthFirstSearch does not fail on errors") {
    val g = Map(ArticleId(0) -> Set.empty[ArticleId])
    val res = Await.result(Wikigraph(InMemory(g)).breadthFirstSearch(ArticleId(1), ArticleId(1000), 4).value, timeout)
    assertEquals(res, Right(None))
  }

  test("distanceMatrix") {
    val g = Map(
      ArticleId(1) -> Set(2).map(ArticleId(_)),
      ArticleId(2) -> Set(3).map(ArticleId(_)),
      ArticleId(3) -> Set(1).map(ArticleId(_))
    )

    val res: Either[Seq[WikiError], Seq[(String, String, Option[Int])]] = Await.result(Wikigraph(InMemory(g)).distanceMatrix(List("TestArticle-1", "TestArticle-3")).value, timeout)
    val exp: Either[Seq[WikiError], Seq[(String, String, Option[Int])]] = Right(Seq(("TestArticle-1", "TestArticle-3", Some(2)), ("TestArticle-3", "TestArticle-1", Some(1))))

    assertEquals(res, exp)
  }

  def property(name: String)(prop: => Prop)(using munit.Location) =
    test(name)(checkProperty(prop))

  def checkProperty(prop: Prop): Unit =
    val result = org.scalacheck.Test.check(org.scalacheck.Test.Parameters.default, prop)
    def failure(labels: Set[String], fallback: String): Nothing =
      if labels.isEmpty then throw AssertionError(fallback)
      else throw AssertionError(labels.mkString(". "))
    result.status match
      case org.scalacheck.Test.Passed | _: org.scalacheck.Test.Proved => ()
      case org.scalacheck.Test.Failed(_, labels) => failure(labels, "A property failed.")
      case org.scalacheck.Test.PropException(_, e: munit.FailException, labels) => failure(labels, e.message)
      case org.scalacheck.Test.PropException(_, e, labels) => failure(labels, s"An exception was thrown during property evaluation: $e")
      case org.scalacheck.Test.Exhausted => failure(Set.empty, "Unable to generate test data.")

end WikigraphSuite
