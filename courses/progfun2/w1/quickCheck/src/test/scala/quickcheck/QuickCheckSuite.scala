package quickcheck

import org.scalacheck.Properties

import org.scalacheck.Arbitrary.*
import org.scalacheck.Prop
import org.scalacheck.Test.{check, Result, Failed, PropException}

object QuickCheckBinomialHeap extends QuickCheckHeap with BinomialHeap

class QuickCheckSuite extends munit.FunSuite:
  def checkBogus(p: Properties): Unit =
    def fail = throw AssertionError(
      s"A bogus heap should NOT satisfy all properties. Try to find the bug!")

    check(asProp(p))(identity) match
      case r: Result => r.status match
        case _: Failed         => () // OK: scalacheck found a counter example!
        case p: PropException  => p.e match
          case e: NoSuchElementException => () // OK: the implementation throws NSEE
          case _ => fail
        case _ => fail

  /** Turns a `Properties` instance into a single `Prop` by combining all the properties */
  def asProp(properties: Properties): Prop = Prop.all(properties.properties.map(_._2).toSeq*)

  test("Binomial heap satisfies properties. (5pts)") {
    assert(
      check(asProp(new QuickCheckHeap with quickcheck.test.BinomialHeap))(identity).passed
    )
  }

  test("Bogus (1) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus1BinomialHeap)
  }

  test("Bogus (2) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus2BinomialHeap)
  }

  test("Bogus (3) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus3BinomialHeap)
  }

  test("Bogus (4) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus4BinomialHeap)
  }

  test("Bogus (5) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus5BinomialHeap)
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
