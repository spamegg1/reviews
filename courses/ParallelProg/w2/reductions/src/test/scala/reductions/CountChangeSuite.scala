package reductions

class CountChangeSuite extends munit.FunSuite:
  import ParallelCountChange.*

  def check(money: Int, coins: List[Int], expected: Int) =
    assertEquals(
      countChange(money, coins),
      expected,
      s"countChange($money, $coins) should be $expected"
    )

  test("countChange should return 0 for money < 0"):
    check(-1, List(), 0)
    check(-1, List(1, 2, 3), 0)
    check(-Int.MinValue, List(), 0)
    check(-Int.MinValue, List(1, 2, 3), 0)

  test("countChange should return 1 when money == 0"):
    check(0, List(), 1)
    check(0, List(1, 2, 3), 1)
    check(0, List.range(1, 100), 1)

  test("countChange should return 0 for money > 0 and coins = List()"):
    check(1, Nil, 0)
    check(Int.MaxValue, Nil, 0)

  test("countChange should work when there is only one coin"):
    check(1, List(1), 1)
    check(2, List(1), 1)
    check(1, List(2), 0)
    check(Int.MaxValue, List(Int.MaxValue), 1)
    check(Int.MaxValue - 1, List(Int.MaxValue), 0)

  test("countChange should work for multi-coins"):
    check(50, List(1, 2, 5, 10), 341)
    check(250, List(1, 2, 5, 10, 20, 50), 177863)

  /** parCountChange */
  def checkPar(money: Int, coins: List[Int], expected: Int) =
    assertEquals(
      parCountChange(money, coins, combinedThreshold(money, coins)),
      expected,
      s"parCountChange($money, $coins) should be $expected"
    )

  test("parCountChange should return 0 for money < 0"):
    checkPar(-1, List(), 0)
    checkPar(-1, List(1, 2, 3), 0)
    checkPar(-Int.MinValue, List(), 0)
    checkPar(-Int.MinValue, List(1, 2, 3), 0)

  test("parCountChange should return 1 when money == 0"):
    checkPar(0, List(), 1)
    checkPar(0, List(1, 2, 3), 1)
    checkPar(0, List.range(1, 100), 1)

  test("parCountChange should return 0 for money > 0 and coins = List()"):
    checkPar(1, Nil, 0)
    checkPar(Int.MaxValue, Nil, 0)

  test("parCountChange should work when there is only one coin"):
    checkPar(1, List(1), 1)
    checkPar(2, List(1), 1)
    checkPar(1, List(2), 0)
    checkPar(Int.MaxValue, List(Int.MaxValue), 1)
    checkPar(Int.MaxValue - 1, List(Int.MaxValue), 0)

  test("parCountChange should work for multi-coins"):
    checkPar(50, List(1, 2, 5, 10), 341)
    checkPar(250, List(1, 2, 5, 10, 20, 50), 177863)

  /* Various tests */
  test("countChange: example given in instructions"):
    assertEquals(countChange(4, List(1, 2)), 3)

  test("countChange: sorted CHF"):
    assertEquals(countChange(300, List(5, 10, 20, 50, 100, 200, 500)), 1022)

  test("countChange: no pennies"):
    assertEquals(countChange(301, List(5, 10, 20, 50, 100, 200, 500)), 0)

  test("countChange: unsorted CHF"):
    assertEquals(countChange(300, List(500, 5, 50, 100, 20, 200, 10)), 1022)

  test("parCountChange(moneyThreshold): example given in instructions"): // money
    assertEquals(parCountChange(4, List(1, 2), moneyThreshold(4)), 3)

  test("parCountChange(moneyThreshold): sorted CHF"):
    assertEquals(
      parCountChange(
        300,
        List(5, 10, 20, 50, 100, 200, 500),
        moneyThreshold(300)
      ),
      1022
    )

  test("parCountChange(moneyThreshold): no pennies"):
    assertEquals(
      parCountChange(301, List(5, 10, 20, 50, 100, 200, 500), moneyThreshold(301)),
      0
    )

  test("parCountChange(moneyThreshold): unsorted CHF"):
    assertEquals(
      parCountChange(
        300,
        List(500, 5, 50, 100, 20, 200, 10),
        moneyThreshold(300)
      ),
      1022
    )

  test("parCountChange(totalCoinsThreshold): example given in instructions"): // totalCoin
    assertEquals(parCountChange(4, List(1, 2), totalCoinsThreshold(2)), 3)

  test("parCountChange(totalCoinsThreshold): sorted CHF"):
    assertEquals(
      parCountChange(
        300,
        List(5, 10, 20, 50, 100, 200, 500),
        totalCoinsThreshold(7)
      ),
      1022
    )

  test("parCountChange(totalCoinsThreshold): no pennies"):
    assertEquals(
      parCountChange(
        301,
        List(5, 10, 20, 50, 100, 200, 500),
        totalCoinsThreshold(7)
      ),
      0
    )

  test("parCountChange(totalCoinsThreshold): unsorted CHF"):
    assertEquals(
      parCountChange(
        300,
        List(500, 5, 50, 100, 20, 200, 10),
        totalCoinsThreshold(7)
      ),
      1022
    )

  test("parCountChange(combinedThreshold): example given in instructions"): // combined
    assertEquals(parCountChange(4, List(1, 2), combinedThreshold(4, List(1, 2))), 3)

  test("parCountChange(combinedThreshold): sorted CHF"):
    assertEquals(
      parCountChange(
        300,
        List(5, 10, 20, 50, 100, 200, 500),
        combinedThreshold(300, List(5, 10, 20, 50, 100, 200, 500))
      ),
      1022
    )

  test("parCountChange(combinedThreshold): no pennies"):
    assertEquals(
      parCountChange(
        301,
        List(5, 10, 20, 50, 100, 200, 500),
        combinedThreshold(301, List(5, 10, 20, 50, 100, 200, 500))
      ),
      0
    )

  test("parCountChange(combinedThreshold): unsorted CHF"):
    assertEquals(
      parCountChange(
        300,
        List(500, 5, 50, 100, 20, 200, 10),
        combinedThreshold(300, List(500, 5, 50, 100, 20, 200, 10))
      ),
      1022
    )

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
