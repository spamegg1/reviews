package taxipark

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTask6ParetoPrinciple {

    private fun testPareto(expected: Boolean, tp: TaxiPark) {
        val message = "Wrong result for 'checkParetoPrinciple()'." + tp.display()
        Assert.assertEquals(message, expected, tp.checkParetoPrinciple())
    }

    @Test
    fun testParetoPrincipleSucceeds() {
        testPareto(true, taxiPark(1..5, 1..4,
                trip(1, 1, 20, 20.0),
                trip(1, 2, 20, 20.0),
                trip(1, 3, 20, 20.0),
                trip(1, 4, 20, 20.0),
                trip(2, 1, 20, 20.0)))
        // The income of driver #1: 160.0;
        // the total income of drivers #2..5: 40.0.
        // The first driver constitutes exactly 20% of the total of five drivers
        // and his relative income is 160.0 / 200.0 = 80%.
    }

    @Test
    fun testParetoPrincipleFails() {
        testPareto(false, taxiPark(1..5, 1..4,
                trip(1, 1, 20, 20.0),
                trip(1, 2, 20, 20.0),
                trip(1, 3, 20, 20.0),
                trip(2, 4, 20, 20.0),
                trip(3, 1, 20, 20.0)))
        // The income of driver #1: 120.0;
        // the total income of drivers #2..5: 80.0.
        // The first driver constitutes 20% of the total of five drivers
        // but his relative income is 120.0 / 200.0 = 60%
        // which is less than 80%.
    }

    @Test
    fun testParetoPrincipleNoTrips() = testPareto(false, taxiPark(1..5, 1..4))

    @Test
    fun test1() = testPareto(true, taxiPark(1..5, 1..4,
            trip(1, 1, 20, 20.0),
            trip(1, 2, 20, 20.0),
            trip(1, 3, 20, 20.0),
            trip(1, 4, 20, 20.0),
            trip(2, 1, 20, 19.0)))

    @Test
    fun test2() = testPareto(false, taxiPark(1..5, 1..4,
            trip(1, 1, 20, 20.0),
            trip(1, 2, 20, 20.0),
            trip(1, 3, 20, 20.0),
            trip(1, 4, 20, 20.0),
            trip(2, 1, 20, 21.0)))

    @Test
    fun test3() = testPareto(true, taxiPark(0..4, 0..9,
            trip(4, listOf(2), duration = 3, distance = 23.0, discount = 0.3),
            trip(4, listOf(4), duration = 27, distance = 8.0, discount = 0.1),
            trip(4, listOf(7), duration = 25, distance = 29.0, discount = 0.2),
            trip(3, listOf(7), duration = 1, distance = 8.0, discount = 0.4),
            trip(0, listOf(2), duration = 18, distance = 2.0, discount = 0.3),
            trip(4, listOf(7), duration = 26, distance = 27.0),
            trip(4, listOf(9), duration = 11, distance = 23.0),
            trip(4, listOf(4, 2, 0), duration = 5, distance = 20.0, discount = 0.1),
            trip(2, listOf(6, 7), duration = 4, distance = 13.0),
            trip(4, listOf(2, 8, 1, 4), duration = 19, distance = 24.0, discount = 0.3),
            trip(4, listOf(6), duration = 17, distance = 19.0),
            trip(0, listOf(9), duration = 15, distance = 7.0, discount = 0.2),
            trip(0, listOf(7, 3), duration = 0, distance = 10.0, discount = 0.2),
            trip(4, listOf(9, 3), duration = 15, distance = 13.0),
            trip(3, listOf(0), duration = 11, distance = 3.0, discount = 0.2)))

    @Test
    fun test4() = testPareto(true, taxiPark(0..4, 0..9,
            trip(4, listOf(7, 1, 0), duration = 3, distance = 3.0, discount = 0.1),
            trip(3, listOf(2), duration = 14, distance = 27.0, discount = 0.2),
            trip(3, listOf(6), duration = 25, distance = 21.0),
            trip(1, listOf(3), duration = 9, distance = 0.0),
            trip(0, listOf(7, 3, 2, 0), duration = 4, distance = 11.0, discount = 0.4),
            trip(3, listOf(9, 1, 2, 3), duration = 28, distance = 17.0),
            trip(3, listOf(3, 1), duration = 5, distance = 17.0, discount = 0.1),
            trip(3, listOf(0, 1, 2), duration = 22, distance = 26.0),
            trip(3, listOf(3, 8), duration = 15, distance = 21.0),
            trip(3, listOf(6, 5), duration = 21, distance = 6.0, discount = 0.1),
            trip(3, listOf(4), duration = 10, distance = 13.0),
            trip(3, listOf(2, 7), duration = 13, distance = 22.0, discount = 0.1),
            trip(1, listOf(4, 9), duration = 22, distance = 5.0, discount = 0.4),
            trip(4, listOf(8, 7, 9), duration = 10, distance = 19.0, discount = 0.4),
            trip(4, listOf(6), duration = 2, distance = 26.0, discount = 0.3)))

    @Test
    fun test5() = testPareto(false, taxiPark(0..5, 0..9,
            trip(1, listOf(6, 1), duration = 22, distance = 12.0),
            trip(0, listOf(5, 6), duration = 17, distance = 18.0),
            trip(1, listOf(9, 7, 3, 1), duration = 30, distance = 14.0, discount = 0.1),
            trip(0, listOf(2), duration = 0, distance = 2.0, discount = 0.1),
            trip(0, listOf(4), duration = 0, distance = 1.0, discount = 0.4),
            trip(0, listOf(7, 2, 4), duration = 25, distance = 24.0, discount = 0.1),
            trip(0, listOf(2), duration = 28, distance = 2.0),
            trip(0, listOf(9, 1, 3), duration = 6, distance = 15.0),
            trip(0, listOf(0), duration = 27, distance = 22.0),
            trip(0, listOf(4, 5, 7, 3), duration = 4, distance = 26.0, discount = 0.1)))

    @Test
    fun test7() = testPareto(true, taxiPark(0..9, 0..19,
            trip(6, listOf(10, 3), duration = 16, distance = 3.0),
            trip(1, listOf(17, 1, 6), duration = 28, distance = 24.0, discount = 0.4),
            trip(1, listOf(13, 14), duration = 10, distance = 19.0, discount = 0.1),
            trip(3, listOf(12, 0, 18), duration = 4, distance = 17.0, discount = 0.2),
            trip(7, listOf(8, 0, 2, 7), duration = 14, distance = 22.0, discount = 0.1),
            trip(8, listOf(15, 12), duration = 22, distance = 3.0, discount = 0.4),
            trip(9, listOf(2, 4, 6, 0), duration = 20, distance = 0.0),
            trip(9, listOf(14, 18), duration = 18, distance = 1.0, discount = 0.3),
            trip(4, listOf(1, 0), duration = 2, distance = 10.0),
            trip(7, listOf(16, 10, 9, 6), duration = 24, distance = 21.0, discount = 0.1),
            trip(7, listOf(9), duration = 12, distance = 25.0, discount = 0.4),
            trip(1, listOf(16, 1), duration = 22, distance = 19.0),
            trip(7, listOf(17, 7, 5), duration = 15, distance = 10.0),
            trip(6, listOf(12, 13, 10), duration = 4, distance = 13.0, discount = 0.3),
            trip(4, listOf(14), duration = 10, distance = 7.0, discount = 0.3),
            trip(1, listOf(2, 10, 3), duration = 25, distance = 1.0),
            trip(1, listOf(6, 11), duration = 18, distance = 19.0),
            trip(6, listOf(15), duration = 7, distance = 5.0, discount = 0.3),
            trip(1, listOf(18, 3), duration = 29, distance = 28.0),
            trip(7, listOf(14), duration = 17, distance = 25.0, discount = 0.3),
            trip(6, listOf(10), duration = 11, distance = 19.0, discount = 0.3),
            trip(7, listOf(15, 19), duration = 22, distance = 9.0),
            trip(7, listOf(15, 19, 0), duration = 12, distance = 22.0),
            trip(8, listOf(9, 19), duration = 1, distance = 4.0),
            trip(7, listOf(5), duration = 19, distance = 21.0, discount = 0.3),
            trip(1, listOf(12, 2, 1), duration = 16, distance = 12.0),
            trip(7, listOf(13), duration = 10, distance = 17.0, discount = 0.2),
            trip(7, listOf(19), duration = 17, distance = 28.0, discount = 0.4),
            trip(0, listOf(10, 8), duration = 3, distance = 5.0, discount = 0.3),
            trip(1, listOf(6, 17, 12), duration = 15, distance = 11.0),
            trip(7, listOf(19), duration = 27, distance = 29.0),
            trip(1, listOf(12, 11, 7, 5), duration = 22, distance = 21.0, discount = 0.1),
            trip(8, listOf(11, 3, 2, 15), duration = 9, distance = 0.0),
            trip(1, listOf(5, 8, 16), duration = 22, distance = 28.0),
            trip(7, listOf(1), duration = 26, distance = 14.0),
            trip(1, listOf(7, 5), duration = 22, distance = 15.0),
            trip(8, listOf(14, 16), duration = 4, distance = 8.0),
            trip(9, listOf(12, 6), duration = 7, distance = 0.0),
            trip(4, listOf(1, 17, 2, 16), duration = 0, distance = 15.0),
            trip(7, listOf(4, 18), duration = 23, distance = 21.0, discount = 0.2))
    )

    @Test
    fun test8() = testPareto(false, taxiPark(0..9, 0..19,
            trip(5, listOf(0, 3, 11), duration = 9, distance = 6.0, discount = 0.2),
            trip(1, listOf(11, 17, 19, 10), duration = 13, distance = 27.0, discount = 0.3),
            trip(1, listOf(19, 10, 6), duration = 29, distance = 26.0),
            trip(1, listOf(16, 19), duration = 9, distance = 22.0),
            trip(1, listOf(3, 4), duration = 29, distance = 13.0),
            trip(9, listOf(5, 9), duration = 28, distance = 3.0, discount = 0.3),
            trip(4, listOf(0, 11), duration = 19, distance = 10.0),
            trip(8, listOf(19, 13, 2, 8), duration = 17, distance = 9.0, discount = 0.4),
            trip(4, listOf(16, 17), duration = 11, distance = 16.0, discount = 0.1),
            trip(4, listOf(16, 0, 13), duration = 15, distance = 20.0),
            trip(8, listOf(1), duration = 15, distance = 15.0, discount = 0.3),
            trip(1, listOf(7, 8), duration = 19, distance = 23.0, discount = 0.3),
            trip(1, listOf(3), duration = 9, distance = 20.0, discount = 0.2),
            trip(2, listOf(16), duration = 16, distance = 9.0, discount = 0.4),
            trip(9, listOf(9), duration = 14, distance = 6.0, discount = 0.4),
            trip(6, listOf(9, 2), duration = 2, distance = 2.0),
            trip(1, listOf(6, 7), duration = 15, distance = 13.0, discount = 0.2),
            trip(4, listOf(14), duration = 29, distance = 20.0),
            trip(1, listOf(3, 18), duration = 27, distance = 29.0, discount = 0.2),
            trip(4, listOf(14, 12), duration = 15, distance = 7.0),
            trip(4, listOf(0, 5, 6), duration = 19, distance = 28.0),
            trip(8, listOf(3, 10, 0), duration = 3, distance = 11.0),
            trip(7, listOf(12, 13, 9), duration = 2, distance = 8.0, discount = 0.2),
            trip(0, listOf(0), duration = 1, distance = 18.0),
            trip(0, listOf(7), duration = 4, distance = 3.0),
            trip(4, listOf(8), duration = 9, distance = 17.0),
            trip(1, listOf(0, 15), duration = 14, distance = 16.0, discount = 0.2),
            trip(1, listOf(0), duration = 19, distance = 10.0),
            trip(1, listOf(9), duration = 28, distance = 21.0, discount = 0.3),
            trip(1, listOf(0, 18), duration = 24, distance = 17.0),
            trip(4, listOf(17, 8, 9), duration = 28, distance = 13.0),
            trip(1, listOf(8, 6, 4), duration = 15, distance = 21.0, discount = 0.3),
            trip(1, listOf(19), duration = 19, distance = 14.0),
            trip(5, listOf(15), duration = 2, distance = 1.0),
            trip(6, listOf(19, 6), duration = 7, distance = 13.0),
            trip(4, listOf(2), duration = 18, distance = 20.0),
            trip(1, listOf(8), duration = 24, distance = 29.0, discount = 0.4),
            trip(1, listOf(13, 0, 7, 2), duration = 15, distance = 20.0, discount = 0.2),
            trip(2, listOf(2, 14), duration = 19, distance = 8.0, discount = 0.2),
            trip(6, listOf(19, 13), duration = 6, distance = 17.0, discount = 0.3)))

    @Test
    fun test9() = testPareto(true, taxiPark(0..19, 0..99,
            trip(7, listOf(72), duration = 24, distance = 15.0, discount = 0.4),
            trip(6, listOf(10, 57, 75, 35), duration = 35, distance = 7.0, discount = 0.3),
            trip(12, listOf(20, 70, 64), duration = 8, distance = 14.0, discount = 0.1),
            trip(13, listOf(14, 75, 71, 88), duration = 0, distance = 22.0, discount = 0.4),
            trip(9, listOf(10, 94), duration = 14, distance = 15.0),
            trip(8, listOf(51, 69, 18), duration = 3, distance = 26.0),
            trip(8, listOf(94, 43), duration = 30, distance = 29.0, discount = 0.3),
            trip(8, listOf(8), duration = 31, distance = 37.0),
            trip(8, listOf(20, 60), duration = 38, distance = 22.0, discount = 0.4),
            trip(1, listOf(62, 10), duration = 4, distance = 26.0, discount = 0.4),
            trip(9, listOf(74), duration = 31, distance = 35.0),
            trip(9, listOf(43, 29), duration = 21, distance = 16.0, discount = 0.1),
            trip(5, listOf(93, 32, 92), duration = 18, distance = 14.0, discount = 0.4),
            trip(7, listOf(97, 85, 16), duration = 22, distance = 3.0, discount = 0.2),
            trip(8, listOf(83, 73), duration = 38, distance = 37.0, discount = 0.3),
            trip(6, listOf(93), duration = 21, distance = 34.0),
            trip(1, listOf(52), duration = 6, distance = 9.0),
            trip(6, listOf(39, 19, 49, 68), duration = 39, distance = 1.0),
            trip(19, listOf(79, 21, 77), duration = 21, distance = 22.0, discount = 0.1),
            trip(9, listOf(81, 56, 67), duration = 29, distance = 28.0),
            trip(8, listOf(63, 83), duration = 32, distance = 26.0),
            trip(9, listOf(48, 56), duration = 28, distance = 19.0, discount = 0.4),
            trip(9, listOf(31), duration = 30, distance = 8.0, discount = 0.1),
            trip(9, listOf(8), duration = 34, distance = 21.0),
            trip(19, listOf(59, 14, 96), duration = 37, distance = 3.0, discount = 0.1),
            trip(9, listOf(74), duration = 13, distance = 33.0),
            trip(13, listOf(2, 64), duration = 5, distance = 9.0, discount = 0.2),
            trip(6, listOf(86), duration = 11, distance = 33.0),
            trip(8, listOf(68), duration = 34, distance = 35.0, discount = 0.4),
            trip(8, listOf(28), duration = 28, distance = 33.0),
            trip(4, listOf(80, 64, 12), duration = 15, distance = 3.0, discount = 0.4),
            trip(9, listOf(24, 68), duration = 18, distance = 23.0),
            trip(19, listOf(76, 68, 42, 56), duration = 0, distance = 30.0),
            trip(10, listOf(3, 53, 8, 59), duration = 6, distance = 19.0),
            trip(18, listOf(29, 78), duration = 14, distance = 16.0, discount = 0.4),
            trip(6, listOf(25, 40, 64), duration = 32, distance = 22.0, discount = 0.4),
            trip(19, listOf(92, 43, 25, 84), duration = 28, distance = 36.0),
            trip(8, listOf(1), duration = 7, distance = 28.0),
            trip(15, listOf(76), duration = 1, distance = 26.0, discount = 0.2),
            trip(9, listOf(58, 54, 8, 47), duration = 0, distance = 39.0, discount = 0.1),
            trip(6, listOf(97), duration = 23, distance = 11.0),
            trip(6, listOf(47, 36), duration = 17, distance = 12.0),
            trip(15, listOf(0, 13), duration = 16, distance = 8.0),
            trip(4, listOf(29, 53, 37, 0), duration = 9, distance = 23.0, discount = 0.2),
            trip(15, listOf(82), duration = 7, distance = 23.0, discount = 0.2),
            trip(14, listOf(3, 56), duration = 2, distance = 25.0),
            trip(8, listOf(33, 26), duration = 29, distance = 33.0),
            trip(1, listOf(13), duration = 35, distance = 1.0, discount = 0.3),
            trip(9, listOf(32, 33), duration = 30, distance = 4.0),
            trip(19, listOf(28), duration = 9, distance = 39.0, discount = 0.3))
    )
}