package reductions

// Interfaces used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.

trait LineOfSightInterface:
  def lineOfSight(input: Array[Float], output: Array[Float]): Unit
  def upsweepSequential(input: Array[Float], from: Int, until: Int): Float
  def upsweep(input: Array[Float], from: Int, end: Int, threshold: Int): Tree
  def downsweepSequential(input: Array[Float], output: Array[Float], startingAngle: Float, from: Int, until: Int): Unit
  def downsweep(input: Array[Float], output: Array[Float], startingAngle: Float, tree: Tree): Unit
  def parLineOfSight(input: Array[Float], output: Array[Float], threshold: Int): Unit

trait ParallelCountChangeInterface:
  def countChange(money: Int, coins: List[Int]): Int
  def parCountChange(money: Int, coins: List[Int], threshold: (Int, List[Int]) => Boolean): Int
  def moneyThreshold(startingMoney: Int): (Int, List[Int]) => Boolean
  def totalCoinsThreshold(totalCoins: Int): (Int, List[Int]) => Boolean
  def combinedThreshold(startingMoney: Int, allCoins: List[Int]): (Int, List[Int]) => Boolean

trait ParallelParenthesesBalancingInterface:
  def balance(chars: Array[Char]): Boolean
  def parBalance(chars: Array[Char], threshold: Int): Boolean
