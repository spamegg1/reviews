object LazyListTask:
  def sieve(xs: LazyList[BigInt]): LazyList[BigInt] /* TODO */.empty

  def firstNPrimes(n: Int): LazyList[BigInt] =
    sieve(LazyList.iterate(BigInt(2)){x => x + 1}).take(n)

  @main
  def main() =
    firstNPrimes(500).foreach(println)