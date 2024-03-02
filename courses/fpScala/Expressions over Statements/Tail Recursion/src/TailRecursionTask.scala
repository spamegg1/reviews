import scala.annotation.tailrec

object TailRecursionTask:

  def reverseList[A](xs: List[A]): List[A] =
    /* TODO */
    @tailrec
    def go(xs: List[A], acc: List[A]): List[A] = xs match
      case ::(head, next) => go(next, head :: acc)
      case Nil => acc
    go(xs, Nil)


  def sumOfDigits(n: Int): Int =
    /* TODO */
    @tailrec
    def go(digits: List[Int], acc: Int): Int = digits match
      case ::(head, next) => go(next, head + acc)
      case Nil => acc
    go(n.toString.toList.map(_.asDigit), 0)

  @main
  def main(): Unit =
    val list = List(1,2,3,4)
    println(s"${reverseList(list).mkString(",")}") // 4,3,2,1
    println(s"${sumOfDigits(1234)}") // 10
    println(s"${sumOfDigits(9078)}") // 24
