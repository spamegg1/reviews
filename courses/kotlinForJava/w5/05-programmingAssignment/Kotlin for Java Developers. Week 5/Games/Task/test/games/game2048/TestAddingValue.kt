package games.game2048

import org.junit.Assert
import org.junit.Test

class TestAddingValue : AbstractTestGameWithSmallNumbers() {
    @Test
    fun test1() = testAddingOneNumber("---- ---- -2-- ----")

    @Test
    fun test2() = testAddingOneNumber("2222 ---- ---- ----")

    @Test
    fun test3() = testAddingOneNumber("2--- 4--- -2-- ---8")

    @Test
    fun test4() = testAddingOneNumber("-248 2-2- -2-8 4442")

    private fun testAddingOneNumber(input: String) {
        val inputBoard = TestBoard(input)
        val board = createBoard(inputBoard)
        board.addNewValue(RandomGame2048Initializer)
        val result = board.toTestBoard()
        Assert.assertEquals(
                buildString {
                    appendln("Only one element should be different after adding a new element.")
                    appendln("Input:")
                    appendln("$inputBoard")
                    appendln("Result:")
                    appendln("$result")
                },
                1, inputBoard.board.indices.count { input[it] != result.board[it] })
    }
}