package quickcheck

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen.{const, oneOf}
import org.scalacheck.{Arbitrary, Gen, Prop, Test}
import org.scalacheck.rng.Seed
import org.scalacheck.Prop.*

class QuickCheckSuite extends munit.FunSuite:

  test("Bogus (1) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus1BinomialHeap())
      (_.insertMinAndGetMin, _.deleteAllProducesSortedList, _.meldingHeaps)
  }

  test("Bogus (2) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus2BinomialHeap())(_.meldingHeaps)
  }

  test("Bogus (3) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus3BinomialHeap())
              (_.meldingSmallHeaps, _.meldingHeaps)
  }

  test("Bogus (4) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus4BinomialHeap())(_.meldingHeaps)
  }

  test("Bogus (5) binomial heap does not satisfy properties. (10pts)") {
    checkBogus(quickcheck.test.Bogus5BinomialHeap())
      (_.deleteAllProducesSortedList, _.meldingSmallHeaps, _.meldingHeaps)
  }

  import scala.concurrent.duration.DurationInt
  override val munitTimeout = 10.seconds
  val testParameters = Test.Parameters.default

  def checkBogus(heapInterface: HeapInterface)(
      shouldFail: HeapProperties => (String, Prop)*
  ): Unit =
    // Check that the properties pass on the correct heap implementation.
    // We don’t perform this check as a separate MUnit test, because if
    // it failed it would only decrease the score for a small percentage.
    checkPropertiesOnCorrectHeap()

    // Check that the properties expected to fail on the given
    // heap implementation effectively fail.
    checkPropertiesOnBogusHeap(heapInterface, shouldFail)
  end checkBogus

  def checkPropertiesOnCorrectHeap(): Unit =
    // All the props should pass on the correct implementation
    val propertiesOnCorrectHeap =
      val heap = new HeapProperties(quickcheck.test.BinomialHeap())
                 with ArbitraryHeaps
      new org.scalacheck.Properties("HeapProperties") {
        def register(labelledProp: (String, Prop)): Unit =
          property(labelledProp(0)) = labelledProp(1)
        register(heap.minOfTwo)
        register(heap.deleteMinOfOne)
        register(heap.insertMinAndGetMin)
        register(heap.deleteAllProducesSortedList)
        register(heap.meldingSmallHeaps)
        register(heap.meldingHeaps)
      }

    val (result, output) =
      val baos = java.io.ByteArrayOutputStream()
      val ps = java.io.PrintStream(baos)
      val testResult =
        Console.withOut(ps) {
          Test.checkProperties(
            testParameters.withTestCallback(
              org
                .scalacheck
                .util
                .ConsoleReporter(verbosity = 1, columnWidth = 120)
                .chain(testParameters.testCallback)
            ),
            propertiesOnCorrectHeap
          )
        }
      ps.flush()
      (testResult, baos.toString)

    if result.exists(!_._2.passed) then throw AssertionError(
      "The properties were not satisfied by the correct binomial heap " +
      "implementation. Make sure the properties you write are true of heaps. " +
      "Here is the output of scalacheck when checking your properties on the " +
      s"correct heap implementation.\n$output"
    )
  end checkPropertiesOnCorrectHeap

  // The properties provided by shouldFail are expected not to pass
  def checkPropertiesOnBogusHeap(heapInterface: HeapInterface,
          shouldFail: Seq[HeapProperties => (String, Prop)]): Unit =
    val submittedProperties =
      new HeapProperties(heapInterface) with ArbitraryHeaps

    val correctProperties =
      new HeapProperties(heapInterface) with CorrectProperties
                                        with ArbitraryHeaps
    shouldFail.foreach { select =>
      // Select the property in both the submitted properties
      // and the correct properties
      val (label, submittedProp) = select(submittedProperties)
      val (_, correctProp)       = select(correctProperties)
      val seed = Seed(1234567L)

      // Check both the submitted property and the correct property
      val submittedPropResult = checkProp(submittedProp, seed, testParameters)
      val correctPropResult   = checkProp(correctProp, seed, testParameters)

      // Check that the submitted property detects a bug in the heap
      // implementation. That is, check that it fails on some input.
      (submittedPropResult.status, correctPropResult.status) match

        // (some bogus heap implementations fail the properties
        // by throwing a NoSuchElementException)
        case (False | Prop.Exception(_: NoSuchElementException), _) => ()  // OK

        case (Prop.Exception(e), _) =>
          throw AssertionError(s"Unexpected exception $e occurred during " +
                               s"evaluation of the property: \"$label\"")

        case (True | Proof, False | Prop.Exception(_) | Undecided) =>
          // The submitted property unexpectedly passed,
          // so we show some input values for which it is expected to fail
          val inputs =
            correctPropResult
              .args
              .zipWithIndex
              .map((arg, i) => s"> ARG_$i: ${arg.arg}")
              .mkString("\n")

          throw AssertionError(s"The property \"$label\" was expected to " +
                               s"fail but it succeeded.\nThe following input " +
                               s"is expected to make it fail\n$inputs")

        case (Undecided, _) =>
          throw AssertionError(s"The property \"$label\" was expected to fail "
                + "but Scalacheck could not generate enough inputs to test it")

        case (True | Proof, True | Proof) =>
          throw AssertionError(s"The property \"$label\" was expected to fail" +
            "but it succeeded.\nSubmit your work to the online grader " +
            "to get examples of input data that should fail the property.")
    }
  end checkPropertiesOnBogusHeap

  def checkProp(p: Prop, startSeed: Seed,
                testParameters: Test.Parameters): Result =
    // Set up tests
    val iterations = testParameters.minSuccessfulTests
    val sizeStep =
      (testParameters.maxSize - testParameters.minSize) / iterations.toDouble

    // Generate all results
    val counts = LazyList.range(0, iterations)

    val results = counts
      .scanLeft(startSeed)((oldSeed, _) => oldSeed.slide)
      .zip(counts)
      .map { (seed, count) =>
        val genPrms = Gen.Parameters.default
          .withLegacyShrinking(testParameters.useLegacyShrinking)
          .withInitialSeed(Some(seed))
          .withSize((testParameters.minSize.toDouble +
                    (sizeStep * count)).round.toInt)

        p(genPrms)
      }

    // If the Prop resulted in at least one failure, report it
    results reduceLeft (_ && _)

end QuickCheckSuite
