package games.gameOfFifteen

import board.Direction
import board.Cell
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    // TODO()
    MyGameOfFifteen(initializer)

class MyGameOfFifteen(private val initializer: GameOfFifteenInitializer): Game {
    private val myBoard = createGameBoard<Int>(4)
    override fun initialize() {
        val perm = initializer.initialPermutation
        for (i in 1..4) {
            for (j in 1..4) {
                val cell = myBoard.getCell(i, j)
                if (i != 4 || j != 4) {
                    myBoard[cell] = perm[4 * (i - 1) + j - 1]
                }
            }
        }
    }
    override fun canMove(): Boolean = true
    override fun hasWon(): Boolean {
        val state = myBoard.getAllCells().map { cell -> myBoard[cell] }
        val winning = (1..15).toList() + listOf(null)
        return state == winning
    }

    private fun validateDirection(freeCell: Cell, direction: Direction): Boolean =
        when (direction) {
            Direction.DOWN -> freeCell.i >= 2
            Direction.UP -> freeCell.i <= 3
            Direction.LEFT -> freeCell.j <= 3
            Direction.RIGHT -> freeCell.j >= 2
        }

    private fun cellToBeMoved(freeCell: Cell, direction: Direction): Cell =
        when (direction) {
            Direction.DOWN -> myBoard.getCell(freeCell.i - 1, freeCell.j)
            Direction.UP -> myBoard.getCell(freeCell.i + 1, freeCell.j)
            Direction.LEFT -> myBoard.getCell(freeCell.i, freeCell.j + 1)
            Direction.RIGHT -> myBoard.getCell(freeCell.i, freeCell.j - 1)
        }

    private fun swap(freeCell: Cell, toBeMoved: Cell) {
        myBoard[freeCell] = myBoard[toBeMoved]
        myBoard[toBeMoved] = null
    }

    override fun processMove(direction: Direction) {
        val freeCell = myBoard.filter { it == null }.first()
        if (validateDirection(freeCell, direction)) {
            swap(freeCell, cellToBeMoved(freeCell, direction))
        }
    }
    override operator fun get(i: Int, j: Int): Int? =
        myBoard[myBoard.getCell(i, j)]
}
