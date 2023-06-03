package board

import board.Direction.*
import java.lang.IllegalArgumentException

class MySquareBoard(override val width: Int): SquareBoard {
    private val cells: List<List<Cell>> =
        List(width) { row ->
            List(width) { col ->
                Cell(row + 1, col + 1)}
        }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        if (i in 1..width && j in 1..width) cells[i-1][j-1] else null

    override fun getCell(i: Int, j: Int): Cell {
        if (i !in 1..width)
            throw IllegalArgumentException("row $i is greater than width $width")
        if (j !in 1..width)
            throw IllegalArgumentException("col $j is greater than width $width")
        return cells[i-1][j-1]
    }

    override fun getAllCells(): Collection<Cell> = cells.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if (i !in 1..width)
            throw IllegalArgumentException("row $i is greater than width $width")
        val row = cells[i - 1]
        return jRange
            .filter { j -> j in 1..width }
            .map { j -> row[j - 1] }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if (j !in 1..width)
            throw IllegalArgumentException("row $j is greater than width $width")
        val col = cells.map { row -> row[j - 1] }
        return iRange
            .filter { i -> i in 1..width }
            .map { i -> col[i - 1] }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            UP -> if (i <= 1) null else cells[i - 2][j - 1]
            DOWN -> if (i >= width) null else cells[i][j - 1]
            RIGHT -> if (j >= width) null else cells[i - 1][j]
            LEFT -> if (j <= 1) null else cells[i - 1][j - 2]
        }
}

fun createSquareBoard(width: Int): SquareBoard = MySquareBoard(width) // TODO()

class MyGameBoard<T>(private val squareBoard: SquareBoard):
    GameBoard<T>, SquareBoard by squareBoard {
    private val boardValues: HashMap<Cell, T?> =
        squareBoard
            .getAllCells()
            .associateWithTo(HashMap()) { _ -> null }

    override operator fun get(cell: Cell): T? = boardValues[cell]

    override operator fun set(cell: Cell, value: T?) {
        boardValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        boardValues
            .filter { (_, value) -> predicate(value) }
            .map { (cell, _) -> cell }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        val found = filter(predicate)
        return if (found.isEmpty()) null else found.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean =
        filter(predicate).isNotEmpty()

    override fun all(predicate: (T?) -> Boolean): Boolean =
        !any { t -> !predicate(t) }
}

fun <T> createGameBoard(width: Int): GameBoard<T> = // TODO()
    MyGameBoard(MySquareBoard(width))
