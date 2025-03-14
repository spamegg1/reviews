object FunctionsReturningFunctionsTask:
  // Implement a function `filterList` which returns another function.
  // You can use `filter` method in the implementation.
  def filterList[A](pred: A => Boolean)/* Put the arguments of the function here */ =
    /* Put implementation here */
    (l: List[A]) => l.filter(pred)

  @main
  def main(): Unit =
    val numbers1 = List()
    val numbers2 = List(-1,0,1)
    val numbers3 = List(1,2,3,4,5,6)
    val evenElements = filterList[Int](x => x % 2 == 0)
    println(evenElements(numbers1))
    println(evenElements(numbers2))
    println(evenElements(numbers3))

    List(numbers1, numbers2, numbers3)
      .map(evenElements)
      .foreach(println)
