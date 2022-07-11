import annotation.tailrec

def factorial(n: Int): Int =
  @tailrec
  def helper(n: Int, acc: Int): Int =
    if n == 0 then acc
    else helper(n - 1, acc * n)
  helper(n, 1)

factorial(5)

// an alternate:
@tailrec
final def fact2(n: Int, acc: Int = 1): Int =
  if n == 0 then acc else fact2(n - 1, n * acc)

fact2(5)
