package games.game2048

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestGame2048Helper {

    @Test
    fun test01SimpleMove1() = testMerge(listOf(null, null, null, "a"), listOf("a"))

    @Test
    fun test02SimpleMove2() = testMerge(listOf("b", null, null, "a"), listOf("b", "a"))

    @Test
    fun test03SimpleMove3() = testMerge(listOf(null, "b", null, "a"), listOf("b", "a"))

    @Test
    fun test04SimpleMerge1() = testMerge(listOf("a", "a", null, null), listOf("aa"))

    @Test
    fun test05SimpleMerge2() = testMerge(listOf(null, "a", "a", null), listOf("aa"))

    @Test
    fun test06SimpleMerge3() = testMerge(listOf(null, null, "a", "a"), listOf("aa"))

    @Test
    fun test07SimpleMerge4() = testMerge(listOf("a", null, "a", null), listOf("aa"))

    @Test
    fun test08SimpleMerge5() = testMerge(listOf("a", null, null, "a"), listOf("aa"))

    @Test
    fun test09SimpleMerge6() = testMerge(listOf(null, "a", null, "a"), listOf("aa"))

    @Test
    fun test10MergeWithExtraElement1() = testMerge(listOf("a", null, "a", "a"), listOf("aa", "a"))

    @Test
    fun test11MergeWithExtraElement2() = testMerge(listOf("a", null, "a", "b"), listOf("aa", "b"))

    @Test
    fun test12MergeWithExtraElement3() = testMerge(listOf("a", "a", null, "b"), listOf("aa", "b"))

    @Test
    fun test13NoMerge1() = testMerge(listOf("a", "b", "a", null), listOf("a", "b", "a"))

    @Test
    fun test14NoMerge2() = testMerge(listOf("a", null, "b", "a"), listOf("a", "b", "a"))

    @Test
    fun test15MergeInFull1() = testMerge(listOf("a", "a", "b", "a"), listOf("aa", "b", "a"))

    @Test
    fun test16MergeInFull2() = testMerge(listOf("a", "a", "b", "b"), listOf("aa", "bb"))

    @Test
    fun test17MergeOfThree1() = testMerge(listOf("a", "a", "a", null), listOf("aa", "a"))

    @Test
    fun test18MergeOfThree2() = testMerge(listOf("a", null, "a", "a"), listOf("aa", "a"))

    private fun testMerge(input: List<String?>, expected: List<String?>) {
        val result = input.moveAndMergeEqual { it.repeat(2) }
        Assert.assertEquals("Wrong result for $input.moveAndMergeEqual()",
                expected, result)
    }
}