object PartialFunctionExample:
  val division: PartialFunction[(Int, Int), Double] = {
    /* Define a partial function to handle division by zero */
    case (n, m) if m != 0 => n.toDouble / m
  }

  // Test the partial function
  val values = List((10, 2), (8, 0), (6, 3))

  values.foreach { case (x, y) =>
    if (/* Write a condition to check if division is defined for the arguments */
      division.isDefinedAt(x, y)
    ) {
      println(s"Result of $x / $y = ${division((x, y))}")
    } else {
      println(s"Cannot divide $x by $y")
    }
  }
