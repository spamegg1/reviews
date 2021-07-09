// We're using the merge sort algorithm to implement sorting

def sort(xs: List[Int]): List[Int] =
  def merge(xs: List[Int], ys: List[Int]): List[Int] =
    (xs, ys) match
      case (left, Nil) => left
      case (Nil, right) => right
      case (x :: xsTail, y :: ysTail) =>
        if x < y then
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
sort(xs)
