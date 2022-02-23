package scalashop

import org.scalameter.*

object VerticalBoxBlurRunner:

  val standardConfig = config(
    Key.exec.minWarmupRuns := 5,
    Key.exec.maxWarmupRuns := 10,
    Key.exec.benchRuns := 10,
    Key.verbose := false
  ) withWarmer(Warmer.Default())

  def main(args: Array[String]): Unit =
    val radius = 3
    val width = 1920
    val height = 1080
    val src = Img(width, height)
    val dst = Img(width, height)
    val seqtime = standardConfig measure {
      VerticalBoxBlur.blur(src, dst, 0, width, radius)
    }
    println(s"sequential blur time: $seqtime")

    val numTasks = 32
    val partime = standardConfig measure {
      VerticalBoxBlur.parBlur(src, dst, numTasks, radius)
    }
    println(s"fork/join blur time: $partime")
    println(s"speedup: ${seqtime.value / partime.value}")


/** A simple, trivially parallelizable computation. */
object VerticalBoxBlur extends VerticalBoxBlurInterface:

  /** Blurs the columns of the source image `src` into the destination image
   *  `dst`, starting with `from` and ending with `end` (non-inclusive).
   *
   *  Within each column, `blur` traverses the pixels by going from top to
   *  bottom.
   */
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit =
    // TODO implement this method using the `boxBlurKernel` method
    var x: Int = from
    var y: Int = 0

    while
      x < end
    do
      while
        y < src.height
      do
        dst(x, y) = boxBlurKernel(src, x, y, radius)
        y = y + 1                                           // go to pixel below

      x = x + 1                                             // go to next column
      y = 0                                                    // go back to top

  /** Blurs the columns of the source image in parallel using `numTasks` tasks.
   *
   *  Parallelization is done by stripping the source image `src` into
   *  `numTasks` separate strips, where each strip is
   *  composed of some number of columns.
   */
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit =
    // TODO implement using the `task` construct and the `blur` method
    // autograder complained about this being 0
    val stripSize: Int = math.max(src.width / numTasks, 1)
    for
      from <- 0 until src.width by stripSize
    do
      val end: Int = math.min(from + stripSize, src.width)
      val mytask = task {blur(src, dst, from, end, radius)}
      mytask.join()
