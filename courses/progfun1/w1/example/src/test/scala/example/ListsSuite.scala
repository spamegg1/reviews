package example

/**
 * This class implements a munit test suite for the methods in object
 * `Lists` that need to be implemented as part of this assignment. A test
 * suite is simply a collection of individual tests for some specific
 * component of a program.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class ListsSuite extends munit.FunSuite:

  /**
   * Tests are written using the `test("description") { ... }` syntax
   * The most common way to implement a test body is using the method `assert`
   * which tests that its argument evaluates to `true`. So one of the simplest
   * successful tests is the following:
   */
  test("one plus one is two (0pts)") {
    assert(1 + 1 == 2)
  }

  test("one plus one is three (0pts)?") {
    assert(1 + 1 != 3) // This assertion fails! Go ahead and fix it.        TODO
  }

  /**
   * One problem with the previous (failing) test is that munit will
   * only tell you that a test failed, but it will not tell you what was
   * the reason for the failure. The output looks like this:
   *
   * {{{
   * ==> X example.ListSuite.one plus one is three (0pts)?
   * 0.007s munit.FailException: 
   * /tmp/example/src/test/scala/example/ListSuite.scala:26 assertion failed
   * 25:  test("one plus one is two (0pts)") {
   * 26:      assert(1 + 1 == 3)
   * 27:  }
   * }}}
   *
   * This situation can be improved by using a assertEquals
   * (this is only possible in munit). So if you
   * run the next test, munit will show the following output:
   *
   * {{{
   * ==> X example.ListSuite.details why one plus one is not three (0pts)  
   * 0.006s munit.FailException: 
   * /tmp/example/src/test/scala/example/ListSuite.scala:72
   * 71:  test("details why one plus one is not three (0pts)") {
   * 72:      assertEquals(1 + 1, 3) // Fix me, please!
   * 73:  }
   * values are not the same
   * => Obtained
   * 3
   * => Diff (- obtained, + expected)
   * -3
   * +2
   * }}}
   *
   * We recommend to always use the assertEquals equality operator
   * when writing tests.
   */
  test("details why one plus one is not three (0pts)") {
    assertNotEquals(1 + 1, 3) // Fix me, please!                            TODO
  }

  /**
   * Exceptional behavior of a methods can be tested using a try/catch
   * and a failed assertion.
   *
   * In the following example, we test the fact that the method `intNotZero`
   * throws an `IllegalArgumentException` if its argument is `0`.
   */
   test("intNotZero throws an exception if its argument is 0") {
     try
       intNotZero(0)
       fail("No exception has been thrown")
     catch
       case e: IllegalArgumentException => ()
   }

   def intNotZero(x: Int): Int =
     if x == 0 then
       throw IllegalArgumentException("zero is not allowed")
     else x

  /**
   * Now we finally write some tests for the list functions that have to be
   * implemented for this assignment. We fist import all members of the
   * `List` object.
   */
  import Lists.*


  /**
   * We only provide two very basic tests for you. Write more tests to make
   * sure your `sum` and `max` methods work as expected.
   *
   * In particular, write tests for corner cases: negative numbers, zeros,
   * empty lists, lists with repeated elements, etc.
   *
   * It is allowed to have multiple `assert` statements inside one test,
   * however it is recommended to write an individual `test` statement for
   * every tested aspect of a method.
   */
  test("sum of a few numbers (10pts)") {                                 // TODO
    assertEquals(sum(List(1,2,0)), 3)
  }

  test("max of a few numbers (10pts)") {
    assertEquals(max(List(3, 7, 2)), 7)
  }

  test("sum of empty list is zero (10pts)") {
    assertEquals(sum(List()), 0)
  }

  test("sum of positive and negative integers (10pts)") {
    assertEquals(sum(List(1, -2, 3, -4)), -2)
  }

  test("sum of negative integers only (10pts)") {
    assertEquals(sum(List(-1, -2, -3, -4)), -10)
  }

  test("max of empty list throws NoSuchElementException (10pts)") {
    try
      max(List())
      fail("No exception has been thrown")
    catch
      case _: NoSuchElementException =>
        println("NoSuchElementException thrown successfully")
  }

  test("max of positive, negative integers, and zero (10pts)") {
    assertEquals(max(List(3, -7, 0)), 3)
  }

  test("max of negative integers only (10pts)") {
    assertEquals(max(List(-3, -7, -2)), -2)
  }

  test("max of list with repeated integers (10pts)") {
    assertEquals(max(List(3, 7, 2, 7, 2, 3, 2, 3)), 7)
  }


  import scala.concurrent.duration.*
  override val munitTimeout = 1.seconds
