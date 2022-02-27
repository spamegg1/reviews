package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values 
   * for multiple tests. For instance, we would like to create an Int-set
   * and have multiple test about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, 
   * we can store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? 
   * Then the test methods are not even executed, because creating an instance 
   * of the test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val three = union(s1, union(s2, s3))                                 // TODO

    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s9 = singletonSet(9)

    val six = union(s1, union(s2, union(s3, union(s4, union(s5, s6)))))

    val bound = 1000
    val range = (-bound to bound)

    val isOdd = (n: Int) => n % 2 != 0
    val isEven = (n: Int) => n % 2 == 0
    val isPos = (n: Int) => n > 0
    val isNeg = (n: Int) => n < 0
    val sq = (n: Int) => n * n

    val odds = range filter isOdd
    val evens = range filter isEven
    val positives = range filter isPos
    val negatives = range filter isNeg

    val foldFun = (set: FunSet, num: Int) => union(set, singletonSet(num))
    val numsToFunSet = (s: Iterable[Int]) =>
      s.tail.foldLeft(singletonSet(s.head))(foldFun)

    val allSet = numsToFunSet(range)
    val oddSet = numsToFunSet(odds)
    val evenSet = numsToFunSet(evens)
    val posSet = numsToFunSet(positives)
    val negSet = numsToFunSet(negatives)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {                               // TODO

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("singleton set one does not contain two") {                       // TODO
    new TestSets:
      assertEquals(contains(s1, 2), false, "Singleton two")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("union with repetitions does not contain repeated elements") {    // TODO
    new TestSets:
      val u = union(s1, union(s2, union(s1, s2)))
      assertEquals(funSize(u), 2, "Union size")
  }

  test("intersect contains only elements common in both sets") {         // TODO
    new TestSets:
      val u = union(s1, s2)
      val i1 = intersect(u, s1)
      val i2 = intersect(s2, u)
      assertEquals(contains(i1, 1), true, "Intersect 1")
      assertEquals(contains(i1, 2), false, "Intersect 1")
      assertEquals(contains(i2, 2), true, "Intersect 2")
      assertEquals(contains(i2, 1), false, "Intersect 2")
  }

  test("intersect of two sets with no common elts is empty") {           // TODO
    new TestSets:
      val u = union(s1, s2)
      val i3 = intersect(u, s3)
      assertEquals(contains(i3, 1), false, "Intersect 3")
      assertEquals(contains(i3, 2), false, "Intersect 3")
      assertEquals(contains(i3, 3), false, "Intersect 3")
  }

  test("diff contains only elements of first set") {                     // TODO
    new TestSets:
      val u = union(s1, s2)
      val d1 = diff(u, s1)
      val d2 = diff(u, s2)
      assertEquals(contains(d1, 1), false, "Diff 1")
      assertEquals(contains(d1, 2), true, "Diff 1")
      assertEquals(contains(d2, 1), true, "Diff 2")
      assertEquals(contains(d2, 2), false, "Diff 2")
  }

  test("diff of two sets where first is a subset of second is empty") {  // TODO
    new TestSets:
      val d3 = diff(s1, three)
      assertEquals(contains(d3, 1), false, "Diff 3")
      assertEquals(contains(d3, 2), false, "Diff 3")
      assertEquals(contains(d3, 3), false, "Diff 3")
  }

  test("funSize returns correct set sizes") {                            // TODO
    new TestSets:
      assertEquals(funSize(s1), 1, "FunSize 1")
      assertEquals(funSize(three), 3, "FunSize 2")
      assertEquals(funSize(six), 6, "FunSize 3")
      assertEquals(funSize(oddSet), 1000, "FunSize 4")
      assertEquals(funSize(evenSet), 1001, "FunSize 5")
  }

  test("filter of even numbers contains only even numbers") {            // TODO
    new TestSets:
      val f = filter(six, isEven)
      assertEquals(contains(f, 1), false, "Filter 1")
      assertEquals(contains(f, 2), true, "Filter 2")
      assertEquals(contains(f, 3), false, "Filter 3")
      assertEquals(contains(f, 4), true, "Filter 4")
      assertEquals(contains(f, 5), false, "Filter 5")
      assertEquals(contains(f, 6), true, "Filter 6")
  }

  test("filter returns all odd/even numbers correctly") {                // TODO
    new TestSets:
      assertEquals(funSize(filter(allSet, isOdd)), 1000, "Filter odds")
      assertEquals(funSize(filter(allSet, isEven)), 1001, "Filter evens")
  }

  test("forall checks all odd/even numbers within bounds correctly") {   // TODO
    new TestSets:
      assertEquals(forall(oddSet, isOdd), true, "Forall odds")
      assertEquals(forall(evenSet, isEven), true, "Forall evens")
  }

  test("exists checks oddness/evenness correctly") {                     // TODO
    new TestSets:
      assertEquals(exists(s1, isOdd), true, "Exists 1")
      assertEquals(exists(s1, isEven), false, "Exists 2")
      assertEquals(exists(s2, isOdd), false, "Exists 3")
      assertEquals(exists(s2, isEven), true, "Exists 4")
  }

  test("map correctly applies square function to a set") {               // TODO
    new TestSets:
      val threeSquared = union(s1, union(s4, s9))
      assertEquals(eql(map(three, sq), threeSquared), true, "Map 123")
  }

  // These two tests take a long time. Don't worry, be patient.
  test("map: squares of negatives are all positive".ignore) {            // TODO
    new TestSets:
      val negsSquared = map(negSet, sq)
      assertEquals(forall(negsSquared, isPos), true, "Map negs sq")
  }

  test("map: adding 1 to odds make them all even".ignore) {              // TODO
    new TestSets:
      val oddsPlusOne = map(oddSet, x => x + 1)
      assertEquals(forall(oddsPlusOne, isEven), true, "Map odds +1")
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
