package games.game2048

import org.junit.Assert
import org.junit.Test

class TestGame2048Initializer : AbstractTestGameWithSmallNumbers() {
    private val prefix = "Testing RandomGame2048Initializer.nextValue():"

    @Test
    fun test0() {
        val board = createBoard("4248 2824 8248 2482")
        val nextValue = RandomGame2048Initializer.nextValue(board)
        Assert.assertNull("$prefix new elements can't be added to a full board", nextValue)
    }

    @Test
    fun test1() = testNextValue("0000 0000 0200 0000")

    @Test
    fun test2() = testNextValue("2222 0000 0000 0000")

    @Test
    fun test3() = testNextValue("2000 4000 0200 0008")

    @Test
    fun test4() = testNextValue("0248 2020 0208 4442")

    private fun testNextValue(input: String) {
        val board = createBoard(input)
        val (cell, value) = RandomGame2048Initializer.nextValue(board)
                ?: throw AssertionError("$prefix Next value should be non-null for $board")
        val empty = board.filter { it == null }
        Assert.assertTrue("$prefix a value might be added only to one of the empty cells", cell in empty)
        Assert.assertTrue("$prefix new element can only be 2 or 4", value in setOf(2, 4))
    }
}