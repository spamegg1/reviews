package taxipark

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTask4SmartPassengers {
    private fun testSmartPassengers(passengerIndexes: Set<Int>, tp: TaxiPark) {
        val message = "Wrong result for 'findSmartPassengers()'." + tp.display()
        val expected = passengerIndexes.map { passenger(it) }.toSet()
        Assert.assertEquals(message, expected, tp.findSmartPassengers())
    }

    @Test
    fun test00() = testSmartPassengers(setOf(), taxiPark(0..1, 0..10))

    @Test
    fun test01() {
        testSmartPassengers(setOf(1), taxiPark(1..2, 1..2, trip(1, 1, discount = 0.1), trip(2, 2)))
    }


    @Test
    fun test02() = testSmartPassengers(setOf(), taxiPark(0..5, 0..9,
            trip(5, listOf(2), duration = 33, distance = 18.0),
            trip(3, listOf(5, 9), duration = 29, distance = 16.0, discount = 0.3),
            trip(2, listOf(5, 3, 8), duration = 0, distance = 3.0),
            trip(1, listOf(4, 8), duration = 32, distance = 6.0),
            trip(0, listOf(1), duration = 37, distance = 28.0),
            trip(2, listOf(0, 8), duration = 0, distance = 7.0),
            trip(2, listOf(9, 4), duration = 25, distance = 35.0),
            trip(0, listOf(3, 7), duration = 30, distance = 35.0),
            trip(2, listOf(2, 3), duration = 17, distance = 27.0, discount = 0.4),
            trip(1, listOf(9, 5, 4), duration = 5, distance = 7.0))
    )

    @Test
    fun test03() = testSmartPassengers(setOf(2), taxiPark(0..2, 0..2,
            trip(2, listOf(2, 1), duration = 14, distance = 10.0, discount = 0.4),
            trip(1, listOf(1, 2, 0), duration = 20, distance = 26.0),
            trip(0, listOf(2, 0), duration = 15, distance = 14.0, discount = 0.4),
            trip(0, listOf(2, 1), duration = 4, distance = 17.0, discount = 0.2),
            trip(1, listOf(0, 1, 2), duration = 35, distance = 23.0),
            trip(2, listOf(1), duration = 28, distance = 5.0),
            trip(0, listOf(0), duration = 30, distance = 25.0, discount = 0.3),
            trip(0, listOf(2, 0), duration = 24, distance = 13.0),
            trip(0, listOf(0, 2), duration = 5, distance = 5.0, discount = 0.2),
            trip(0, listOf(2), duration = 39, distance = 29.0, discount = 0.1),
            trip(1, listOf(0, 2), duration = 25, distance = 9.0),
            trip(2, listOf(2, 0), duration = 36, distance = 23.0, discount = 0.2)))

    @Test
    fun test04() = testSmartPassengers(setOf(0, 1), taxiPark(0..2, 0..2,
            trip(2, listOf(0), duration = 8, distance = 2.0),
            trip(1, listOf(2), duration = 36, distance = 8.0),
            trip(1, listOf(1), duration = 29, distance = 8.0, discount = 0.1),
            trip(2, listOf(0), duration = 22, distance = 10.0, discount = 0.4),
            trip(1, listOf(1), duration = 16, distance = 27.0),
            trip(1, listOf(0, 1), duration = 20, distance = 35.0, discount = 0.3),
            trip(0, listOf(2, 0), duration = 12, distance = 18.0),
            trip(0, listOf(1), duration = 25, distance = 21.0),
            trip(0, listOf(2, 1), duration = 9, distance = 23.0, discount = 0.3),
            trip(2, listOf(2), duration = 12, distance = 8.0),
            trip(1, listOf(1, 2, 0), duration = 24, distance = 10.0, discount = 0.3),
            trip(2, listOf(1), duration = 18, distance = 14.0, discount = 0.3)))

    @Test
    fun test05() = testSmartPassengers(setOf(), taxiPark(0..2, 0..2,
            trip(1, listOf(0, 1), duration = 4, distance = 13.0),
            trip(2, listOf(1), duration = 17, distance = 3.0, discount = 0.4),
            trip(0, listOf(1), duration = 39, distance = 37.0),
            trip(0, listOf(0, 2), duration = 3, distance = 30.0),
            trip(2, listOf(2), duration = 31, distance = 34.0),
            trip(1, listOf(2, 1), duration = 38, distance = 21.0, discount = 0.1),
            trip(1, listOf(1, 2), duration = 9, distance = 21.0),
            trip(2, listOf(0), duration = 22, distance = 37.0, discount = 0.1),
            trip(2, listOf(1), duration = 27, distance = 7.0, discount = 0.2),
            trip(0, listOf(2, 1, 0), duration = 9, distance = 0.0, discount = 0.4),
            trip(2, listOf(2), duration = 31, distance = 23.0, discount = 0.4),
            trip(1, listOf(1, 2, 0), duration = 20, distance = 31.0)))

    @Test
    fun test06() = testSmartPassengers(setOf(2), taxiPark(0..2, 0..2,
            trip(1, listOf(1), duration = 19, distance = 23.0),
            trip(2, listOf(2), duration = 16, distance = 39.0, discount = 0.1),
            trip(1, listOf(1), duration = 19, distance = 27.0),
            trip(2, listOf(1), duration = 9, distance = 1.0),
            trip(0, listOf(2, 1, 0), duration = 24, distance = 24.0),
            trip(0, listOf(0), duration = 9, distance = 13.0, discount = 0.1),
            trip(1, listOf(2, 0), duration = 36, distance = 20.0, discount = 0.1),
            trip(1, listOf(2, 1), duration = 16, distance = 6.0, discount = 0.2),
            trip(2, listOf(0, 1), duration = 27, distance = 8.0),
            trip(1, listOf(2), duration = 15, distance = 31.0, discount = 0.2),
            trip(2, listOf(0, 2, 1), duration = 0, distance = 1.0),
            trip(2, listOf(0, 2), duration = 15, distance = 34.0, discount = 0.3)))

    @Test
    fun test07() = testSmartPassengers(setOf(), taxiPark(0..2, 0..2,
            trip(1, listOf(2, 0), duration = 6, distance = 18.0, discount = 0.3),
            trip(1, listOf(1), duration = 33, distance = 12.0),
            trip(2, listOf(0, 1, 2), duration = 34, distance = 4.0),
            trip(1, listOf(2, 1), duration = 19, distance = 33.0),
            trip(1, listOf(1), duration = 5, distance = 1.0),
            trip(2, listOf(2), duration = 18, distance = 34.0),
            trip(2, listOf(2), duration = 15, distance = 31.0, discount = 0.1),
            trip(2, listOf(1), duration = 14, distance = 9.0, discount = 0.3),
            trip(0, listOf(1), duration = 10, distance = 31.0),
            trip(2, listOf(0, 2), duration = 21, distance = 36.0),
            trip(2, listOf(1), duration = 22, distance = 22.0, discount = 0.1),
            trip(1, listOf(2), duration = 0, distance = 39.0, discount = 0.3)))

    @Test
    fun test08() = testSmartPassengers(setOf(), taxiPark(0..2, 0..2,
            trip(1, listOf(0), duration = 28, distance = 17.0),
            trip(1, listOf(2, 1), duration = 18, distance = 24.0, discount = 0.2),
            trip(0, listOf(2, 1), duration = 23, distance = 34.0, discount = 0.2),
            trip(2, listOf(1, 0), duration = 12, distance = 3.0),
            trip(0, listOf(0, 1, 2), duration = 24, distance = 3.0),
            trip(0, listOf(1, 2), duration = 28, distance = 37.0),
            trip(2, listOf(2, 1), duration = 11, distance = 38.0, discount = 0.3),
            trip(1, listOf(1), duration = 22, distance = 2.0, discount = 0.2),
            trip(0, listOf(0), duration = 0, distance = 32.0),
            trip(0, listOf(0, 2, 1), duration = 31, distance = 0.0),
            trip(0, listOf(1), duration = 6, distance = 28.0),
            trip(2, listOf(2), duration = 16, distance = 4.0)))

    @Test
    fun test09() = testSmartPassengers(setOf(0, 1, 2, 5), taxiPark(0..9, 0..9,
            trip(9, listOf(7, 4, 0), duration = 2, distance = 33.0, discount = 0.3),
            trip(0, listOf(7), duration = 0, distance = 20.0),
            trip(0, listOf(5), duration = 20, distance = 18.0),
            trip(8, listOf(5), duration = 21, distance = 21.0, discount = 0.1),
            trip(5, listOf(0, 9), duration = 21, distance = 11.0, discount = 0.3),
            trip(2, listOf(9), duration = 22, distance = 10.0, discount = 0.3),
            trip(8, listOf(2, 1), duration = 10, distance = 24.0, discount = 0.4),
            trip(6, listOf(9), duration = 1, distance = 21.0),
            trip(4, listOf(1), duration = 16, distance = 25.0, discount = 0.2),
            trip(7, listOf(3, 7, 5), duration = 12, distance = 34.0),
            trip(5, listOf(2, 7), duration = 30, distance = 1.0),
            trip(0, listOf(0), duration = 33, distance = 9.0, discount = 0.1),
            trip(7, listOf(6), duration = 19, distance = 38.0),
            trip(3, listOf(5, 4, 6), duration = 37, distance = 14.0),
            trip(6, listOf(8, 5, 7), duration = 32, distance = 7.0, discount = 0.3),
            trip(8, listOf(1, 4), duration = 14, distance = 8.0, discount = 0.2),
            trip(0, listOf(8, 9, 6), duration = 21, distance = 32.0),
            trip(7, listOf(5, 6), duration = 8, distance = 31.0, discount = 0.4),
            trip(3, listOf(9, 3, 7), duration = 38, distance = 28.0, discount = 0.4),
            trip(9, listOf(0), duration = 27, distance = 27.0, discount = 0.4),
            trip(1, listOf(6, 9, 7, 4), duration = 0, distance = 13.0),
            trip(2, listOf(6), duration = 0, distance = 9.0, discount = 0.3),
            trip(8, listOf(5), duration = 7, distance = 15.0),
            trip(5, listOf(9, 6, 4), duration = 7, distance = 30.0),
            trip(6, listOf(7), duration = 28, distance = 27.0, discount = 0.2),
            trip(6, listOf(7, 8), duration = 15, distance = 11.0, discount = 0.4),
            trip(1, listOf(9), duration = 16, distance = 12.0, discount = 0.1),
            trip(0, listOf(9, 7, 2, 1), duration = 38, distance = 32.0),
            trip(6, listOf(2), duration = 38, distance = 25.0, discount = 0.4),
            trip(0, listOf(9, 7, 6), duration = 38, distance = 29.0, discount = 0.1),
            trip(2, listOf(9, 7, 8), duration = 26, distance = 7.0),
            trip(7, listOf(5), duration = 34, distance = 22.0),
            trip(5, listOf(4, 8, 5), duration = 38, distance = 17.0, discount = 0.2),
            trip(0, listOf(9), duration = 30, distance = 18.0),
            trip(5, listOf(2, 5), duration = 25, distance = 37.0, discount = 0.4),
            trip(0, listOf(0, 4), duration = 14, distance = 21.0),
            trip(9, listOf(5, 3, 7), duration = 7, distance = 23.0, discount = 0.2),
            trip(6, listOf(4, 0, 1), duration = 36, distance = 0.0),
            trip(9, listOf(3, 8, 7), duration = 3, distance = 31.0),
            trip(9, listOf(0), duration = 6, distance = 22.0, discount = 0.1))
    )

    @Test
    fun test10() = testSmartPassengers(setOf(3, 8), taxiPark(0..9, 0..9,
            trip(8, listOf(5, 1), duration = 27, distance = 28.0),
            trip(9, listOf(0, 3, 8, 2), duration = 15, distance = 20.0),
            trip(4, listOf(4, 3, 8), duration = 15, distance = 20.0, discount = 0.1),
            trip(4, listOf(3, 7), duration = 28, distance = 27.0),
            trip(7, listOf(7), duration = 6, distance = 32.0, discount = 0.2),
            trip(2, listOf(6), duration = 2, distance = 30.0),
            trip(8, listOf(2, 7, 4, 3), duration = 4, distance = 2.0, discount = 0.2),
            trip(5, listOf(6, 5), duration = 22, distance = 6.0, discount = 0.2),
            trip(7, listOf(8), duration = 12, distance = 0.0, discount = 0.3),
            trip(1, listOf(5, 8, 4, 9), duration = 36, distance = 6.0),
            trip(4, listOf(8), duration = 9, distance = 32.0, discount = 0.3),
            trip(0, listOf(4), duration = 6, distance = 31.0),
            trip(3, listOf(2, 8, 6), duration = 10, distance = 31.0, discount = 0.4),
            trip(1, listOf(7, 6), duration = 38, distance = 16.0),
            trip(6, listOf(7, 8), duration = 35, distance = 7.0),
            trip(7, listOf(9, 6), duration = 12, distance = 19.0),
            trip(9, listOf(2), duration = 7, distance = 13.0),
            trip(0, listOf(6), duration = 30, distance = 8.0),
            trip(3, listOf(6), duration = 18, distance = 23.0, discount = 0.2),
            trip(2, listOf(9), duration = 22, distance = 12.0),
            trip(5, listOf(4), duration = 1, distance = 35.0, discount = 0.1),
            trip(6, listOf(8), duration = 2, distance = 25.0, discount = 0.1),
            trip(1, listOf(8, 4, 7, 5), duration = 13, distance = 20.0),
            trip(5, listOf(7), duration = 3, distance = 18.0),
            trip(0, listOf(1, 8, 5), duration = 7, distance = 30.0, discount = 0.3),
            trip(7, listOf(6, 1, 9), duration = 36, distance = 4.0, discount = 0.3),
            trip(0, listOf(8, 0), duration = 13, distance = 29.0, discount = 0.4),
            trip(3, listOf(7, 9, 2), duration = 33, distance = 22.0),
            trip(2, listOf(7, 6), duration = 4, distance = 30.0),
            trip(5, listOf(4, 9, 3), duration = 32, distance = 5.0, discount = 0.2),
            trip(6, listOf(9), duration = 24, distance = 14.0),
            trip(6, listOf(8, 2), duration = 31, distance = 29.0),
            trip(4, listOf(6), duration = 35, distance = 2.0),
            trip(3, listOf(7, 8), duration = 8, distance = 27.0, discount = 0.3),
            trip(8, listOf(5, 7), duration = 9, distance = 32.0, discount = 0.1),
            trip(7, listOf(1, 5, 9), duration = 16, distance = 6.0),
            trip(0, listOf(9), duration = 30, distance = 36.0),
            trip(5, listOf(3, 8, 7, 6), duration = 25, distance = 24.0, discount = 0.2),
            trip(8, listOf(6, 3), duration = 11, distance = 36.0, discount = 0.1),
            trip(3, listOf(3, 4, 1), duration = 2, distance = 11.0)))

    @Test
    fun test11() = testSmartPassengers(setOf(5, 7), taxiPark(0..3, 0..9,
            trip(3, listOf(9, 0), duration = 5, distance = 7.0),
            trip(3, listOf(5, 6, 3, 9), duration = 20, distance = 13.0),
            trip(0, listOf(4, 3), duration = 4, distance = 25.0),
            trip(0, listOf(4, 5), duration = 10, distance = 20.0, discount = 0.4),
            trip(2, listOf(1, 2), duration = 32, distance = 4.0),
            trip(2, listOf(0, 9), duration = 26, distance = 20.0),
            trip(3, listOf(2, 5, 9), duration = 4, distance = 9.0, discount = 0.2),
            trip(3, listOf(6, 5, 7), duration = 24, distance = 11.0, discount = 0.1)))
}