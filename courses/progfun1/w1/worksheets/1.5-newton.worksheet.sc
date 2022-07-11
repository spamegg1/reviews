def abs(x: Double) = if (x < 0) -x else x

def sqrtIter(guess: Double, x: Double): Double =
  if isGoodEnough(guess, x) then guess
  else sqrtIter(improve(guess, x), x)

def isGoodEnough(guess: Double, x: Double) =
  if x <= 0.001 then
    abs(guess * guess - x) < 0.0000001
  else if x > 1.0e10 then
    abs(guess * guess - x) < 1
  else
    abs(guess * guess - x) < 0.001

def improve(guess: Double, x: Double) = (guess + x / guess) / 2

def sqrt(x: Double) = sqrtIter(1.0, x)

sqrt(0.01)
sqrt(0.0001)
sqrt(0.000001)
sqrt(25)
sqrt(100)
sqrt(1.0e10)
sqrt(1.0e20)
sqrt(1.0e30)
sqrt(1.0e40)
sqrt(1.0e45)
// sqrt(1.0e46) // doesn't work
