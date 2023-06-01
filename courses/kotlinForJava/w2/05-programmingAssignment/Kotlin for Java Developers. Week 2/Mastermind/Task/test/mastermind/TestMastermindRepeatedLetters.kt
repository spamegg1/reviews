package mastermind

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestMastermindRepeatedLetters {

    @Test
    fun test01Sample() = testEvaluation("AABC", "ADFE", 1, 0)

    @Test
    fun test02Sample() = testEvaluation("AABC", "ADFA", 1, 1)

    @Test
    fun test03Sample() = testEvaluation("AABC", "DFAA", 0, 2)

    @Test
    fun test04Sample() = testEvaluation("AABC", "DEFA", 0, 1)

    @Test
    fun test05() = testEvaluation("ABCD", "EAAA", 0, 1)

    @Test
    fun test06() = testEvaluation("AABC", "ADFA", 1, 1)

    @Test
    fun test07() = testEvaluation("AABC", "DEFA", 0, 1)

    @Test
    fun test08() = testEvaluation("EDEB", "CBFE", 0, 2)

    @Test
    fun test09() = testEvaluation("CFDF", "FCCD", 0, 3)

    @Test
    fun test10() = testEvaluation("AABC", "AEFD", 1, 0)

    @Test
    fun test11() = testEvaluation("DCFC", "ABEC", 1, 0)

    @Test
    fun test12() = testEvaluation("FDCD", "FBAD", 2, 0)

    @Test
    fun test13() = testEvaluation("DEFA", "AABC", 0, 1)

    @Test
    fun test14() = testEvaluation("DAAE", "AABC", 1, 1)

    @Test
    fun test15() = testEvaluation("BBDC", "DFBB", 0, 3)

    @Test
    fun test16() = testEvaluation("DBFF", "FFDD", 0, 3)

    @Test
    fun test17() = testEvaluation("BDAD", "AAAE", 1, 0)

    @Test
    fun test18() = testEvaluation("FDDB", "CABB", 1, 0)

    @Test
    fun test19() = testEvaluation("BDBC", "DDFC", 2, 0)

    @Test
    fun test20() = testEvaluation("ECDE", "CEEE", 1, 2)

    @Test
    fun test21() = testEvaluation("AAAF", "ABCA", 1, 1)

    @Test
    fun test22() = testEvaluation("BCDA", "AFEA", 1, 0)

    @Test
    fun test23() = testEvaluation("EEEE", "AFEA", 1, 0)

    @Test
    fun test24() = testEvaluation("EEBE", "AFEA", 0, 1)

    @Test
    fun test25() = testEvaluation("EEAD", "EEEE", 2, 0)

    @Test
    fun test26() = testEvaluation("BACD", "EAFF", 1, 0)
}
