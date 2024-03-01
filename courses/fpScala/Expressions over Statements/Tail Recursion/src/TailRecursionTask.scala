import scala.annotation.tailrec

object TailRecursionTask:

  def reverseList[A](xs: List[A]): List[A] =
    @tailrec
    def go/* TODO */

  def sumOfDigits(n: Int): Int =
    @tailrec
    def go/* TODO */

  @main
  def main(): Unit =
    val list = List(1,2,3,4)
    println(s"${reverseList(list).mkString(",")}") // 4,3,2,1
    println(s"${sumOfDigits(1234)}") // 10
    println(s"${sumOfDigits(9078)}") // 24


