package reductions

class ParenthesesBalancingSuite extends munit.FunSuite:
  import ParallelParenthesesBalancing.*

  // sequential
  def check(input: String, expected: Boolean) =
    assertEquals(balance(input.toArray), expected, s"balance($input) should be $expected")

  test("balance should work for empty string") {
    check("", true)
  }

  test("balance should work for string of length 1") {
    check("(", false)
    check(")", false)
    check(".", true)
  }

  test("balance should work for string of length 2") {
    check("()", true)
    check(")(", false)
    check("((", false)
    check("))", false)
    check(".)", false)
    check(".(", false)
    check("(.", false)
    check(").", false)
  }

  // parallel
  def checkPar(input: String, expected: Boolean): Unit =
    assertEquals(
      parBalance(input.toArray, 2),
      expected,
      s"parBalance($input) should be $expected"
    )

  test("parBalance should work for empty string") {
    checkPar("", true)
  }

  test("parBalance should work for string of length 1") {
    checkPar("(", false)
    checkPar(")", false)
    checkPar(".", true)
  }

  test("parBalance should work for string of length 2") {
    checkPar("()", true)
    checkPar(")(", false)
    checkPar("((", false)
    checkPar("))", false)
    checkPar(".)", false)
    checkPar(".(", false)
    checkPar("(.", false)
    checkPar(").", false)
  }

  // sequential
  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toCharArray))
  }

  test("balance: `I told him...` is balanced") {
    val sentence = "I told him (that it's not (yet) done).\n(But he didn't listen)"
    check(sentence, true)
  }

  test("balance: ':-)' is unbalanced") {
    check(":-)", false)
  }

  test("balance: counting is not enough") {
    check("())(", false)
  }

  // parallel
  test("parBalance: '(if (zero? x) max (/ 1 x))' is balanced") {
    checkPar("(if (zero? x) max (/ 1 x))", true)
  }

  test("parBalance: 'I told him ...' is parBalanced") {
    val sentence = "I told him (that it's not (yet) done).\n(But he wasn't listening)"
    checkPar(sentence, true)
  }

  test("parBalance: ':-)' is unBalanced") {
    checkPar(":-)", false)
  }

  test("parBalance: counting is not enough") {
    checkPar("())(", false)
  }

  test("parBalance: should work for string of length 2 and threshold 1") {
    assert(parBalance("()".toCharArray, 1))
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
