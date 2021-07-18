package kmeans
package fun

import scala.collection.Seq
import scala.collection.parallel.ParSeq
import scala.collection.parallel.CollectionConverters.*

abstract sealed trait InitialSelectionStrategy
case object RandomSampling extends InitialSelectionStrategy
case object UniformSampling extends InitialSelectionStrategy
case object UniformChoice extends InitialSelectionStrategy

abstract sealed trait ConvergenceStrategy
case class ConvergedWhenSNRAbove(x: Double) extends ConvergenceStrategy
case class ConvergedAfterNSteps(n: Int) extends ConvergenceStrategy
case class ConvergedAfterMeansAreStill(eta: Double) extends ConvergenceStrategy


class IndexedColorFilter(initialImage: Img,
                         colorCount: Int,
                         initStrategy: InitialSelectionStrategy,
                         convStrategy: ConvergenceStrategy) extends KMeans:

  private var steps = 0

  val points = imageToPoints(initialImage).par
  val means = initializeIndex(colorCount, points).par

  /* The work is done here: */
  private val newMeans = kMeans(points, means, 0.01)

  /* And these are the results exposed */
  def getStatus() = s"Converged after $steps steps."
  def getResult() = indexedImage(initialImage, newMeans)

  private def imageToPoints(img: Img): Seq[Point] =
    for x <- 0 until img.width; y <- 0 until img.height yield
      val rgba = img(x, y)
      Point(red(rgba), green(rgba), blue(rgba))

  private def indexedImage(img: Img, means: ParSeq[Point]) =
    val dst = Img(img.width, img.height)
    val pts = collection.mutable.Set[Point]()

    for x <- 0 until img.width; y <- 0 until img.height yield
      val v = img(x, y)
      var point = Point(red(v), green(v), blue(v))
      point = findClosest(point, means)
      pts += point
      dst(x, y) = rgba(point.x, point.y, point.z, 1d)

    dst

  private def initializeIndex(numColors: Int, points: ParSeq[Point]): Seq[Point] =
    val initialPoints: Seq[Point] =
      initStrategy match
        case RandomSampling =>
          val d: Int = points.size / numColors
          (0 until numColors) map (idx => points(d * idx))
        case UniformSampling =>
          val sep: Int = 32
          (for r <- 0 until 255 by sep; g <- 0 until 255 by sep; b <- 0 until 255 by sep yield {
            def inside(p: Point): Boolean =
              (p.x >= (r.toDouble / 255)) &&
              (p.x <= ((r.toDouble + sep) / 255)) &&
              (p.y >= (g.toDouble / 255)) &&
              (p.y <= ((g.toDouble + sep) / 255)) &&
              (p.z >= (b.toDouble / 255)) &&
              (p.z <= ((b.toDouble + sep) / 255))

            val pts = points.filter(inside(_))
            val cnt = pts.size * 3 * numColors / points.size
            if cnt >= 1 then {
              val d = pts.size / cnt
              (0 until cnt) map (idx => pts(d * idx))
            } else
              Seq()
          }).flatten
        case UniformChoice =>
          val d: Int = math.max(1, (256 / math.cbrt(numColors.toDouble).ceil).toInt)
          for r <- 0 until 256 by d; g <- 0 until 256 by d; b <- 0 until 256 by d yield
            Point(r.toDouble / 256,g.toDouble / 256, b.toDouble / 256)

    val d2 = initialPoints.size.toDouble / numColors
    (0 until numColors) map (idx => initialPoints((idx * d2).toInt))

  private def computeSNR(points: ParSeq[Point], means: ParSeq[Point]): Double =
    var sound = 0.0
    var noise = 0.0

    for point <- points do
      import math.{pow, sqrt}
      val closest = findClosest(point, means)
      sound += sqrt(pow(point.x, 2) + pow(point.y, 2) + pow(point.z, 2))
      noise += sqrt(pow(point.x - closest.x, 2) + pow(point.y - closest.y, 2) + pow(point.z - closest.z, 2))
    sound/noise

  override def converged(eta: Double, oldMeans: ParSeq[Point], newMeans: ParSeq[Point]): Boolean =
    steps += 1
    convStrategy match
      case ConvergedAfterNSteps(n) =>
        steps >= n
      case ConvergedAfterMeansAreStill(eta) =>
        super.converged(eta, oldMeans, newMeans)
      case ConvergedWhenSNRAbove(snr_desired) =>
        val snr_computed = computeSNR(points.par, newMeans)
        snr_computed >= snr_desired
