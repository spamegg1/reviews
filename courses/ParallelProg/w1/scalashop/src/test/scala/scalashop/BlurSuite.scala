package scalashop

import java.util.concurrent.*
import scala.collection.*

class BlurSuite extends munit.FunSuite:
  def check(x: Int, y: Int, expected: Int)(using dst: Img) =
    assert(dst(x, y) == expected, s"(destination($x, $y) should be $expected)")

  test("boxBlurKernel should correctly handle radius 0"):
    val src = new Img(5, 5)
    for
      x <- 0 until 5
      y <- 0 until 5
    do src(x, y) = rgba(x, y, x + y, math.abs(x - y))
    for
      x <- 0 until 5
      y <- 0 until 5
    do
      assert(
        boxBlurKernel(src, x, y, 0) == rgba(x, y, x + y, math.abs(x - y)),
        "boxBlurKernel(_,_,0) should be identity."
      )

  test(
    "boxBlurKernel should return the correct value on an interior pixel of a 3x4 image with radius 1"
  ):
    val src = new Img(3, 4)
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8
    src(0, 3) = 50; src(1, 3) = 11; src(2, 3) = 16

    val res = boxBlurKernel(src, 1, 2, 1)

    assert(
      res == 12,
      s"(boxBlurKernel(1, 2, 1) should be 12, but it's ${res})"
    )

  test(
    "HorizontalBoxBlur.blur with radius 1 should correctly blur the entire 3x3 image"
  ):
    val w = 3
    val h = 3
    val src = new Img(w, h)
    given dst: Img = new Img(w, h)
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8

    HorizontalBoxBlur.blur(src, dst, 0, 2, 1)

    check(0, 0, 2)
    check(1, 0, 2)
    check(2, 0, 3)
    check(0, 1, 3)
    check(1, 1, 4)
    check(2, 1, 4)
    check(0, 2, 0)
    check(1, 2, 0)
    check(2, 2, 0)

  test("VerticalBoxBlur.blur with radius 2 should correctly blur the entire 4x3 image"):
    val w = 4
    val h = 3
    val src = new Img(w, h)
    given dst: Img = new Img(w, h)
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2; src(3, 0) = 9
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5; src(3, 1) = 10
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8; src(3, 2) = 11

    VerticalBoxBlur.blur(src, dst, 0, 4, 2)

    check(0, 0, 4)
    check(1, 0, 5)
    check(2, 0, 5)
    check(3, 0, 6)
    check(0, 1, 4)
    check(1, 1, 5)
    check(2, 1, 5)
    check(3, 1, 6)
    check(0, 2, 4)
    check(1, 2, 5)
    check(2, 2, 5)
    check(3, 2, 6)

  test(
    "HorizontalBoxBlur.parBlur with radius 2 should correctly blur the entire 4x3 image"
  ):
    val w = 4
    val h = 3
    val src = new Img(w, h)
    given dst: Img = new Img(w, h)
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2; src(3, 0) = 9
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5; src(3, 1) = 10
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8; src(3, 2) = 11

    HorizontalBoxBlur.parBlur(src, dst, 3, 2)

    check(0, 0, 4)
    check(1, 0, 5)
    check(2, 0, 5)
    check(3, 0, 6)
    check(0, 1, 4)
    check(1, 1, 5)
    check(2, 1, 5)
    check(3, 1, 6)
    check(0, 2, 4)
    check(1, 2, 5)
    check(2, 2, 5)
    check(3, 2, 6)

  test(
    "VerticalBoxBlur.parBlur with radius 2 should correctly blur the entire 4x3 image with 4 tasks"
  ):
    val w = 4
    val h = 3
    val src = new Img(w, h)
    given dst: Img = new Img(w, h)
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2; src(3, 0) = 9
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5; src(3, 1) = 10
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8; src(3, 2) = 11

    VerticalBoxBlur.parBlur(src, dst, 4, 2)

    check(0, 0, 4)
    check(1, 0, 5)
    check(2, 0, 5)
    check(3, 0, 6)
    check(0, 1, 4)
    check(1, 1, 5)
    check(2, 1, 5)
    check(3, 1, 6)
    check(0, 2, 4)
    check(1, 2, 5)
    check(2, 2, 5)
    check(3, 2, 6)

  test(
    "VerticalBoxBlur.parBlur with radius 2 should correctly blur the entire 4x3 image with 3 tasks"
  ):
    val w = 4
    val h = 3
    val src = new Img(w, h)
    given dst: Img = new Img(w, h)
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2; src(3, 0) = 9
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5; src(3, 1) = 10
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8; src(3, 2) = 11

    VerticalBoxBlur.parBlur(src, dst, 3, 2)

    check(0, 0, 4)
    check(1, 0, 5)
    check(2, 0, 5)
    check(3, 0, 6)
    check(0, 1, 4)
    check(1, 1, 5)
    check(2, 1, 5)
    check(3, 1, 6)
    check(0, 2, 4)
    check(1, 2, 5)
    check(2, 2, 5)
    check(3, 2, 6)
