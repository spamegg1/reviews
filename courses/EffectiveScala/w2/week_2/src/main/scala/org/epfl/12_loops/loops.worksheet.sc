
object FactorialWithFoldLeft:
  // Calculating factorial using `foldLeft`
  def factorial(n: Int): Int =
    (1 to n).foldLeft(1)((result, x) => result * x)

FactorialWithFoldLeft.factorial(4)
FactorialWithFoldLeft.factorial(12)

object FatorialWithCollectionsProduct:
  // Calculating factorial using `product` on a collection
  def factorial(n: Int): Int = (1 to n).product

FatorialWithCollectionsProduct.factorial(4)
FatorialWithCollectionsProduct.factorial(12)

object FactorialImperative:
  // Calculating factorial imperatively
  def factorial(n: Int): Int =
    var acc = 1
    var i = 1
    while (i < n) {
      i = i + 1
      acc = acc * i
    }
    acc

FactorialImperative.factorial(4)
FactorialImperative.factorial(12)

object FactorialRecursive:
  // Calculating factorial recursively
  def factorial(n: Int): Int =
    if n == 0 then 1
    else n * factorial(n - 1)

FactorialRecursive.factorial(4)
FactorialRecursive.factorial(12)
