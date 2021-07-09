// We're using the merge sort algorithm to implement sorting

def sort[A](xs: List[A])(lessThan: (A, A) => Boolean): List[A] =
  def merge(xs: List[A], ys: List[A]): List[A] =
    (xs, ys) match
      case (left, Nil) => left
      case (Nil, right) => right
      case (x :: xsTail, y :: ysTail) =>
        if lessThan(x, y) then    // a generic comparison
          x :: merge(xsTail, ys)
        else
          y :: merge(xs, ysTail)
  end merge

  val n = xs.length / 2
  if n == 0 then
    xs
  else
    val (left, right) = xs.splitAt(n)
    merge(sort(left)(lessThan), sort(right)(lessThan))
end sort

// Let's do some sorting...
val xs = List(-5, 6, 3, 2, 7)
val strings = List("apple", "pear", "orange", "pineapple")

sort(xs)((x: Int, y: Int) => x < y)
sort(strings)((s1, s2) => s1.compareTo(s2) < 0)
