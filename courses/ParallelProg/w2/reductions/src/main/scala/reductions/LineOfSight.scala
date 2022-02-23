package reductions

import org.scalameter.*

object LineOfSightRunner:

  val standardConfig = config(
    Key.exec.minWarmupRuns := 40,
    Key.exec.maxWarmupRuns := 80,
    Key.exec.benchRuns := 100,
    Key.verbose := false
  ) withWarmer(Warmer.Default())

  def main(args: Array[String]): Unit =
    val length = 10000000
    val input = (0 until length).map(_ % 100 * 1.0f).toArray
    val output = new Array[Float](length + 1)
    val seqtime = standardConfig measure {
      LineOfSight.lineOfSight(input, output)
    }
    println(s"sequential time: $seqtime")

    val partime = standardConfig measure {
      LineOfSight.parLineOfSight(input, output, 10000)
    }
    println(s"parallel time: $partime")
    println(s"speedup: ${seqtime.value / partime.value}")

enum Tree(val maxPrevious: Float):
  case Node(left: Tree, right: Tree)
    extends Tree(left.maxPrevious.max(right.maxPrevious))
  case Leaf(from: Int, until: Int, override val maxPrevious: Float)
    extends Tree(maxPrevious)

object LineOfSight extends LineOfSightInterface:

  def lineOfSight(input: Array[Float], output: Array[Float]): Unit =     // TODO
    output(0) = 0
    var max: Float = 0
    var i: Int = 1
    while
      i < input.length
    do
      max = math.max(input(i) / i, max)
      output(i) = max
      i = i + 1

  /** Traverses the specified part of the array and returns the maximum angle.
   */
  def upsweepSequential(input: Array[Float], from: Int, until: Int): Float =
    var max: Float = 0                                                   // TODO
    var i: Int = from
    while
      i < until
    do
      max = math.max(max, input(i) / i)
      i = i + 1
    max

  /** Traverses the part of the array starting at `from` and until `end`, and
   *  returns the reduction tree for that part of the array.
   *
   *  The reduction tree is a `Tree.Leaf` if the length of the specified part of
   *  the array is smaller or equal to `threshold`, and a `Tree.Node` otherwise.
   *  If the specified part of the array is longer than `threshold`, then the
   *  work is divided and done recursively in parallel.
   */
  def upsweep(input: Array[Float], from: Int, end: Int,
              threshold: Int): Tree =                                    // TODO
    if end - from <= threshold then      // if I use < only, I get stackOverFlow
      Tree.Leaf(from, end, upsweepSequential(input, from, end))
    else
      val mid = from + (end - from) / 2
      val (left, right) = parallel(upsweep(input, from, mid, threshold),
                                   upsweep(input, mid, end, threshold))
      Tree.Node(left, right)

  /** Traverses the part of the `input` array starting at `from` and until
   *  `until`, and computes the maximum angle for each entry of
   *  the output array, given the `startingAngle`.
   */
  def downsweepSequential(input: Array[Float], output: Array[Float],     // TODO
                          startingAngle: Float, from: Int, until: Int): Unit =
    var i: Int = if from == 0 then 1 else from
    var max: Float = startingAngle
    while
      i < until
    do
      max = math.max(max, input(i) / i)
      output(i) = max
      i = i + 1

  /** Pushes the maximum angle in the prefix of the array to each leaf of the
   *  reduction `tree` in parallel, and then calls `downsweepSequential`
   *  to write the `output` angles.
   */
  def downsweep(input: Array[Float], output: Array[Float],
                startingAngle: Float, tree: Tree): Unit =
    tree match
      case Tree.Leaf(from, until, _) =>
        downsweepSequential(input, output, startingAngle, from, until)
      case Tree.Node(left, right) =>
        parallel(downsweep(input, output, startingAngle, left),
                 downsweep(input, output,
                           math.max(startingAngle, left.maxPrevious), right))

  /** Compute the line-of-sight in parallel. */
  def parLineOfSight(input: Array[Float], output: Array[Float],
                     threshold: Int): Unit =                             // TODO
    // had to change the from output to 1 instead of 0 to pass all the tests!
    val tree: Tree = upsweep(input, 1, input.length, threshold)
    downsweep(input, output, 0, tree)
