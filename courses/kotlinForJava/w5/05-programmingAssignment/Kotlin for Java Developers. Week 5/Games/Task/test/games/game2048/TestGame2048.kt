package games.game2048

import board.Cell
import board.Direction
import board.Direction.*
import board.GameBoard
import games.game.Game
import org.junit.Assert
import org.junit.Test

class TestGame2048 {
    private fun Game.asString() =
            (1..4).joinToString("\n") { i ->
                (1..4).joinToString(" ") { j ->
                    "${get(i, j) ?: "-"}"
                }
            }

    class TestGame2048Initializer(moves: List<Move>) : Game2048Initializer<Int> {
        val iterator = moves.iterator()
        override fun nextValue(board: GameBoard<Int?>): Pair<Cell, Int> {
            val move = iterator.next()
            return board.getCell(move.position.first, move.position.second) to move.value
        }
    }

    private fun testGame(moves: List<Move>) {
        val game = newGame2048(TestGame2048Initializer(moves))
        game.initialize()
        run {
            // checking the state after initialization
            val first = moves[0]
            val second = moves[1]
            Assert.assertEquals("Wrong result after board initialization " +
                    "by '${first.value}' at ${first.cell} and " +
                    "'${second.value}' at ${second.cell}",
                    second.board, game.asString())
        }

        for ((index, move) in moves.withIndex()) {
            if (move.direction == null) continue
            // checking the state after each move
            game.processMove(move.direction)
            val prev = moves[index - 1].board
            Assert.assertEquals("Wrong result after moving ${move.direction} " +
                    "and then adding '${move.value}' to ${move.cell} " +
                    "for\n$prev\n",
                    move.board, game.asString())
        }
    }

    data class Move(
            val position: Pair<Int, Int>,
            val value: Int,
            val direction: Direction?,
            val initialBoard: String
    ) {
        val cell: String
            get() = "Cell(${position.first}, ${position.second})"

        val board: String = initialBoard.trimMargin()
    }

    @Test
    fun testMoves() {
        testGame(listOf(
                Move(Pair(1, 1), 2, null, """
            |2 - - -
            |- - - -
            |- - - -
            |- - - -"""),
                Move(Pair(1, 4), 2, null, """
            |2 - - 2
            |- - - -
            |- - - -
            |- - - -"""),
                Move(Pair(3, 2), 4, RIGHT, """
            |- - - 4
            |- - - -
            |- 4 - -
            |- - - -"""),
                Move(Pair(4, 2), 2, UP, """
            |- 4 - 4
            |- - - -
            |- - - -
            |- 2 - -"""),
                Move(Pair(2, 2), 2, LEFT, """
            |8 - - -
            |- 2 - -
            |- - - -
            |2 - - -"""),
                Move(Pair(4, 4), 2, DOWN, """
            |- - - -
            |- - - -
            |8 - - -
            |2 2 - 2"""),
                Move(Pair(3, 3), 2, RIGHT, """
            |- - - -
            |- - - -
            |- - 2 8
            |- - 2 4"""),
                Move(Pair(1, 2), 4, DOWN, """
            |- 4 - -
            |- - - -
            |- - - 8
            |- - 4 4"""),
                Move(Pair(3, 1), 2, RIGHT, """
            |- - - 4
            |- - - -
            |2 - - 8
            |- - - 8"""),
                Move(Pair(3, 3), 2, DOWN, """
            |- - - -
            |- - - -
            |- - 2 4
            |2 - - 16"""),
                Move(Pair(2, 3), 2, DOWN, """
            |- - - -
            |- - 2 -
            |- - - 4
            |2 - 2 16"""),
                Move(Pair(1, 4), 2, RIGHT, """
            |- - - 2
            |- - - 2
            |- - - 4
            |- - 4 16"""),
                Move(Pair(3, 2), 2, LEFT, """
            |2 - - -
            |2 - - -
            |4 2 - -
            |4 16 - -"""),
                Move(Pair(1, 3), 2, DOWN, """
            |- - 2 -
            |- - - -
            |4 2 - -
            |8 16 - -""")
        ))
    }
}