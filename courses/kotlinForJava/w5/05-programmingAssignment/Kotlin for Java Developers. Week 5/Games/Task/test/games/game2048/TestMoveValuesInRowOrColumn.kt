package games.game2048

import board.Cell
import board.SquareBoard
import org.junit.Assert
import org.junit.Test

class TestMoveValuesInRowOrColumn : AbstractTestGameWithSmallNumbers() {
    private val defaultInput = """-2-4 2--- ---- 4---"""

    @Test
    fun testRow() = testMoveInRowOrColumn({ it.getRow(1, 1..4) }, "Row(1, 1..4)",
            "24-- 2--- ---- 4---")

    @Test
    fun testRowReversed() = testMoveInRowOrColumn({ it.getRow(1, 4 downTo 1) }, "Row(1, 4 downTo 1)",
            "--24 2--- ---- 4---")


    @Test
    fun testColumn() = testMoveInRowOrColumn({ it.getColumn(1..4, 1) }, "Column(1..4, 1)",
            "22-4 4--- ---- ----")

    @Test
    fun testColumnReversed() = testMoveInRowOrColumn({ it.getColumn(4 downTo 1, 1) }, "Column(4 downTo 1, 1)",
            "-2-4 ---- 2--- 4---")

    @Test
    fun testNoMove() = testMoveInRowOrColumn({ it.getRow(1, 1..4) }, "Row(1, 1..4)",
            "2424 ---- ---- ----", "2424 ---- ---- ----", expectedMove = false)

    private fun testMoveInRowOrColumn(
            getRowOrColumn: (SquareBoard) -> List<Cell>,
            rowOrColumnName: String,
            expected: String,
            input: String = defaultInput,
            expectedMove: Boolean = true
    ) {
        val inputBoard = TestBoard(input)
        val board = createBoard(inputBoard)
        val rowOrColumn = getRowOrColumn(board)
        val actualMove = board.moveValuesInRowOrColumn(rowOrColumn)

        Assert.assertEquals("Incorrect move in $rowOrColumnName.\nInput:\n$inputBoard\n",
                TestBoard(expected), board.toTestBoard())

        Assert.assertEquals("The 'moveValuesInRowOrColumn' method returns incorrect result for input:\n$inputBoard",
                expectedMove, actualMove)
    }
}