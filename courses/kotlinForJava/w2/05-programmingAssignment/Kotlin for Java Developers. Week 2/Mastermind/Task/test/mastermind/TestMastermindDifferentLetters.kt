package mastermind

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestMastermindDifferentLetters {

    @Test
    fun test01SampleEqual() = testEvaluation("ABCD", "ABCD", 4, 0)

    @Test
    fun test02SampleOnlyWrongPositions() = testEvaluation("ABCD", "CDBA", 0, 4)

    @Test
    fun test03SampleSwap() = testEvaluation("ABCD", "ABDC", 2, 2)

    @Test
    fun test04RightPositions() = testEvaluation("ABCD", "ABCF", 3, 0)

    @Test
    fun test05WrongPositions() = testEvaluation("DAEF", "FECA", 0, 3)

    @Test
    fun test06BothRightAndWrong() = testEvaluation("ACEB", "BCDF", 1, 1)

    @Test
    fun test07() = testEvaluation("FBAE", "ABCD", 1, 1)

    @Test
    fun test08() = testEvaluation("FBAE", "AFDC", 0, 2)

    @Test
    fun test09() = testEvaluation("FBAE", "CBAE", 3, 0)

    @Test
    fun test10() = testEvaluation("FBAE", "CBFE", 2, 1)

    @Test
    fun test11() = testEvaluation("FBAE", "FBAE", 4, 0)

    @Test
    fun test12() = testEvaluation("EBAC", "ABCD", 1, 2)

    @Test
    fun test13() = testEvaluation("EBAC", "AFCB", 0, 3)

    @Test
    fun test14() = testEvaluation("EBAC", "CBDF", 1, 1)

    @Test
    fun test15() = testEvaluation("EBAC", "EBAC", 4, 0)
}
