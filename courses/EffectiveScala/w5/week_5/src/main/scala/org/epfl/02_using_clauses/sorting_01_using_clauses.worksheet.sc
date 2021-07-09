// We're using the merge sort algorithm to implement sorting

// Have a look at: https://www.scala-lang.org/api/current/scala/math/Ordering.html
import math.Ordering

def sort[A](xs: List[A])(using ord: Ordering[A]): List[A] =
  def merge(xs: List[A], ys: List[A]): List[A] =
    (xs, ys) match
      case (left, Nil) => left
      case (Nil, right) => right
      case (x :: xsTail, y :: ysTail) =>
        if ord.lt(x, y) then    // a generic comparison
          x :: merge(xsTail, ys)
        else
          y :: merge(xs, ysTail)
  end merge

  val n = xs.length / 2
  if n == 0 then
    xs
  else
    val (left, right) = xs.splitAt(n)
    merge(sort(left), sort(right))
end sort

// Let's do some sorting...
val xs = List(-5, 6, 3, 2, 7)
val strings = List("apple", "pear", "orange", "pineapple")

sort(xs)
sort(strings)

// Sort in reverse order is a breeze
sort(xs)(using Ordering[Int].reverse)
sort(strings)(using Ordering[String].reverse)
