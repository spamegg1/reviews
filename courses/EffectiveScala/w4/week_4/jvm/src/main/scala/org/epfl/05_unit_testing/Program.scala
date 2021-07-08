package org.epfl.testing

def add(x: Int, y: Int): Int = x + y

def fibonacci(n: Int): Int =
  require(n >= 1 && n <= 47)
  def loop(remaining: Int, x1: Int, x2: Int): Int =
    if remaining == 1 then
      x1
    else
      loop(remaining - 1, x2, x1 + x2)

  loop(n, 0, 1)
end fibonacci
