package scalashop

import java.util.concurrent.*
import scala.util.DynamicVariable
import org.scalameter.*

/** The value of every pixel is represented as a 32 bit integer. */
type RGBA = Int

/** Returns the red component. */
def red(c: RGBA): Int = (0xff000000 & c) >>> 24

/** Returns the green component. */
def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

/** Returns the blue component. */
def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

/** Returns the alpha component. */
def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

/** Used to create an RGBA value from separate components. */
def rgba(r: Int, g: Int, b: Int, a: Int): RGBA =
  (r << 24) | (g << 16) | (b << 8) | (a << 0)

/** Restricts the integer into the specified range. */
def clamp(v: Int, min: Int, max: Int): Int =
  if v < min then min else if v > max then max else v

/** Image is a two-dimensional matrix of pixel values. */
class Img(val width: Int, val height: Int, private val data: Array[RGBA]):
  def this(w: Int, h: Int) = this(w, h, new Array(w * h))
  def apply(x: Int, y: Int): RGBA = data(y * width + x)
  def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c

/** Computes the blurred RGBA value of a single pixel of the input image. */
def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = // TODO
  // implement using while loops
  /* declare variables for the 4 averages and neighbor count */
  var rnew, gnew, bnew, anew, nghCount = 0

  /* define bounds for the while loops by clamping down on x -+ radius, y -+ radius */
  val xmin: Int = clamp(x - radius, 0, src.width - 1)
  val xmax: Int = clamp(x + radius, 0, src.width - 1)
  val ymin: Int = clamp(y - radius, 0, src.height - 1)
  val ymax: Int = clamp(y + radius, 0, src.height - 1)

  /* define variables for the while loops */
  var i: Int = xmin
  var j: Int = ymin

  /* get neighbors within clamped borders */
  while i <= xmax do
    while j <= ymax do
      val neighbor: RGBA = src(i, j)
      nghCount = nghCount + 1

      /* add neighbor's RGBA values to accumulated 4 channels */
      rnew = rnew + red(neighbor)
      gnew = gnew + green(neighbor)
      bnew = bnew + blue(neighbor)
      anew = anew + alpha(neighbor)
      j = j + 1

    i = i + 1
    j = ymin // back to leftmost

  /* average the 4 channels and create a new RGBA value out of them */
  rgba(rnew / nghCount, gnew / nghCount, bnew / nghCount, anew / nghCount)

val forkJoinPool = ForkJoinPool()

abstract class TaskScheduler:
  def schedule[T](body: => T): ForkJoinTask[T]
  def parallel[A, B](taskA: => A, taskB: => B): (A, B) =
    val right = task(taskB)
    val left = taskA
    (left, right.join())

class DefaultTaskScheduler extends TaskScheduler:
  def schedule[T](body: => T): ForkJoinTask[T] =
    val t = new RecursiveTask[T]:
      def compute = body

    Thread.currentThread match
      case wt: ForkJoinWorkerThread => t.fork()
      case _                        => forkJoinPool.execute(t)

    t

val scheduler = DynamicVariable[TaskScheduler](DefaultTaskScheduler())

def task[T](body: => T): ForkJoinTask[T] = scheduler.value.schedule(body)

def parallel[A, B](taskA: => A, taskB: => B): (A, B) =
  scheduler.value.parallel(taskA, taskB)

def parallel[A, B, C, D](
    taskA: => A,
    taskB: => B,
    taskC: => C,
    taskD: => D
): (A, B, C, D) =
  val ta = task(taskA)
  val tb = task(taskB)
  val tc = task(taskC)
  val td = taskD
  (ta.join(), tb.join(), tc.join(), td)
