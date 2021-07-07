package org.epfl.sierpinski

import doodle.core.Color
import doodle.image.Image
import doodle.image.syntax.*
import doodle.java2d.*
import cats.instances.list.*

// Depth of the Sierpinski triangle
val iterations = 6
// Size of a single triangle in the whole picture
val resolution = 10
// Image showing a single triangle
val unit: Image =
  Image
    .triangle(width = resolution.toDouble, height = resolution.toDouble)
    .fillColor(Color.black)

// Implementation based on collections
@main def collections(): Unit =
  val n = 2 << (iterations - 1)
  val images =
    ((n - 1) to 0 by -1).flatMap { i =>
      (0 until (n - i))
        .filter { j => (j & i) == 0 }
        .map { j =>
          unit.at((i / 2.0 + j) * resolution, i * resolution.toDouble)
        }
    }

  val image = images.toList.allOn
  image.draw()
end collections

// Imperative implementation
@main def imperative(): Unit =
  import scala.collection.mutable
  val images = mutable.ListBuffer[Image]()
  val n = 2 << (iterations - 1)

  ((n - 1) to 0 by -1).foreach { i =>
    (0 until (n - i)).foreach { j =>
      if (j & i) == 0 then
      images += unit.at((i / 2.0 + j) * resolution, i * resolution.toDouble)
    }
  }

  val image = images.toList.allOn
  image.draw()
end imperative

// Recursive implementation
@main def recursion(): Unit =
  def loop(n: Int): Image =
    if n == 0 then
      unit
    else
      val previous = loop(n - 1)
      previous.above(
        previous.beside(previous)
      )

  val image = loop(iterations)
  image.draw()
end recursion
