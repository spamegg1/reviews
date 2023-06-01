package games.gameOfFifteen

import org.junit.Assert
import org.junit.Test

class TestGameOfFifteenHelper {
    private fun testPermutation(permutation: List<Int>, expected: Boolean) {
        Assert.assertEquals("The following permutation should be ${if (expected) "even" else "odd"}: $permutation", expected,
            isEven(permutation))
    }

    private fun testPermutation(permutation: String, parity: Boolean) {
        testPermutation(permutation.map { "$it".toInt() }, parity)
    }

    private fun testEven(shortPermutation: String) = testPermutation(shortPermutation, true)
    private fun testOdd(shortPermutation: String) = testPermutation(shortPermutation, false)

    private fun testEven(shortPermutation: Int) = testEven(shortPermutation.toString())
    private fun testOdd(shortPermutation: Int) = testOdd(shortPermutation.toString())

    @Test
    fun testEven0() = testEven(123)

    @Test
    fun testEven1() = testEven(1234)

    @Test
    fun testEven2() = testEven(3124)

    @Test
    fun testEven3() = testEven(2314)

    @Test
    fun testEven4() = testEven(2143)

    @Test
    fun testEven5() = testEven(1423)

    @Test
    fun testEven6() = testEven(4213)

    @Test
    fun testEven7() = testEven(1342)

    @Test
    fun testEven8() = testEven(4132)

    @Test
    fun testEven9() = testEven(3412)

    @Test
    fun testEven10() = testEven(3241)

    @Test
    fun testEven11() = testEven(2431)

    @Test
    fun testEven12() = testEven(4321)

    @Test
    fun testEven13() = testEven("0123456789")

    @Test
    fun testEven14() = testEven("4301865279")

    @Test
    fun testEven15() = testEven("9604572831")

    @Test
    fun testEven16() = testEven("1320485769")

    @Test
    fun testOdd0() = testOdd(132)

    @Test
    fun testOdd1() = testOdd(2134)

    @Test
    fun testOdd2() = testOdd(1324)

    @Test
    fun testOdd3() = testOdd(3214)

    @Test
    fun testOdd4() = testOdd(1243)

    @Test
    fun testOdd5() = testOdd(4123)

    @Test
    fun testOdd6() = testOdd(2413)

    @Test
    fun testOdd7() = testOdd(3142)

    @Test
    fun testOdd8() = testOdd(1432)

    @Test
    fun testOdd9() = testOdd(4312)

    @Test
    fun testOdd10() = testOdd(2341)

    @Test
    fun testOdd11() = testOdd(4231)

    @Test
    fun testOdd12() = testOdd(3421)

    @Test
    fun testOdd13() = testOdd("1234567890")

    @Test
    fun testOdd14() = testOdd("5782401963")

    @Test
    fun testOdd15() = testOdd("3592871064")

    @Test
    fun testOdd16() = testOdd("2045831967")

    @Test
    fun testStart() = testPermutation((1..15).toList(), true)
}