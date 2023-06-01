package rationals

import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.ONE

class Rational(n: BigInteger, d: BigInteger): Comparable<Rational> {
    private var numer = n
    private var denom = d
    init {
        require(denom != ZERO)
        val gcd = numer.gcd(denom)
        val sign = denom.signum().toBigInteger()
        numer /= gcd * sign
        denom /= gcd * sign
    }

    override fun toString(): String =
        if (denom == ONE) "$numer" else "$numer/$denom"

    override fun equals(other: Any?): Boolean =
        other is Rational && this.numer == other.numer && this.denom == other.denom

    operator fun plus(that: Rational): Rational = Rational(
        this.numer * that.denom + that.numer * this.denom,
        this.denom * that.denom
    )

    operator fun minus(that: Rational): Rational = Rational(
        this.numer * that.denom - that.numer * this.denom,
        this.denom * that.denom
    )

    operator fun unaryMinus(): Rational = Rational(-numer, denom)

    operator fun times(that: Rational): Rational = Rational(
        this.numer * that.numer,
        this.denom * that.denom
    )

    operator fun div(that: Rational): Rational = Rational(
        this.numer * that.denom,
        this.denom * that.numer
    )

    override operator fun compareTo(other: Rational): Int =
        (this.numer * other.denom).compareTo(this.denom * other.numer)

    override fun hashCode(): Int = 31 * numer.hashCode() + denom.hashCode()
}

infix fun Int.divBy(that: Int): Rational =
    Rational(this.toBigInteger(), that.toBigInteger())

infix fun Long.divBy(that: Long): Rational =
    Rational(this.toBigInteger(), that.toBigInteger())

infix fun BigInteger.divBy(that: BigInteger): Rational =
    Rational(this, that)

fun String.toRational(): Rational {
    if (!contains("/")) return Rational(toBigInteger(), ONE)
    val (numer, denom) = split("/")
    return Rational(numer.toBigInteger(), denom.toBigInteger())
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
