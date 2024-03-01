object FoldLeftTask:
  def computeAverage(numbers: List[Int]): Double =
    val sum = numbers.foldLeft(0 /* accumulator */){_+_ /* Folding function */ }
    if (numbers.length <= 0) 0 else sum.toDouble/numbers.length

  def maximum(numbers: List[Int]) =
    numbers.foldLeft(Int.MinValue/* accumulator */){ /* folding function */
      (max, n) => if n >= max then n else max
    }

  def reverse[A](numbers: List[A]) =
    numbers.foldLeft[List[A]](Nil/* accumulator */){ /* folding function */
      (rev, n) => n :: rev
    }

  @main
  def main() =
    val numbers1 = List(1,2,3,4)
    val numbers2 = List(1,3,4)
    val numbers3 = List(1,3,0,4,5,2)
    println(computeAverage(List(1,2,3,4)))
    println(computeAverage(List(1,3,4)))
    println(maximum(numbers3))
    println(reverse(numbers3))
