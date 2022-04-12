package calculator

object Polynomial extends PolynomialInterface:
  def computeDelta(a: Signal[Double],
                   b: Signal[Double],
                   c: Signal[Double]): Signal[Double] =                  // TODO
    Signal(b() * b() - 4 * a() * c())

  def computeSolutions(a: Signal[Double],
                       b: Signal[Double],
                       c: Signal[Double],
                       delta: Signal[Double]): Signal[Set[Double]] =     // TODO
    Signal(if   delta() < 0
           then Set()
           else Set((-b() - math.sqrt(delta())) / (2 * a()),
                    (-b() + math.sqrt(delta())) / (2 * a())))
