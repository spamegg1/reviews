package recfun

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do print(s"${pascal(col, row)} ")
      println()

  /** Exercise 1
    */
  def pascal(c: Int, r: Int): Int = // TODO
    (c, r) match
      case (_, 0)      => 1
      case (0, _)      => 1
      case _ if c == r => 1
      case _           => pascal(c - 1, r - 1) + pascal(c, r - 1)

  /** Exercise 2
    */
  def balance(chars: List[Char]): Boolean = // TODO
    @annotation.tailrec
    def helper(chars: List[Char], count: Int): Boolean =
      if chars.isEmpty
      then count == 0
      else
        chars.head match
          case '('               => helper(chars.tail, count + 1)
          case ')' if count > 0  => helper(chars.tail, count - 1)
          case ')' if count <= 0 => false
          case _                 => helper(chars.tail, count)
    helper(chars, 0)

  /** Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = // TODO
    // def helper(money: Int, coins: List[Int], acc: Int): Int =
    //   (money, coins) match
    //     case (0, _)   => acc
    //     case (_, Nil) => acc
    //     case (_, head :: tail) => money compare head match
    //     case -1 => acc
    //     case 0  => acc + 1
    //     case 1  => helper(money - head, coins, acc) + helper(money, tail, acc)
    // helper(money, coins.sorted, 0)

    // simpler solution. Why does it work for unsorted???
    if money == 0 then 1
    else if money < 0 || coins.isEmpty then 0
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
