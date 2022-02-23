package reductions

import scala.annotation.*
import org.scalameter.*

object ParallelParenthesesBalancingRunner:

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns := 40,
    Key.exec.maxWarmupRuns := 80,
    Key.exec.benchRuns := 120,
    Key.verbose := false
  ) withWarmer(Warmer.Default())

  def main(args: Array[String]): Unit =
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }

    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }

    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime")
    println(s"speedup: ${seqtime.value / fjtime.value}")

object ParallelParenthesesBalancing
  extends ParallelParenthesesBalancingInterface:

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean =                             // TODO
    @scala.annotation.tailrec
    def helper(left: Int, right: Int, chars: Array[Char]): Boolean =
      if   chars.isEmpty
      then left == right
      else chars.head match
        case ')' if left == right => false
        case ')' if left >  right => helper(left,     right + 1, chars.tail)
        case '('                  => helper(left + 1, right,     chars.tail)
        case _                    => helper(left,     right,     chars.tail)
    helper(0, 0, chars)

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean =          // TODO
    @scala.annotation.tailrec
    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int, Int) =
      if   (idx >= until)
      then (arg1, arg2)
      else chars(idx) match
        case '('              => traverse(idx + 1, until, arg1 + 1, arg2)
        case ')' if arg1 >  0 => traverse(idx + 1, until, arg1 - 1, arg2)
        case ')' if arg1 <= 0 => traverse(idx + 1, until,    0,     arg2 + 1)
        case _                => traverse(idx + 1, until, arg1,     arg2)

    def reduce(from: Int, until: Int): (Int, Int) =                      // TODO
      if   until - from <= threshold
      then traverse(from, until, 0, 0)
      else
        val mid: Int = (from + until) / 2
        val ((l1, r1), (l2, r2)) =
          parallel(reduce(from, mid), reduce(mid, until))
        val left = math.max(l1 - r2, 0)
        val right = math.max(r2 - l1, 0)
        (left + l2, right + r1)

    reduce(0, chars.length) == (0, 0)                                    // TODO

  // For those who want more:
  // Prove that your reduction operator is associative!
