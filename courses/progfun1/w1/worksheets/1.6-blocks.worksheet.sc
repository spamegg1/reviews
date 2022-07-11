def square(x: Double) = x * x
def abs(x: Double) = if (x < 0) -x else x

def sqrt(x: Double) =
  def sqrtIter(guess: Double): Double =
    if isGoodEnough(guess) then guess
    else sqrtIter(improve(guess))

  def improve(guess: Double) =
    (guess + x / guess) / 2

  def isGoodEnough(guess: Double) =
    abs(square(guess) - x) < 0.001

  sqrtIter(1.0)

sqrt(2)
