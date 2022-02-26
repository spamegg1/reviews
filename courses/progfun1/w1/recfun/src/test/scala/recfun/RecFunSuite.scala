package recfun

class RecFunSuite extends munit.FunSuite:
  import RecFun.*

  // ------ balance tests -----------------------------------------------------

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance(
      "I told him (that it's not (yet) done).\n(But he wasn't listening)"
      .toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  // ------ countChange tests -------------------------------------------------

  test("countChange: example given in instructions") {
    assertEquals(countChange(4,List(1,2)), 3)
  }

  test("countChange: sorted CHF") {
    assertEquals(countChange(300,List(5,10,20,50,100,200,500)), 1022)
  }

  test("countChange: no pennies") {
    assertEquals(countChange(301,List(5,10,20,50,100,200,500)), 0)
  }

  test("countChange: unsorted CHF") {
    assertEquals(countChange(300,List(500,5,50,100,20,200,10)), 1022)
  }

  // ------ pascal tests ------------------------------------------------------

  test("pascal: col=0,row=2") {
    assertEquals(pascal(0, 2), 1)
  }

  test("pascal: col=1,row=2") {
    assertEquals(pascal(1, 2), 2)
  }

  test("pascal: col=1,row=3") {
    assertEquals(pascal(1, 3), 3)
  }

  import scala.collection.mutable.Map                                    // TODO
  var factMemo: Map[Int, Int] = Map(0 -> 1)

  def fact(n: Int): Int =
    require(n >= 0)
    factMemo.get(n) match
      case Some(x) => x
      case None =>
        val x = n * fact(n - 1)
        factMemo += n -> x
        x

  def choose(c: Int, r: Int) =
    require(c <= r)
    fact(r) / (fact(c) * fact(r - c))

  test(s"pascal: col=0 to 10 row=0 to 10") {
    for r <- 0 to 10 do
      for c <- 0 to r do
        assertEquals(pascal(c, r), choose(c, r))
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
