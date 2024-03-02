object WhatIsExpressionTask:
  def abs(n: Int): Int =
    /* Rewrite this code into an expression
    var result = 0
    if (n < 0) {
      result = -n
    }
    else {
      result = n
    }
    result
    */
    if n < 0 then -n else n

  def concatStrings(strings: List[String]): String =
    /* Rewrite this code into an expression
    var result = ""
    for (str <- strings) {
      result += str
    }
    result
    */
    strings.mkString

  def sumOfAbsoluteDifferences(xs: Array[Int], ys: Array[Int]): Int =
    /* Implement a function which finds the absolute difference
     of the corresponding elements of the two arrays */
    xs.zip(ys).map((x, y) => math.abs(x - y)).sum


  def longestCommonPrefix(strings: List[String]): String =
    /* Implement a function which finds the longest common prefix of the strings */
    if strings.isEmpty then ""
    else strings
      .view
      .minBy(_.length)
      .zipWithIndex
      .takeWhile((char, index) => strings.forall(s => s(index) == char))
      .map(_._1)
      .mkString
