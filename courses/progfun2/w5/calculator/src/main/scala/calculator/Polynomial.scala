package calculator

object Polynomial extends PolynomialInterface:
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] =  // TODO
    Signal(b() * b() - 4 * a() * c())

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] =  // TODO
    val disc: Signal[Double] = computeDelta(a, b, c)
    Signal(
      if (disc() < 0) Set()
      else if (disc() == 0) Set(-b() / (2 * a()))
      else Set((-b() - math.sqrt(disc())) / (2 * a()),
               (-b() + math.sqrt(disc())) / (2 * a()))
    )
