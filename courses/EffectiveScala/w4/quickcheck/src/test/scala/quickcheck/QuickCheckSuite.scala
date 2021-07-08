package quickcheck

import org.scalacheck.Prop
import org.scalacheck.Properties

import org.scalacheck.Arbitrary.*
import org.scalacheck.Prop
import org.scalacheck.Prop.*
import org.scalacheck.Test.{check, Result, Failed, PropException}

class QuickCheckSuite extends munit.FunSuite:
  def checkBogus(heapInterface: HeapInterface): Unit =
    def fail = throw AssertionError(
      s"A bogus heap should NOT satisfy all properties. Try to find the bug!")

    assert(
      check(asProp(HeapProperties(new quickcheck.test.BinomialHeap {})))(identity).passed,
      "The properties were not satisfied by the correct binomial heap implementation. Make sure the properties you write are true of heaps."
    )

    check(asProp(HeapProperties(heapInterface)))(identity) match
      case r: Result => r.status match
        case _: Failed         => () // OK: scalacheck found a counter example!
        case p: PropException  => p.e match
          case e: NoSuchElementException => () // OK: the implementation throws NSEE
          case _ => fail
        case _ => fail
  end checkBogus

  /** Turns a `Properties` instance into a single `Prop` by combining all the properties */
  def asProp(properties: Properties): Prop = Prop.all(properties.properties.map(_(1)).toSeq*)

  test("Bogus (1) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus1BinomialHeap())
  }

  test("Bogus (2) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus2BinomialHeap())
  }

  test("Bogus (3) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus3BinomialHeap())
  }

  test("Bogus (4) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus4BinomialHeap())
  }

  test("Bogus (5) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus5BinomialHeap())
  }

  import scala.concurrent.duration.DurationInt
  override val munitTimeout = 10.seconds

end QuickCheckSuite
