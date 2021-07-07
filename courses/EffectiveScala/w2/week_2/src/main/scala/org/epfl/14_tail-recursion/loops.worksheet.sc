
def factorial(n: Int): Int =
  def factorialTailRec(x: Int, accumulator: Int): Int =
    if x == 0 then 1
    else if x == 1 then accumulator
    else factorialTailRec(x - 1, x * accumulator)

  factorialTailRec(n, 1)
end factorial

factorial(1)
factorial(2)
factorial(3)
factorial(4)
