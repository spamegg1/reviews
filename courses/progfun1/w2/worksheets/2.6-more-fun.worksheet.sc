class Rational(x: Int, y: Int):
  require(y > 0, "denominator must be positive")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int =
    if b == 0 then a else gcd(b, a % b)

  private val g = gcd(x.abs, y)

  val numer = x / g
  val denom = y / g

  def add(that: Rational) = Rational(
    numer * that.denom + denom * that.numer, denom * that.denom
  )

  def mul(that: Rational) = Rational(numer * that.numer, denom * that.denom)

  def neg: Rational = Rational(-numer, denom)

  def sub(that: Rational): Rational = add(that.neg)

  def less(that: Rational): Boolean =
    this.numer * that.denom < that.numer * this.denom

  def max(that: Rational): Rational =
    if this less that then this else that

  override def toString: String = s"$numer / $denom"

end Rational

val x = Rational(1, 3)
val y = Rational(5, 7)
val z = Rational(3, 2)

x.add(y).mul(z)
x.sub(y).sub(z)
