package taxipark

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTask1FakeDrivers {
    private fun testFakeDrivers(fakeDriverIndexes: Set<Int>, tp: TaxiPark) {
        val message = "Wrong result for 'findFakeDrivers()'." + tp.display()
        val expected = fakeDriverIndexes.map { driver(it) }.toSet()
        Assert.assertEquals(message, expected, tp.findFakeDrivers())
    }

    @Test
    fun test0() = testFakeDrivers(setOf(0, 1, 2), taxiPark(0..2, 0..4))

    @Test
    fun test1() = testFakeDrivers(setOf(2, 3), taxiPark(1..3, 1..2, trip(1, 1), trip(1, 2)))

    @Test
    fun test2() = testFakeDrivers(setOf(3), taxiPark(0..3, 0..9,
            trip(2, listOf(9), duration = 9, distance = 36.0),
            trip(1, listOf(0), duration = 15, distance = 28.0),
            trip(2, listOf(1), duration = 37, distance = 30.0),
            trip(0, listOf(9), duration = 24, distance = 10.0),
            trip(1, listOf(2), duration = 1, distance = 6.0),
            trip(0, listOf(0, 9), duration = 9, distance = 7.0),
            trip(2, listOf(3, 2, 8), duration = 18, distance = 39.0, discount = 0.1),
            trip(1, listOf(9, 4), duration = 19, distance = 1.0, discount = 0.2),
            trip(1, listOf(3), duration = 16, distance = 23.0),
            trip(2, listOf(7), duration = 10, distance = 31.0, discount = 0.2)))

    @Test
    fun test3() = testFakeDrivers(setOf(0, 2, 4), taxiPark(0..4, 0..9,
            trip(3, listOf(2), duration = 24, distance = 7.0),
            trip(3, listOf(8, 5, 9), duration = 30, distance = 23.0, discount = 0.4),
            trip(3, listOf(4, 9, 3, 7), duration = 24, distance = 8.0),
            trip(1, listOf(2), duration = 32, distance = 27.0, discount = 0.2),
            trip(3, listOf(0, 5, 7, 6), duration = 38, distance = 3.0, discount = 0.2),
            trip(3, listOf(8, 0), duration = 6, distance = 39.0),
            trip(1, listOf(3, 1), duration = 18, distance = 39.0, discount = 0.2),
            trip(3, listOf(6, 5), duration = 19, distance = 21.0),
            trip(1, listOf(8, 0), duration = 5, distance = 5.0),
            trip(3, listOf(3, 7, 9), duration = 24, distance = 20.0)))

    @Test
    fun test4() = testFakeDrivers(setOf(0, 1, 4, 5, 8), taxiPark(0..9, 0..19,
            trip(6, listOf(0), duration = 36, distance = 1.0, discount = 0.3),
            trip(7, listOf(3, 5), duration = 34, distance = 11.0),
            trip(9, listOf(15, 1), duration = 13, distance = 12.0),
            trip(3, listOf(7), duration = 15, distance = 30.0, discount = 0.2),
            trip(9, listOf(8, 6, 7, 11), duration = 36, distance = 16.0),
            trip(3, listOf(11, 8, 15, 6), duration = 37, distance = 32.0, discount = 0.4),
            trip(2, listOf(1, 6), duration = 12, distance = 15.0, discount = 0.1),
            trip(2, listOf(3, 2, 19), duration = 2, distance = 11.0, discount = 0.2),
            trip(3, listOf(7, 5), duration = 26, distance = 10.0, discount = 0.3),
            trip(3, listOf(6, 4), duration = 10, distance = 35.0),
            trip(7, listOf(7, 14), duration = 27, distance = 2.0, discount = 0.3),
            trip(3, listOf(3, 11), duration = 1, distance = 33.0),
            trip(7, listOf(3), duration = 26, distance = 4.0, discount = 0.4),
            trip(2, listOf(18, 7), duration = 15, distance = 6.0, discount = 0.4),
            trip(2, listOf(0, 1, 2), duration = 30, distance = 17.0),
            trip(2, listOf(0, 11), duration = 32, distance = 5.0, discount = 0.4),
            trip(9, listOf(0, 15), duration = 27, distance = 3.0),
            trip(9, listOf(11, 15), duration = 11, distance = 15.0, discount = 0.2),
            trip(2, listOf(12, 14, 8), duration = 31, distance = 34.0),
            trip(7, listOf(15, 11), duration = 1, distance = 3.0)))

    @Test
    fun test5() = testFakeDrivers(setOf(2, 7, 8, 11, 14, 15, 16, 18), taxiPark(0..19, 0..19,
            trip(3, listOf(14, 15, 3), duration = 10, distance = 24.0, discount = 0.4),
            trip(17, listOf(11), duration = 39, distance = 31.0, discount = 0.2),
            trip(1, listOf(15), duration = 3, distance = 12.0, discount = 0.4),
            trip(5, listOf(19, 0, 15, 8), duration = 15, distance = 13.0, discount = 0.2),
            trip(10, listOf(16, 1, 9), duration = 23, distance = 15.0, discount = 0.2),
            trip(1, listOf(11), duration = 6, distance = 13.0),
            trip(1, listOf(9, 1), duration = 16, distance = 26.0, discount = 0.1),
            trip(10, listOf(14), duration = 28, distance = 17.0),
            trip(5, listOf(8), duration = 28, distance = 8.0),
            trip(13, listOf(0, 9), duration = 31, distance = 28.0, discount = 0.3),
            trip(12, listOf(4), duration = 28, distance = 34.0),
            trip(9, listOf(9, 13, 4, 0), duration = 38, distance = 29.0),
            trip(3, listOf(15), duration = 4, distance = 31.0, discount = 0.3),
            trip(13, listOf(18, 1, 7), duration = 38, distance = 16.0, discount = 0.4),
            trip(6, listOf(11, 15, 3), duration = 15, distance = 11.0, discount = 0.1),
            trip(5, listOf(13), duration = 3, distance = 17.0),
            trip(4, listOf(4), duration = 26, distance = 24.0, discount = 0.3),
            trip(6, listOf(2, 18), duration = 35, distance = 13.0, discount = 0.1),
            trip(19, listOf(5), duration = 3, distance = 24.0, discount = 0.2),
            trip(0, listOf(7, 19, 18, 16), duration = 20, distance = 14.0, discount = 0.4)))

    @Test
    fun test6() = testFakeDrivers(setOf(5, 7), taxiPark(0..9, 0..19,
            trip(8, listOf(1, 18), duration = 33, distance = 28.0, discount = 0.2),
            trip(8, listOf(0), duration = 13, distance = 35.0, discount = 0.4),
            trip(9, listOf(9), duration = 8, distance = 29.0, discount = 0.4),
            trip(9, listOf(15, 3), duration = 34, distance = 34.0),
            trip(6, listOf(4, 7), duration = 35, distance = 13.0, discount = 0.4),
            trip(1, listOf(15, 6, 11), duration = 36, distance = 29.0, discount = 0.4),
            trip(6, listOf(9, 12, 6), duration = 21, distance = 36.0),
            trip(6, listOf(13, 8), duration = 27, distance = 6.0, discount = 0.4),
            trip(8, listOf(4, 0), duration = 31, distance = 38.0, discount = 0.2),
            trip(4, listOf(15, 19, 7), duration = 21, distance = 26.0, discount = 0.2),
            trip(4, listOf(12, 7, 11, 15), duration = 4, distance = 26.0, discount = 0.4),
            trip(3, listOf(5, 4), duration = 27, distance = 27.0, discount = 0.2),
            trip(6, listOf(13, 17, 0, 14), duration = 33, distance = 35.0, discount = 0.4),
            trip(9, listOf(0, 14, 18), duration = 24, distance = 10.0, discount = 0.2),
            trip(4, listOf(7, 8, 6, 2), duration = 29, distance = 11.0, discount = 0.3),
            trip(0, listOf(9), duration = 36, distance = 20.0),
            trip(1, listOf(17), duration = 36, distance = 4.0),
            trip(8, listOf(19, 10), duration = 24, distance = 27.0),
            trip(8, listOf(11, 18), duration = 23, distance = 33.0, discount = 0.3),
            trip(4, listOf(10), duration = 33, distance = 8.0),
            trip(0, listOf(13), duration = 32, distance = 11.0),
            trip(0, listOf(12), duration = 39, distance = 37.0, discount = 0.2),
            trip(6, listOf(5, 19, 10), duration = 19, distance = 20.0, discount = 0.4),
            trip(2, listOf(19, 10), duration = 17, distance = 18.0, discount = 0.2),
            trip(4, listOf(0), duration = 5, distance = 14.0, discount = 0.3),
            trip(3, listOf(14), duration = 1, distance = 39.0),
            trip(8, listOf(9), duration = 32, distance = 17.0, discount = 0.2),
            trip(1, listOf(0), duration = 38, distance = 35.0, discount = 0.3),
            trip(3, listOf(19), duration = 30, distance = 5.0),
            trip(1, listOf(18), duration = 4, distance = 12.0),
            trip(2, listOf(0), duration = 21, distance = 23.0, discount = 0.1),
            trip(6, listOf(17), duration = 4, distance = 27.0, discount = 0.2),
            trip(0, listOf(13, 18), duration = 19, distance = 31.0, discount = 0.3),
            trip(4, listOf(5, 14), duration = 17, distance = 4.0, discount = 0.2),
            trip(3, listOf(16), duration = 35, distance = 1.0),
            trip(2, listOf(5, 8, 12), duration = 34, distance = 20.0, discount = 0.3),
            trip(9, listOf(1, 11), duration = 37, distance = 9.0, discount = 0.2),
            trip(1, listOf(19, 15), duration = 30, distance = 5.0, discount = 0.4),
            trip(9, listOf(4), duration = 3, distance = 0.0, discount = 0.3),
            trip(2, listOf(11, 10, 14, 1), duration = 27, distance = 29.0, discount = 0.4)))
}