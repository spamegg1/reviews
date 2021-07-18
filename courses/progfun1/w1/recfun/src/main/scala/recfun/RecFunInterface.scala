package recfun

/**
 * The interface used by the grading infrastructure. Do not change signatures
 * or your submission will fail with a NoSuchMethodError.
 */
trait RecFunInterface:
  def pascal(c: Int, r: Int): Int
  def balance(chars: List[Char]): Boolean
  def countChange(money: Int, coins: List[Int]): Int
