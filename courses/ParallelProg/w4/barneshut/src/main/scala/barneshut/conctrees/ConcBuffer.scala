package barneshut
package conctrees

import scala.reflect.ClassTag
import scala.collection.parallel.CollectionConverters.*
import org.scalameter.*

class ConcBuffer[@specialized(Byte, Char, Int, Long, Float, Double) T: ClassTag](
  val k: Int, private var conc: Conc[T]
) extends Iterable[T]:
  require(k > 0)

  def this() = this(128, Conc.Empty)

  private var chunk: Array[T] = new Array(k)
  private var lastSize: Int = 0

  def iterator: Iterator[T] = Conc.iterator(conc) ++ chunk.iterator.take(lastSize)

  final def +=(elem: T): this.type =
    if lastSize >= k then expand()
    chunk(lastSize) = elem
    lastSize += 1
    this

  final def combine(that: ConcBuffer[T]): ConcBuffer[T] =
    val combinedConc = this.result <> that.result
    this.clear()
    that.clear()
    ConcBuffer(k, combinedConc)

  private def pack(): Unit =
    conc = Conc.appendTop(conc, Conc.Chunk(chunk, lastSize, k))

  private def expand(): Unit =
    pack()
    chunk = new Array(k)
    lastSize = 0

  def clear(): Unit =
    conc = Conc.Empty
    chunk = new Array(k)
    lastSize = 0

  def result: Conc[T] =
    pack()
    conc

object ConcBufferRunner:

  val standardConfig = config(
    Key.exec.minWarmupRuns := 20,
    Key.exec.maxWarmupRuns := 40,
    Key.exec.benchRuns := 60,
    Key.verbose := false
  ).withWarmer(Warmer.Default())

  def main(args: Array[String]): Unit =
    val size = 1000000

    def run(p: Int): Unit =
      val taskSupport = collection.parallel.ForkJoinTaskSupport(
        java.util.concurrent.ForkJoinPool(p))
      val strings = (0 until size).map(_.toString)
      val time = standardConfig measure {
        val parallelized = strings.par
        parallelized.tasksupport = taskSupport
        parallelized.aggregate(ConcBuffer[String])(_ += _, _ combine _).result
      }
      println(s"p = $p, time = ${time.value}")

    run(1)
    run(2)
    run(4)
    run(8)

