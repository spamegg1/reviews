extension (n: Int)
  def isZero: Boolean = n == 0
  def ** (e: Int): Int = List.fill(e)(n).product

42.isZero // : Boolean = false
0.isZero  // : Boolean = true
isZero(1) // : Boolean = false

5 ** 3   // : Int = 125
**(2)(4) // : Int = 16
