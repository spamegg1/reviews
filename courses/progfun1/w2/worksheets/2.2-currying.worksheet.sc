def product(f: Int => Int)(a: Int, b: Int): Int =
  if a > b then 1 else f(a) * product(f)(a + 1, b)

product(x => x * x)(1, 5)

def fact(n: Int): Int = product(identity)(1, n)

fact(5)

def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)
  (a: Int, b: Int): Int =
  def recur(x: Int): Int =
    if x > b then zero
    else combine(f(x), recur(x + 1))
  recur(a)

def sum(f: Int => Int) = mapReduce(f, (_+_), 0)
def product2(f: Int => Int) = mapReduce(f, (_*_), 1)

sum(fact)(1, 5)
product2(identity)(1, 5)
