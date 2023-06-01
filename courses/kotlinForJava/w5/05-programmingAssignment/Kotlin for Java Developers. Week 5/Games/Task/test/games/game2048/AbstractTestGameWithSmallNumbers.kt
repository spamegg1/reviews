package games.game2048

import board.GameBoard
import board.createGameBoard

abstract class AbstractTestGameWithSmallNumbers {
    private val width = 4

    fun createBoard(input: String): GameBoard<Int?> {
        return createBoard(TestBoard(input))
    }

    fun createBoard(input: TestBoard): GameBoard<Int?> {
        val board = createGameBoard<Int?>(width)
        for (cell in board.getAllCells()) {
            val ch = input.values[cell.i - 1][cell.j - 1]
            if (ch != 0) {
                board[cell] = ch
            }
        }
        return board
    }

    fun GameBoard<Int?>.toTestBoard() = TestBoard(valuesToString { i, j ->
        this[getCell(i + 1, j + 1)]
    })
}

private fun valuesToString(getElement: (Int, Int) -> Int?) = buildString {
    for (i in 0..3) {
        for (j in 0..3) {
            append(getElement(i, j) ?: '-')
        }
        append(' ')
    }
}.trim()

data class TestBoard(val board: String) {
    val values: List<List<Int?>> by lazy {
        board.trim()
                .split(' ')
                .map { row -> row.map { ch -> if (ch == '-') null else ch - '0' } }
    }

    fun reversed(): TestBoard = TestBoard(board.reversed())

    fun mirror(): TestBoard {
        return TestBoard(valuesToString { i, j -> values[j][i] })
    }

    override fun toString() =
            board.split(' ')
                    .joinToString("\n") { it.toList().joinToString(" ") }
}