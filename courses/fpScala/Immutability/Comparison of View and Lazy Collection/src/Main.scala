object Main:
   @main
   def main() =
      val numbers = 1 to 1000

      // using a standard, eager collection
      val eagerResult = numbers
        .map(n => n * n) // Squaring operation
        .filter(n => n % 2 == 0) // Filtering even numbers
        .take(5) // Taking first 5
        .toList // Forcing evaluation

      // Using view
      val viewResult = numbers.view
        .map(n => n * n) // Squaring operation
        .filter(n => n % 2 == 0) // Filtering even numbers
        .take(5) // Taking first 5
        .toList // Forcing evaluation

      println(s"View Result: $viewResult")

      // Using LazyList
      lazy val lazyListResult: LazyList[Int] = numbers.to(LazyList)
        .map(n => n * n)
        .filter(n => n % 2 == 0)
        .take(5)

      println(s"LazyList Result: ${lazyListResult.toList}")