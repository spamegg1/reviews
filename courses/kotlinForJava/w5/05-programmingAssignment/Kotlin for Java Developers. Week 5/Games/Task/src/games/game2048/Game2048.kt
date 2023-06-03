package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
    Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) { // TODO()
    val attempt = initializer.nextValue(this)
    if (attempt != null) {
        val (cell, value) = attempt // smart cast
        this[cell] = value
    }
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    // TODO()
    val values = rowOrColumn.map { cell -> this[cell] }
    val merge: (Int) -> Int = { it * 2 }
    val mergedValues = values.moveAndMergeEqual(merge)
    if (values.size == mergedValues.size || mergedValues.isEmpty()) return false

    var index = 0
    var mergedIndex = 0
    while (index < rowOrColumn.size) {
        if (index < mergedValues.size) {
            this[rowOrColumn[index]] = mergedValues[mergedIndex]
            mergedIndex++
        } else {
            this[rowOrColumn[index]] = null
        }
        index++
    }
    return true
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean { // TODO()
    return when (direction) {
        Direction.RIGHT -> {
            val rows = (1..width).map { i -> getRow(i, width downTo 1) }
            rows.map { row -> moveValuesInRowOrColumn(row) }.any { it }
        }
        Direction.LEFT -> {
            val rows = (1..width).map { i -> getRow(i, 1..width) }
            rows.map { row -> moveValuesInRowOrColumn(row) }.any { it }
        }
        Direction.DOWN -> {
            val cols = (1..width).map { j -> getColumn(width downTo 1, j) }
            cols.map { col -> moveValuesInRowOrColumn(col) }.any { it }
        }
        Direction.UP -> {
            val cols = (1..width).map { j -> getColumn(1..width, j) }
            cols.map { col -> moveValuesInRowOrColumn(col) }.any { it }
        }
    }
}
