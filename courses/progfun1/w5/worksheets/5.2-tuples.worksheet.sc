val xs = List('a', 'b', 'c', 'd')

extension [T](xs: List[T])
  def splitAt(n: Int) = (xs.take(n), xs.drop(n))

xs.splitAt(2)
