package taxipark

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTask2FaithfulPassengers {
    private fun testFaithfulPassengers(minTrips: Int, passengerIndexes: Set<Int>, tp: TaxiPark) {
        val message = "Wrong result for 'findFaithfulPassengers()'. MinTrips: $minTrips." + tp.display()
        val expected = passengerIndexes.map { passenger(it) }.toSet()
        Assert.assertEquals(message, expected, tp.findFaithfulPassengers(minTrips))
    }

    @Test
    fun test0() = testFaithfulPassengers(1, setOf(), taxiPark(0..2, 0..4))


    @Test
    fun test1() {
        testFaithfulPassengers(2, setOf(1, 4, 5),
                taxiPark(1..3, 1..5, trip(1, 1), trip(2, 1), trip(1, 4), trip(3, 4), trip(1, 5), trip(2, 5), trip(2, 2)))
    }

    @Test
    fun test2() = testFaithfulPassengers(3, setOf(0, 1, 2, 4, 5), taxiPark(0..5, 0..5,
            trip(3, listOf(2), duration = 5, distance = 4.0, discount = 0.3),
            trip(3, listOf(5, 2, 0), duration = 13, distance = 1.0, discount = 0.2),
            trip(2, listOf(1, 2, 0), duration = 33, distance = 23.0),
            trip(2, listOf(5, 4), duration = 16, distance = 5.0),
            trip(0, listOf(2, 1), duration = 37, distance = 20.0),
            trip(1, listOf(2, 4), duration = 18, distance = 22.0),
            trip(3, listOf(5), duration = 20, distance = 27.0, discount = 0.1),
            trip(1, listOf(0, 4), duration = 18, distance = 13.0, discount = 0.1),
            trip(4, listOf(1, 3), duration = 19, distance = 31.0, discount = 0.2),
            trip(3, listOf(4), duration = 29, distance = 11.0, discount = 0.1)))

    @Test
    fun test3() = testFaithfulPassengers(4, setOf(2, 4), taxiPark(0..5, 0..5,
            trip(3, listOf(2), duration = 5, distance = 4.0, discount = 0.3),
            trip(3, listOf(5, 2, 0), duration = 13, distance = 1.0, discount = 0.2),
            trip(2, listOf(1, 2, 0), duration = 33, distance = 23.0),
            trip(2, listOf(5, 4), duration = 16, distance = 5.0),
            trip(0, listOf(2, 1), duration = 37, distance = 20.0),
            trip(1, listOf(2, 4), duration = 18, distance = 22.0),
            trip(3, listOf(5), duration = 20, distance = 27.0, discount = 0.1),
            trip(1, listOf(0, 4), duration = 18, distance = 13.0, discount = 0.1),
            trip(4, listOf(1, 3), duration = 19, distance = 31.0, discount = 0.2),
            trip(3, listOf(4), duration = 29, distance = 11.0, discount = 0.1)))

    @Test
    fun test4() = testFaithfulPassengers(8, setOf(2, 3, 6, 7), taxiPark(0..3, 0..7,
            trip(1, listOf(2, 6), duration = 23, distance = 23.0, discount = 0.4),
            trip(1, listOf(3, 2, 7), duration = 29, distance = 27.0, discount = 0.1),
            trip(0, listOf(7), duration = 13, distance = 28.0, discount = 0.1),
            trip(1, listOf(5), duration = 5, distance = 0.0),
            trip(0, listOf(3, 2), duration = 12, distance = 19.0),
            trip(1, listOf(4, 3, 6, 7), duration = 18, distance = 9.0, discount = 0.3),
            trip(0, listOf(1, 3), duration = 3, distance = 32.0),
            trip(0, listOf(6, 5, 0), duration = 9, distance = 7.0),
            trip(2, listOf(6, 2, 7), duration = 19, distance = 18.0),
            trip(0, listOf(7), duration = 32, distance = 31.0, discount = 0.1),
            trip(3, listOf(4, 2, 5, 6), duration = 22, distance = 28.0),
            trip(3, listOf(3, 7, 2), duration = 36, distance = 20.0),
            trip(1, listOf(1, 3), duration = 16, distance = 5.0, discount = 0.3),
            trip(0, listOf(6), duration = 18, distance = 27.0),
            trip(3, listOf(3, 7), duration = 0, distance = 10.0),
            trip(0, listOf(2, 1, 6), duration = 9, distance = 8.0),
            trip(0, listOf(6, 4), duration = 35, distance = 31.0, discount = 0.3),
            trip(1, listOf(7), duration = 23, distance = 7.0),
            trip(1, listOf(0, 2, 3, 5), duration = 33, distance = 14.0, discount = 0.2),
            trip(0, listOf(5, 3), duration = 8, distance = 1.0))
    )

    @Test
    fun test5() = testFaithfulPassengers(0, setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), taxiPark(0..3, 0..11,
            trip(3, listOf(9), duration = 4, distance = 26.0, discount = 0.3),
            trip(0, listOf(7), duration = 16, distance = 34.0, discount = 0.2),
            trip(2, listOf(9), duration = 19, distance = 16.0),
            trip(1, listOf(4, 6, 3), duration = 0, distance = 3.0),
            trip(3, listOf(6, 11), duration = 33, distance = 10.0),
            trip(3, listOf(11, 9), duration = 20, distance = 22.0),
            trip(1, listOf(3, 4), duration = 18, distance = 19.0),
            trip(3, listOf(4, 7), duration = 0, distance = 31.0, discount = 0.3),
            trip(0, listOf(8, 7), duration = 7, distance = 14.0),
            trip(0, listOf(11, 7, 5, 8), duration = 4, distance = 1.0, discount = 0.4),
            trip(3, listOf(4, 8, 1), duration = 35, distance = 2.0),
            trip(3, listOf(1), duration = 35, distance = 30.0),
            trip(2, listOf(6, 1), duration = 23, distance = 33.0),
            trip(3, listOf(7, 6), duration = 38, distance = 9.0),
            trip(1, listOf(3, 4, 5), duration = 2, distance = 34.0, discount = 0.2),
            trip(1, listOf(4, 8, 7), duration = 5, distance = 31.0, discount = 0.1),
            trip(0, listOf(11, 4, 6), duration = 15, distance = 2.0),
            trip(3, listOf(9, 8, 6), duration = 24, distance = 17.0),
            trip(3, listOf(0), duration = 37, distance = 3.0, discount = 0.1),
            trip(1, listOf(5, 7), duration = 0, distance = 15.0, discount = 0.4))
    )

    @Test
    fun test6() = testFaithfulPassengers(1, setOf(0, 1, 3, 4, 5, 6, 7, 8, 9, 11), taxiPark(0..3, 0..11,
            trip(3, listOf(9), duration = 4, distance = 26.0, discount = 0.3),
            trip(0, listOf(7), duration = 16, distance = 34.0, discount = 0.2),
            trip(2, listOf(9), duration = 19, distance = 16.0),
            trip(1, listOf(4, 6, 3), duration = 0, distance = 3.0),
            trip(3, listOf(6, 11), duration = 33, distance = 10.0),
            trip(3, listOf(11, 9), duration = 20, distance = 22.0),
            trip(1, listOf(3, 4), duration = 18, distance = 19.0),
            trip(3, listOf(4, 7), duration = 0, distance = 31.0, discount = 0.3),
            trip(0, listOf(8, 7), duration = 7, distance = 14.0),
            trip(0, listOf(11, 7, 5, 8), duration = 4, distance = 1.0, discount = 0.4),
            trip(3, listOf(4, 8, 1), duration = 35, distance = 2.0),
            trip(3, listOf(1), duration = 35, distance = 30.0),
            trip(2, listOf(6, 1), duration = 23, distance = 33.0),
            trip(3, listOf(7, 6), duration = 38, distance = 9.0),
            trip(1, listOf(3, 4, 5), duration = 2, distance = 34.0, discount = 0.2),
            trip(1, listOf(4, 8, 7), duration = 5, distance = 31.0, discount = 0.1),
            trip(0, listOf(11, 4, 6), duration = 15, distance = 2.0),
            trip(3, listOf(9, 8, 6), duration = 24, distance = 17.0),
            trip(3, listOf(0), duration = 37, distance = 3.0, discount = 0.1),
            trip(1, listOf(5, 7), duration = 0, distance = 15.0, discount = 0.4))
    )

    @Test
    fun test7() = testFaithfulPassengers(3, setOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15), taxiPark(0..7, 0..15,
            trip(6, listOf(10, 15), duration = 18, distance = 2.0, discount = 0.2),
            trip(1, listOf(13), duration = 26, distance = 17.0),
            trip(5, listOf(5, 9), duration = 5, distance = 17.0, discount = 0.1),
            trip(2, listOf(10), duration = 6, distance = 13.0, discount = 0.4),
            trip(7, listOf(0, 6, 5), duration = 24, distance = 21.0, discount = 0.2),
            trip(0, listOf(3, 14, 10, 8), duration = 11, distance = 0.0, discount = 0.2),
            trip(1, listOf(3, 12), duration = 19, distance = 35.0, discount = 0.1),
            trip(5, listOf(4), duration = 0, distance = 33.0),
            trip(1, listOf(14), duration = 7, distance = 25.0, discount = 0.4),
            trip(4, listOf(2, 15), duration = 15, distance = 2.0),
            trip(0, listOf(11, 0), duration = 32, distance = 23.0),
            trip(1, listOf(0, 8), duration = 19, distance = 15.0, discount = 0.4),
            trip(4, listOf(11, 4), duration = 14, distance = 19.0, discount = 0.3),
            trip(4, listOf(5), duration = 16, distance = 38.0, discount = 0.3),
            trip(5, listOf(7), duration = 3, distance = 31.0, discount = 0.4),
            trip(4, listOf(9, 12), duration = 1, distance = 16.0),
            trip(4, listOf(0, 4, 15, 6), duration = 1, distance = 38.0, discount = 0.3),
            trip(7, listOf(11, 6), duration = 38, distance = 14.0),
            trip(2, listOf(1), duration = 11, distance = 19.0, discount = 0.4),
            trip(1, listOf(10, 11, 1), duration = 30, distance = 33.0),
            trip(7, listOf(0), duration = 2, distance = 23.0, discount = 0.2),
            trip(2, listOf(11, 12), duration = 14, distance = 4.0, discount = 0.2),
            trip(2, listOf(1, 12), duration = 30, distance = 39.0),
            trip(4, listOf(11), duration = 27, distance = 30.0),
            trip(6, listOf(7, 3, 5), duration = 21, distance = 18.0),
            trip(0, listOf(5, 11, 6, 13), duration = 3, distance = 26.0),
            trip(3, listOf(7, 14), duration = 39, distance = 13.0, discount = 0.4),
            trip(5, listOf(0, 2), duration = 25, distance = 9.0),
            trip(4, listOf(1, 2), duration = 36, distance = 26.0),
            trip(7, listOf(0, 5), duration = 36, distance = 9.0, discount = 0.2),
            trip(2, listOf(7, 14), duration = 8, distance = 26.0),
            trip(5, listOf(6), duration = 25, distance = 29.0, discount = 0.4),
            trip(1, listOf(14), duration = 39, distance = 31.0, discount = 0.1),
            trip(2, listOf(5, 13), duration = 7, distance = 37.0, discount = 0.1),
            trip(4, listOf(4), duration = 16, distance = 31.0, discount = 0.2),
            trip(1, listOf(13), duration = 33, distance = 31.0),
            trip(5, listOf(11, 1), duration = 23, distance = 32.0, discount = 0.2),
            trip(5, listOf(13, 2), duration = 25, distance = 8.0),
            trip(4, listOf(15), duration = 18, distance = 15.0),
            trip(4, listOf(13, 8), duration = 0, distance = 27.0))
    )
}