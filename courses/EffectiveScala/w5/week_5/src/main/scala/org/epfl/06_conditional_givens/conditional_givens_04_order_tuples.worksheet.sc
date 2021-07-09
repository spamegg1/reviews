
// given orderingList[A](using ord: Ordering[A]): Ordering[List[A]] with
given [A: Ordering]: Ordering[List[A]] with
  def compare(xs: List[A], ys: List[A]): Int =
    (xs, ys) match
      case (Nil, Nil) => 0
      case (Nil, _) => -1
      case (_, Nil) => 1
      case (x :: xs, y :: ys) =>
        val c = summon[Ordering[A]].compare(x, y)
        if c != 0 then
          c
        else
          compare(xs, ys)
end given

given [A: Ordering, B: Ordering]: Ordering[(A, B)] with
  def compare(t1: (A, B), t2: (A, B)): Int =
    val c = summon[Ordering[A]].compare(t1(0), t2(0))
    if c != 0 then
      c
    else
      summon[Ordering[B]].compare(t1(1), t2(1))
end given

def sort[A](xs: List[A])(using ord: Ordering[A]): List[A] =
  def merge(xs: List[A], ys: List[A]): List[A] =
    (xs, ys) match
      case(left, Nil) => left
      case(Nil, right) => right
      case(x :: xsTail, y :: ysTail) =>
        if ord.lt(x, y) then    // a generic comparison
          x::merge(xsTail, ys)
        else
          y :: merge(xs, ysTail)
  end merge

  val n = xs.length / 2
  if (n == 0) then
    xs
  else
    val (left, right) = xs.splitAt(n)
    merge(sort(left), sort(right))
end sort

val addresses = List(
  (2610, "Kouwerheide"),
  (2000, "Halewijllaan"),
  (2060, "Fort VII straat")
)

sort(addresses)
