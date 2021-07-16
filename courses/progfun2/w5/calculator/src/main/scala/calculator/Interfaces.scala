package calculator

// Interfaces used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.

trait TweetLengthInterface:
  def MaxTweetLength: Int
  def tweetRemainingCharsCount(tweetText: Signal[String]): Signal[Int]
  def colorForRemainingCharsCount(remainingCharsCount: Signal[Int]): Signal[String]

trait PolynomialInterface:
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double]

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]]

trait CalculatorInterface:
  def computeValues(
        namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]]

  def eval(expr: Expr, references: Map[String, Signal[Expr]])(using Signal.Caller): Double

