package taxipark

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTask5TheMostFrequentTripDurationPeriod {

    private fun testDurationPeriod(expected: Set<IntRange?>, tp: TaxiPark) {
        val actual = tp.findTheMostFrequentTripDurationPeriod()
        val message = "Wrong result for 'findTheMostFrequentTripDurationPeriod()': $actual."
        if (expected.size <= 1) {
            Assert.assertEquals(
                    message + tp.display(),
                    expected.firstOrNull(), actual)
        } else {
            Assert.assertTrue(message +
                    tp.display() +
                    "\nPossible results: $expected" +
                    "\nActual: $actual\n",
                    actual?.let { it in expected } ?: expected.isEmpty())
        }
    }

    @Test
    fun test00() = testDurationPeriod(setOf(null), taxiPark(0..1, 0..10))

    @Test
    fun test01() {
        // The period 30..39 is the most frequent since there are two trips (duration 30 and 35)
        testDurationPeriod(setOf(30..39), taxiPark(1..3, 1..5,
                trip(1, 1, duration = 10),
                trip(3, 4, duration = 30),
                trip(1, 2, duration = 20),
                trip(2, 3, duration = 35)))
    }

    @Test
    fun test02() = testDurationPeriod(setOf(30..39), taxiPark(0..5, 0..9,
            trip(0, listOf(2, 9), duration = 14, distance = 25.0),
            trip(1, listOf(8), duration = 39, distance = 37.0, discount = 0.2),
            trip(5, listOf(0, 5), duration = 27, distance = 28.0, discount = 0.3),
            trip(4, listOf(0, 6), duration = 33, distance = 14.0),
            trip(2, listOf(5, 1, 4, 3), duration = 2, distance = 15.0),
            trip(4, listOf(7), duration = 27, distance = 2.0),
            trip(4, listOf(4, 6), duration = 31, distance = 31.0),
            trip(3, listOf(9, 0), duration = 34, distance = 7.0),
            trip(5, listOf(3), duration = 25, distance = 33.0),
            trip(1, listOf(0, 7, 2, 3), duration = 13, distance = 17.0))
    )

    @Test
    fun test03() = testDurationPeriod(setOf(0..9), taxiPark(0..5, 0..9,
            trip(1, listOf(1, 6), duration = 9, distance = 0.0, discount = 0.2),
            trip(3, listOf(1), duration = 9, distance = 22.0, discount = 0.3),
            trip(2, listOf(9, 3), duration = 3, distance = 26.0),
            trip(5, listOf(7), duration = 19, distance = 20.0),
            trip(4, listOf(3, 8), duration = 4, distance = 30.0, discount = 0.3),
            trip(4, listOf(7, 8, 5, 2), duration = 1, distance = 33.0, discount = 0.3),
            trip(4, listOf(9), duration = 4, distance = 20.0),
            trip(5, listOf(9), duration = 16, distance = 10.0),
            trip(4, listOf(0, 6), duration = 6, distance = 36.0),
            trip(1, listOf(6, 0), duration = 20, distance = 31.0, discount = 0.1)))

    @Test
    fun test04() = testDurationPeriod(setOf(10..19), taxiPark(0..5, 0..9,
            trip(4, listOf(3, 2), duration = 12, distance = 17.0, discount = 0.4),
            trip(4, listOf(5), duration = 16, distance = 14.0),
            trip(4, listOf(1), duration = 29, distance = 34.0),
            trip(3, listOf(4, 0), duration = 37, distance = 38.0),
            trip(0, listOf(7), duration = 3, distance = 0.0),
            trip(0, listOf(1, 6, 3), duration = 33, distance = 26.0),
            trip(0, listOf(9, 2), duration = 21, distance = 2.0),
            trip(1, listOf(9), duration = 27, distance = 12.0),
            trip(5, listOf(1, 6), duration = 19, distance = 23.0),
            trip(3, listOf(5, 9, 2), duration = 10, distance = 39.0)))

    @Test
    fun test05() = testDurationPeriod(setOf(0..9, 10..19, 20..29), taxiPark(0..5, 0..9,
            trip(0, listOf(8), duration = 20, distance = 22.0, discount = 0.3),
            trip(4, listOf(3, 5), duration = 12, distance = 25.0, discount = 0.4),
            trip(2, listOf(2, 8), duration = 28, distance = 6.0),
            trip(5, listOf(0, 3, 4), duration = 24, distance = 4.0),
            trip(2, listOf(2, 8), duration = 19, distance = 20.0),
            trip(4, listOf(0, 2), duration = 39, distance = 4.0, discount = 0.1),
            trip(3, listOf(4, 2), duration = 17, distance = 27.0),
            trip(2, listOf(4), duration = 6, distance = 11.0),
            trip(2, listOf(5), duration = 1, distance = 18.0),
            trip(5, listOf(2), duration = 3, distance = 35.0, discount = 0.4)))

    @Test
    fun test06() = testDurationPeriod(setOf(0..9, 30..39), taxiPark(0..9, 0..19,
            trip(7, listOf(18, 5), duration = 21, distance = 17.0),
            trip(6, listOf(8, 11), duration = 37, distance = 29.0, discount = 0.1),
            trip(5, listOf(19, 11, 10, 4), duration = 33, distance = 12.0, discount = 0.2),
            trip(7, listOf(4, 3), duration = 8, distance = 2.0),
            trip(5, listOf(18), duration = 1, distance = 11.0),
            trip(8, listOf(13, 10), duration = 26, distance = 4.0, discount = 0.4),
            trip(0, listOf(14, 16, 1), duration = 35, distance = 33.0),
            trip(8, listOf(13, 8), duration = 9, distance = 29.0),
            trip(0, listOf(12), duration = 31, distance = 5.0),
            trip(5, listOf(2, 11, 4), duration = 22, distance = 18.0, discount = 0.3),
            trip(5, listOf(19, 13), duration = 21, distance = 16.0, discount = 0.3),
            trip(6, listOf(4), duration = 11, distance = 16.0),
            trip(9, listOf(14, 16), duration = 3, distance = 9.0),
            trip(7, listOf(11, 10, 3, 9), duration = 27, distance = 35.0, discount = 0.1),
            trip(9, listOf(13), duration = 9, distance = 22.0),
            trip(1, listOf(12), duration = 36, distance = 8.0),
            trip(1, listOf(11, 13), duration = 7, distance = 15.0),
            trip(3, listOf(10, 0), duration = 36, distance = 6.0),
            trip(3, listOf(16, 12), duration = 28, distance = 27.0),
            trip(4, listOf(14, 6), duration = 8, distance = 34.0, discount = 0.4),
            trip(0, listOf(9), duration = 16, distance = 7.0, discount = 0.1),
            trip(7, listOf(11, 7, 3), duration = 18, distance = 2.0),
            trip(5, listOf(3), duration = 15, distance = 39.0, discount = 0.2),
            trip(3, listOf(14, 7), duration = 31, distance = 16.0),
            trip(2, listOf(4), duration = 16, distance = 2.0, discount = 0.3),
            trip(7, listOf(7), duration = 14, distance = 36.0),
            trip(0, listOf(11), duration = 38, distance = 23.0),
            trip(7, listOf(12, 6, 3, 4), duration = 19, distance = 3.0, discount = 0.1),
            trip(6, listOf(13, 3, 9), duration = 2, distance = 10.0, discount = 0.2),
            trip(7, listOf(8, 2, 3, 19), duration = 21, distance = 6.0, discount = 0.3)))

    @Test
    fun test07() = testDurationPeriod(setOf(0..9), taxiPark(0..9, 0..19,
            trip(5, listOf(4, 18), duration = 13, distance = 32.0),
            trip(7, listOf(7, 9, 16, 13), duration = 8, distance = 11.0),
            trip(9, listOf(3, 18, 1), duration = 4, distance = 27.0, discount = 0.1),
            trip(4, listOf(2, 3, 8), duration = 7, distance = 8.0),
            trip(1, listOf(14, 4), duration = 9, distance = 34.0),
            trip(2, listOf(8, 4), duration = 9, distance = 19.0, discount = 0.1),
            trip(4, listOf(8), duration = 35, distance = 24.0, discount = 0.3),
            trip(8, listOf(14, 13), duration = 11, distance = 4.0),
            trip(9, listOf(5, 1), duration = 20, distance = 18.0, discount = 0.2),
            trip(2, listOf(9, 13), duration = 24, distance = 13.0),
            trip(1, listOf(5, 14, 13), duration = 1, distance = 4.0),
            trip(1, listOf(15, 5), duration = 17, distance = 34.0),
            trip(0, listOf(16), duration = 18, distance = 7.0),
            trip(5, listOf(17), duration = 1, distance = 34.0, discount = 0.1),
            trip(1, listOf(11, 3, 7), duration = 9, distance = 23.0, discount = 0.2),
            trip(8, listOf(8, 11), duration = 7, distance = 34.0, discount = 0.4),
            trip(8, listOf(3), duration = 5, distance = 20.0),
            trip(9, listOf(3, 13, 12, 17), duration = 26, distance = 19.0, discount = 0.1),
            trip(8, listOf(16, 7), duration = 35, distance = 9.0, discount = 0.2),
            trip(0, listOf(16), duration = 28, distance = 5.0, discount = 0.1),
            trip(6, listOf(4), duration = 27, distance = 38.0),
            trip(4, listOf(7, 9), duration = 38, distance = 20.0),
            trip(9, listOf(6, 13), duration = 19, distance = 5.0),
            trip(5, listOf(4), duration = 20, distance = 5.0, discount = 0.4),
            trip(3, listOf(14), duration = 32, distance = 37.0),
            trip(1, listOf(11), duration = 19, distance = 11.0, discount = 0.4),
            trip(3, listOf(6, 16), duration = 39, distance = 10.0),
            trip(7, listOf(4, 6, 11), duration = 8, distance = 16.0, discount = 0.4),
            trip(0, listOf(5), duration = 19, distance = 39.0, discount = 0.1),
            trip(5, listOf(8, 19), duration = 35, distance = 31.0, discount = 0.4)))

    @Test
    fun test08() = testDurationPeriod(setOf(340..349), taxiPark(0..5, 0..9,
            trip(3, listOf(7, 3, 4), duration = 400, distance = 890.0),
            trip(5, listOf(4, 1), duration = 10, distance = 897.0, discount = 0.1),
            trip(0, listOf(4, 2), duration = 672, distance = 818.0),
            trip(5, listOf(9), duration = 715, distance = 807.0, discount = 0.2),
            trip(4, listOf(5, 3, 2), duration = 462, distance = 816.0),
            trip(2, listOf(6, 2), duration = 824, distance = 948.0),
            trip(5, listOf(5, 3, 7), duration = 261, distance = 26.0),
            trip(3, listOf(8, 3, 5), duration = 575, distance = 57.0, discount = 0.1),
            trip(3, listOf(2, 1), duration = 341, distance = 607.0, discount = 0.3),
            trip(4, listOf(3, 8, 6), duration = 349, distance = 26.0)))

    @Test
    fun test09() = testDurationPeriod(
            setOf(420..429, 150..159, 890..899, 530..539, 470..479, 70..79, 430..439, 260..269, 80..89, 60..69),
            taxiPark(0..5, 0..9,
                    trip(5, listOf(6, 8, 3), duration = 425, distance = 230.0, discount = 0.3),
                    trip(3, listOf(4), duration = 151, distance = 351.0),
                    trip(0, listOf(6), duration = 892, distance = 165.0, discount = 0.2),
                    trip(2, listOf(4, 7, 6), duration = 538, distance = 699.0, discount = 0.3),
                    trip(2, listOf(3), duration = 477, distance = 706.0, discount = 0.3),
                    trip(2, listOf(6, 7, 1), duration = 79, distance = 924.0),
                    trip(3, listOf(4), duration = 433, distance = 404.0, discount = 0.3),
                    trip(0, listOf(3, 5), duration = 264, distance = 217.0),
                    trip(2, listOf(5), duration = 81, distance = 8.0, discount = 0.3),
                    trip(0, listOf(5), duration = 67, distance = 613.0)))

    @Test
    fun test10() = testDurationPeriod(setOf(320..329), taxiPark(0..5, 0..9,
            trip(0, listOf(8, 1, 9), duration = 716, distance = 164.0),
            trip(3, listOf(9), duration = 406, distance = 180.0, discount = 0.1),
            trip(2, listOf(8), duration = 856, distance = 104.0, discount = 0.2),
            trip(5, listOf(4), duration = 592, distance = 270.0),
            trip(0, listOf(4, 7, 6, 2), duration = 101, distance = 880.0),
            trip(5, listOf(5, 2, 0), duration = 323, distance = 253.0, discount = 0.4),
            trip(2, listOf(1, 0, 4), duration = 127, distance = 99.0),
            trip(0, listOf(4, 6, 2), duration = 324, distance = 182.0),
            trip(1, listOf(6, 0), duration = 325, distance = 36.0),
            trip(4, listOf(0, 9), duration = 213, distance = 182.0)))

    @Test
    fun test11() = testDurationPeriod(setOf(0..9), taxiPark(0..9, 0..19,
            trip(0, listOf(15, 14, 10), duration = 0, distance = 19.0),
            trip(0, listOf(18, 8, 7, 14), duration = 15, distance = 1.0),
            trip(9, listOf(4, 19, 0), duration = 9, distance = 15.0),
            trip(0, listOf(12, 7, 2), duration = 13, distance = 19.0, discount = 0.3),
            trip(6, listOf(11, 8), duration = 14, distance = 19.0),
            trip(0, listOf(2), duration = 12, distance = 14.0, discount = 0.3),
            trip(8, listOf(1, 17, 9, 10), duration = 16, distance = 3.0),
            trip(0, listOf(17), duration = 10, distance = 6.0, discount = 0.4),
            trip(1, listOf(8, 1), duration = 7, distance = 5.0),
            trip(9, listOf(6), duration = 3, distance = 6.0),
            trip(4, listOf(12, 19, 7, 0), duration = 9, distance = 10.0, discount = 0.4),
            trip(7, listOf(16), duration = 11, distance = 12.0, discount = 0.1),
            trip(9, listOf(6, 14), duration = 9, distance = 7.0),
            trip(5, listOf(4), duration = 1, distance = 16.0, discount = 0.4),
            trip(2, listOf(15, 7, 12), duration = 9, distance = 6.0),
            trip(1, listOf(13), duration = 2, distance = 7.0, discount = 0.1),
            trip(5, listOf(0, 18, 3), duration = 10, distance = 11.0),
            trip(0, listOf(9), duration = 6, distance = 18.0, discount = 0.4),
            trip(1, listOf(9, 0, 3, 14), duration = 0, distance = 15.0),
            trip(1, listOf(10), duration = 17, distance = 15.0),
            trip(6, listOf(0, 18, 13), duration = 3, distance = 5.0),
            trip(7, listOf(19, 10), duration = 0, distance = 8.0),
            trip(8, listOf(4, 15), duration = 9, distance = 17.0, discount = 0.1),
            trip(6, listOf(15), duration = 13, distance = 1.0),
            trip(2, listOf(3), duration = 13, distance = 15.0, discount = 0.2),
            trip(3, listOf(13, 11, 15), duration = 2, distance = 9.0),
            trip(2, listOf(12), duration = 2, distance = 16.0),
            trip(6, listOf(13, 18), duration = 12, distance = 14.0),
            trip(8, listOf(19), duration = 1, distance = 13.0, discount = 0.4),
            trip(6, listOf(9, 13, 1, 19), duration = 5, distance = 14.0, discount = 0.2),
            trip(4, listOf(8), duration = 15, distance = 8.0, discount = 0.2),
            trip(6, listOf(16, 15), duration = 9, distance = 4.0),
            trip(2, listOf(14, 0), duration = 7, distance = 2.0),
            trip(5, listOf(18), duration = 7, distance = 19.0),
            trip(9, listOf(15), duration = 15, distance = 10.0, discount = 0.4),
            trip(7, listOf(7, 18), duration = 12, distance = 11.0),
            trip(3, listOf(10, 19, 17), duration = 10, distance = 7.0),
            trip(3, listOf(10), duration = 18, distance = 10.0, discount = 0.2),
            trip(8, listOf(9, 15, 16, 19), duration = 19, distance = 12.0, discount = 0.3),
            trip(5, listOf(13), duration = 16, distance = 11.0, discount = 0.3)))

    @Test
    fun test12() = testDurationPeriod(setOf(0..9, 10..19), taxiPark(0..9, 0..19,
            trip(6, listOf(16), duration = 8, distance = 17.0),
            trip(4, listOf(11), duration = 10, distance = 12.0),
            trip(8, listOf(8, 13), duration = 14, distance = 4.0, discount = 0.1),
            trip(6, listOf(19), duration = 14, distance = 9.0),
            trip(4, listOf(18, 10), duration = 6, distance = 17.0, discount = 0.1),
            trip(0, listOf(0), duration = 19, distance = 17.0, discount = 0.1),
            trip(7, listOf(2, 15), duration = 12, distance = 9.0),
            trip(9, listOf(11, 10, 7, 15), duration = 11, distance = 5.0),
            trip(3, listOf(1, 18), duration = 5, distance = 14.0, discount = 0.3),
            trip(2, listOf(19, 8), duration = 7, distance = 8.0, discount = 0.2),
            trip(1, listOf(11), duration = 11, distance = 18.0),
            trip(5, listOf(0, 18, 11, 14), duration = 6, distance = 17.0, discount = 0.3),
            trip(6, listOf(11, 6), duration = 1, distance = 4.0),
            trip(5, listOf(11), duration = 10, distance = 19.0),
            trip(8, listOf(0, 4), duration = 2, distance = 12.0, discount = 0.4),
            trip(6, listOf(16, 14), duration = 16, distance = 0.0),
            trip(3, listOf(12), duration = 2, distance = 15.0, discount = 0.2),
            trip(5, listOf(15, 6), duration = 5, distance = 6.0),
            trip(8, listOf(4, 19), duration = 12, distance = 0.0),
            trip(4, listOf(8, 14), duration = 4, distance = 19.0),
            trip(3, listOf(0), duration = 14, distance = 19.0, discount = 0.3),
            trip(3, listOf(9), duration = 16, distance = 5.0),
            trip(2, listOf(7, 6, 12), duration = 4, distance = 19.0),
            trip(9, listOf(0, 1), duration = 10, distance = 1.0),
            trip(8, listOf(16), duration = 10, distance = 4.0),
            trip(7, listOf(16, 3), duration = 12, distance = 10.0, discount = 0.4),
            trip(9, listOf(18, 0), duration = 15, distance = 14.0, discount = 0.2),
            trip(9, listOf(10), duration = 2, distance = 2.0, discount = 0.2),
            trip(4, listOf(11, 14, 18), duration = 4, distance = 3.0),
            trip(7, listOf(5), duration = 0, distance = 4.0),
            trip(9, listOf(13, 17), duration = 0, distance = 8.0, discount = 0.3),
            trip(3, listOf(8, 5), duration = 16, distance = 3.0),
            trip(9, listOf(19, 7), duration = 1, distance = 3.0),
            trip(6, listOf(4, 17), duration = 10, distance = 18.0),
            trip(2, listOf(7, 12), duration = 9, distance = 6.0, discount = 0.2),
            trip(6, listOf(4, 11), duration = 8, distance = 5.0),
            trip(5, listOf(0), duration = 3, distance = 15.0, discount = 0.4),
            trip(3, listOf(1), duration = 1, distance = 13.0),
            trip(5, listOf(11), duration = 17, distance = 16.0),
            trip(3, listOf(2, 19, 8, 1), duration = 10, distance = 15.0)))

    @Test
    fun test13() {
        testDurationPeriod(setOf(30..39, 4320..4329), taxiPark(1..2, 1..2,
                trip(1, 1, duration = 33),
                trip(2, 2, duration = 4320)))
    }
}