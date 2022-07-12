val nums = List(1, 2, 3, 4, 5, 6)
nums partition (_ % 2 != 0)
nums span (_ % 2 != 0)

def pack[T](xs: List[T]): List[List[T]] = xs match
  case Nil => Nil
  case y :: ys =>
    // without span
    // pack(ys) match
    //   case Nil => (y :: Nil) :: Nil
    //   case z :: zs =>
    //     if y == z.head then (y :: z) :: zs
    //     else (y :: Nil) :: z :: zs
    // with span
    val (more, rest) = ys.span(_ == y)
    (y :: more) :: pack(rest)

val elems = List("a", "a", "a", "b", "c", "c", "a")
pack(elems)

def encode[T](xs: List[T]): List[(T, Int)] =
  pack(xs).map(list => (list.head, list.length))

encode(elems)
