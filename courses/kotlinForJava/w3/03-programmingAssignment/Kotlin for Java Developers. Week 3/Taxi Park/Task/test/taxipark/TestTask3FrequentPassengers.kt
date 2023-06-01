package taxipark

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTask3FrequentPassengers {
    private fun testFrequentPassengers(driverIndex: Int, passengerIndexes: Set<Int>, tp: TaxiPark) {
        val message = "Wrong result for 'findFrequentPassengers()'. Driver: ${driver(driverIndex).name}." + tp.display()
        val expected = passengerIndexes.map { passenger(it) }.toSet()
        Assert.assertEquals(message, expected, tp.findFrequentPassengers(driver(driverIndex)))
    }

    @Test
    fun test0() {
        testFrequentPassengers(1, setOf(1, 3),
                taxiPark(1..2, 1..4, trip(1, 1), trip(1, 1), trip(1, listOf(1, 3)), trip(1, 3), trip(1, 2), trip(2, 2)))
    }


    @Test
    fun test01() = testFrequentPassengers(2, setOf(5, 7), taxiPark(0..2, 0..7,
            trip(1, listOf(3, 6, 4, 1), duration = 26, distance = 22.0),
            trip(2, listOf(7), duration = 18, distance = 27.0),
            trip(1, listOf(1), duration = 11, distance = 37.0),
            trip(0, listOf(0, 7), duration = 1, distance = 6.0),
            trip(2, listOf(6, 7, 5), duration = 1, distance = 37.0, discount = 0.4),
            trip(2, listOf(5, 0), duration = 39, distance = 39.0, discount = 0.2),
            trip(0, listOf(1), duration = 6, distance = 15.0, discount = 0.3),
            trip(2, listOf(7), duration = 36, distance = 26.0, discount = 0.1),
            trip(2, listOf(5), duration = 5, distance = 24.0),
            trip(1, listOf(7, 5, 0, 1), duration = 18, distance = 22.0, discount = 0.3)))

    @Test
    fun test02() = testFrequentPassengers(1, setOf(1), taxiPark(0..2, 0..7,
            trip(1, listOf(3, 6, 4, 1), duration = 26, distance = 22.0),
            trip(2, listOf(7), duration = 18, distance = 27.0),
            trip(1, listOf(1), duration = 11, distance = 37.0),
            trip(0, listOf(0, 7), duration = 1, distance = 6.0),
            trip(2, listOf(6, 7, 5), duration = 1, distance = 37.0, discount = 0.4),
            trip(2, listOf(5, 0), duration = 39, distance = 39.0, discount = 0.2),
            trip(0, listOf(1), duration = 6, distance = 15.0, discount = 0.3),
            trip(2, listOf(7), duration = 36, distance = 26.0, discount = 0.1),
            trip(2, listOf(5), duration = 5, distance = 24.0),
            trip(1, listOf(7, 5, 0, 1), duration = 18, distance = 22.0, discount = 0.3)))

    @Test
    fun test03() = testFrequentPassengers(1, setOf(0, 2, 3, 7), taxiPark(0..2, 0..7,
            trip(2, listOf(6, 3, 4), duration = 35, distance = 14.0, discount = 0.4),
            trip(0, listOf(2, 1, 5), duration = 13, distance = 2.0, discount = 0.4),
            trip(0, listOf(1, 0), duration = 14, distance = 21.0),
            trip(1, listOf(7), duration = 18, distance = 19.0, discount = 0.4),
            trip(2, listOf(3), duration = 18, distance = 24.0, discount = 0.2),
            trip(2, listOf(6, 3), duration = 8, distance = 32.0),
            trip(2, listOf(3, 6), duration = 36, distance = 0.0, discount = 0.4),
            trip(1, listOf(3, 0, 2, 7), duration = 30, distance = 23.0),
            trip(1, listOf(0, 4, 3, 1), duration = 5, distance = 39.0),
            trip(1, listOf(2, 7), duration = 12, distance = 38.0)))

    @Test
    fun test04() = testFrequentPassengers(2, setOf(2, 3, 4, 5), taxiPark(0..2, 0..7,
            trip(2, listOf(2), duration = 25, distance = 7.0, discount = 0.4),
            trip(2, listOf(3), duration = 34, distance = 38.0, discount = 0.1),
            trip(1, listOf(7, 3, 6), duration = 27, distance = 26.0),
            trip(2, listOf(3, 5), duration = 25, distance = 8.0),
            trip(2, listOf(2, 4, 3), duration = 1, distance = 20.0, discount = 0.4),
            trip(0, listOf(0), duration = 3, distance = 5.0),
            trip(2, listOf(5, 4), duration = 5, distance = 5.0, discount = 0.3),
            trip(2, listOf(4, 2, 1), duration = 0, distance = 20.0),
            trip(1, listOf(1, 2, 6), duration = 10, distance = 6.0),
            trip(1, listOf(5), duration = 3, distance = 1.0)))

    @Test
    fun test05() = testFrequentPassengers(2, setOf(), taxiPark(0..5, 0..15,
            trip(3, listOf(2, 5, 15), duration = 22, distance = 4.0, discount = 0.4),
            trip(1, listOf(1, 8), duration = 28, distance = 17.0),
            trip(5, listOf(15), duration = 32, distance = 2.0, discount = 0.3),
            trip(4, listOf(12, 7), duration = 30, distance = 24.0),
            trip(3, listOf(11, 15), duration = 13, distance = 24.0),
            trip(3, listOf(1, 11, 13), duration = 28, distance = 25.0),
            trip(4, listOf(13, 5, 14, 2), duration = 19, distance = 36.0, discount = 0.4),
            trip(0, listOf(4, 13), duration = 2, distance = 14.0),
            trip(3, listOf(6), duration = 28, distance = 36.0),
            trip(2, listOf(12, 4, 8, 10), duration = 20, distance = 8.0),
            trip(5, listOf(11, 15, 14, 13), duration = 31, distance = 11.0),
            trip(0, listOf(8, 3, 14), duration = 39, distance = 23.0, discount = 0.3),
            trip(0, listOf(3), duration = 0, distance = 29.0),
            trip(1, listOf(7, 14), duration = 18, distance = 21.0, discount = 0.2),
            trip(1, listOf(6), duration = 36, distance = 19.0),
            trip(1, listOf(3), duration = 6, distance = 7.0, discount = 0.3),
            trip(0, listOf(9, 6), duration = 23, distance = 8.0),
            trip(3, listOf(0, 14, 8), duration = 16, distance = 8.0, discount = 0.1),
            trip(2, listOf(5), duration = 34, distance = 1.0, discount = 0.2),
            trip(1, listOf(15, 7), duration = 6, distance = 19.0)))

    @Test
    fun test06() = testFrequentPassengers(3, setOf(3, 13, 15), taxiPark(0..5, 0..15,
            trip(4, listOf(0, 5), duration = 28, distance = 38.0),
            trip(5, listOf(1, 9), duration = 2, distance = 39.0, discount = 0.4),
            trip(1, listOf(8, 7, 0), duration = 1, distance = 34.0, discount = 0.4),
            trip(0, listOf(11), duration = 2, distance = 11.0, discount = 0.4),
            trip(3, listOf(1, 7), duration = 15, distance = 27.0, discount = 0.4),
            trip(4, listOf(0, 8, 3), duration = 6, distance = 18.0, discount = 0.2),
            trip(5, listOf(14, 3, 1), duration = 24, distance = 2.0, discount = 0.3),
            trip(2, listOf(8), duration = 15, distance = 4.0, discount = 0.1),
            trip(3, listOf(12, 15, 8), duration = 23, distance = 5.0, discount = 0.2),
            trip(5, listOf(15, 10), duration = 12, distance = 15.0),
            trip(2, listOf(3), duration = 11, distance = 23.0, discount = 0.1),
            trip(3, listOf(13, 15), duration = 39, distance = 39.0),
            trip(0, listOf(12), duration = 21, distance = 36.0),
            trip(3, listOf(3, 6), duration = 2, distance = 12.0),
            trip(4, listOf(3, 13), duration = 36, distance = 15.0),
            trip(3, listOf(13, 4), duration = 34, distance = 11.0),
            trip(3, listOf(3, 15), duration = 33, distance = 17.0, discount = 0.2),
            trip(4, listOf(15), duration = 10, distance = 10.0, discount = 0.1),
            trip(1, listOf(8, 5), duration = 14, distance = 33.0),
            trip(0, listOf(15, 10), duration = 28, distance = 19.0)))

    @Test
    fun test07() = testFrequentPassengers(5, setOf(1), taxiPark(0..5, 0..15,
            trip(4, listOf(0, 5), duration = 28, distance = 38.0),
            trip(5, listOf(1, 9), duration = 2, distance = 39.0, discount = 0.4),
            trip(1, listOf(8, 7, 0), duration = 1, distance = 34.0, discount = 0.4),
            trip(0, listOf(11), duration = 2, distance = 11.0, discount = 0.4),
            trip(3, listOf(1, 7), duration = 15, distance = 27.0, discount = 0.4),
            trip(4, listOf(0, 8, 3), duration = 6, distance = 18.0, discount = 0.2),
            trip(5, listOf(14, 3, 1), duration = 24, distance = 2.0, discount = 0.3),
            trip(2, listOf(8), duration = 15, distance = 4.0, discount = 0.1),
            trip(3, listOf(12, 15, 8), duration = 23, distance = 5.0, discount = 0.2),
            trip(5, listOf(15, 10), duration = 12, distance = 15.0),
            trip(2, listOf(3), duration = 11, distance = 23.0, discount = 0.1),
            trip(3, listOf(13, 15), duration = 39, distance = 39.0),
            trip(0, listOf(12), duration = 21, distance = 36.0),
            trip(3, listOf(3, 6), duration = 2, distance = 12.0),
            trip(4, listOf(3, 13), duration = 36, distance = 15.0),
            trip(3, listOf(13, 4), duration = 34, distance = 11.0),
            trip(3, listOf(3, 15), duration = 33, distance = 17.0, discount = 0.2),
            trip(4, listOf(15), duration = 10, distance = 10.0, discount = 0.1),
            trip(1, listOf(8, 5), duration = 14, distance = 33.0),
            trip(0, listOf(15, 10), duration = 28, distance = 19.0)))

    @Test
    fun test08() = testFrequentPassengers(3, setOf(), taxiPark(0..5, 0..15))

    @Test
    fun test09() = testFrequentPassengers(1, setOf(0, 1, 2, 3, 4, 5), taxiPark(0..1, 0..5,
            trip(1, listOf(4), duration = 33, distance = 14.0, discount = 0.2),
            trip(1, listOf(0), duration = 0, distance = 22.0),
            trip(0, listOf(5, 0), duration = 21, distance = 11.0),
            trip(0, listOf(0, 4), duration = 13, distance = 8.0, discount = 0.3),
            trip(1, listOf(1), duration = 9, distance = 7.0),
            trip(1, listOf(4, 3), duration = 10, distance = 29.0, discount = 0.2),
            trip(1, listOf(5, 3), duration = 25, distance = 0.0),
            trip(0, listOf(0), duration = 37, distance = 13.0, discount = 0.3),
            trip(1, listOf(5, 2), duration = 24, distance = 21.0),
            trip(1, listOf(4), duration = 21, distance = 33.0, discount = 0.2),
            trip(1, listOf(0, 1, 4), duration = 32, distance = 21.0),
            trip(1, listOf(5, 4), duration = 0, distance = 30.0),
            trip(1, listOf(5, 1), duration = 0, distance = 14.0, discount = 0.3),
            trip(1, listOf(0, 4), duration = 25, distance = 19.0),
            trip(1, listOf(2, 5), duration = 36, distance = 26.0, discount = 0.4),
            trip(1, listOf(0, 2), duration = 16, distance = 38.0),
            trip(1, listOf(2, 4, 1), duration = 4, distance = 26.0),
            trip(0, listOf(1, 5), duration = 28, distance = 10.0),
            trip(1, listOf(4, 3, 0), duration = 16, distance = 26.0, discount = 0.1),
            trip(0, listOf(5), duration = 9, distance = 37.0)))

    @Test
    fun test10() = testFrequentPassengers(1, setOf(0, 1, 2, 4, 5), taxiPark(0..1, 0..5,
            trip(0, listOf(4), duration = 9, distance = 14.0),
            trip(0, listOf(3, 5, 0), duration = 21, distance = 15.0),
            trip(0, listOf(5, 3), duration = 9, distance = 33.0),
            trip(1, listOf(2), duration = 29, distance = 6.0, discount = 0.2),
            trip(0, listOf(4), duration = 4, distance = 39.0, discount = 0.4),
            trip(1, listOf(5), duration = 8, distance = 31.0, discount = 0.1),
            trip(1, listOf(0, 5, 1), duration = 6, distance = 1.0, discount = 0.3),
            trip(0, listOf(5, 4), duration = 32, distance = 36.0),
            trip(1, listOf(4), duration = 27, distance = 4.0, discount = 0.3),
            trip(1, listOf(0, 2), duration = 2, distance = 9.0),
            trip(0, listOf(1, 3), duration = 2, distance = 2.0, discount = 0.3),
            trip(0, listOf(2, 3), duration = 3, distance = 6.0, discount = 0.1),
            trip(1, listOf(4, 3), duration = 37, distance = 29.0, discount = 0.3),
            trip(1, listOf(4, 2), duration = 38, distance = 32.0),
            trip(1, listOf(0, 4, 5), duration = 29, distance = 5.0, discount = 0.3),
            trip(0, listOf(5, 4), duration = 8, distance = 14.0),
            trip(0, listOf(2), duration = 36, distance = 37.0, discount = 0.1),
            trip(0, listOf(0), duration = 26, distance = 11.0, discount = 0.4),
            trip(1, listOf(1, 0), duration = 23, distance = 6.0, discount = 0.3),
            trip(1, listOf(4, 0), duration = 30, distance = 20.0, discount = 0.4)))

}