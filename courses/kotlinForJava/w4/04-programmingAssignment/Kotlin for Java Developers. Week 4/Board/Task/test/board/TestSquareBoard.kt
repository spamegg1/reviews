package board

import board.Direction.*
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestSquareBoard {
    @Test
    fun test00AllCells() {
        val board = createSquareBoard(2)
        val cells = board.getAllCells().sortedWith(compareBy<Cell> { it.i }.thenBy { it.j })
        Assert.assertEquals("[(1, 1), (1, 2), (2, 1), (2, 2)]", cells.toString())
    }

    @Test
    fun test01Cell() {
        val board = createSquareBoard(2)
        val cell = board.getCellOrNull(1, 2)
        Assert.assertEquals(1, cell?.i)
        Assert.assertEquals(2, cell?.j)
    }

    @Test
    fun test02NoCell() {
        val board = createSquareBoard(2)
        val cell = board.getCellOrNull(3, 3)
        Assert.assertEquals(null, cell)
    }

    @Test
    fun test03Row() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..2)
        Assert.assertEquals("[(1, 1), (1, 2)]", row.toString())
    }

    @Test
    fun test04RowReversed() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 2 downTo 1)
        Assert.assertEquals("[(1, 2), (1, 1)]", row.toString())
    }

    @Test
    fun test05RowWrongRange() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..10)
        Assert.assertEquals("[(1, 1), (1, 2)]", row.toString())
    }

    @Test
    fun test06Neighbour() {
        val board = createSquareBoard(2)
        with(board) {
            val cell = getCellOrNull(1, 1)
            Assert.assertNotNull(cell)
            Assert.assertEquals(null, cell!!.getNeighbour(Direction.UP))
            Assert.assertEquals(null, cell.getNeighbour(Direction.LEFT))
            Assert.assertEquals("(2, 1)", cell.getNeighbour(Direction.DOWN).toString())
            Assert.assertEquals("(1, 2)", cell.getNeighbour(Direction.RIGHT).toString())
        }
    }

    @Test
    fun test07AllCells() {
        val board = createSquareBoard(3)
        val cells = board.getAllCells().sortedWith(compareBy<Cell> { it.i }.thenBy { it.j })
        Assert.assertEquals("Wrong result for 'getAllCells()' for the board of width 3.",
                "[(1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3), (3, 1), (3, 2), (3, 3)]",
                cells.toString())
    }

    @Test
    fun test08Cell() {
        val board = createSquareBoard(4)
        val cell = board.getCellOrNull(2, 3)
        Assert.assertEquals("The board of width 4 should contain the cell (2, 3).",
                "(2, 3)", cell.toString())
    }

    @Test
    fun test09NoCell() {
        val board = createSquareBoard(4)
        val cell = board.getCellOrNull(10, 10)
        Assert.assertEquals("The board of width 4 should contain the cell (10, 10).", null, cell)
    }

    @Test
    fun test10Row() {
        val row = createSquareBoard(4).getRow(1, 1..2)
        Assert.assertEquals("Wrong row for 'createSquareBoard(4).getRow(1, 1..2)'.",
                "[(1, 1), (1, 2)]", row.toString())
    }

    @Test
    fun test11Column() {
        val row = createSquareBoard(4).getColumn(1..2, 3)
        Assert.assertEquals("Wrong column for 'createSquareBoard(4).getColumn(1..2, 3)'.",
                "[(1, 3), (2, 3)]", row.toString())
    }

    @Test
    fun test12RowReversedRange() {
        val row = createSquareBoard(4).getRow(1, 4 downTo 1)
        Assert.assertEquals("Wrong column for 'createSquareBoard(4).getRow(1, 4 downTo 1)'.",
                "[(1, 4), (1, 3), (1, 2), (1, 1)]", row.toString())
    }

    @Test
    fun test13ColumnReversedRange() {
        val row = createSquareBoard(4).getColumn(2 downTo 1, 3)
        Assert.assertEquals("Wrong column for 'createSquareBoard(4).getColumn(2 downTo 1, 3)'.",
                "[(2, 3), (1, 3)]", row.toString())
    }

    @Test
    fun test14ColumnWrongRange() {
        val row = createSquareBoard(4).getColumn(3..6, 2)
        Assert.assertEquals("Wrong column for 'createSquareBoard(4).getColumn(3..6, 2)'.",
                "[(3, 2), (4, 2)]", row.toString())
    }

    private fun neighbourMessage(cell: Cell, direction: Direction) =
            "Wrong neighbour for the cell $cell in a direction $direction."

    @Test
    fun test15Neighbour() {
        with(createSquareBoard(4)) {
            val cell = getCellOrNull(2, 3)
            Assert.assertNotNull("The board of width 4 should contain the cell (2, 3).", cell)
            Assert.assertEquals(neighbourMessage(cell!!, UP), "(1, 3)", cell.getNeighbour(UP).toString())
            Assert.assertEquals(neighbourMessage(cell, DOWN), "(3, 3)", cell.getNeighbour(DOWN).toString())
            Assert.assertEquals(neighbourMessage(cell, LEFT), "(2, 2)", cell.getNeighbour(LEFT).toString())
            Assert.assertEquals(neighbourMessage(cell, RIGHT), "(2, 4)", cell.getNeighbour(RIGHT).toString())
        }
    }

    @Test
    fun test16NullableNeighbour() {
        with(createSquareBoard(4)) {
            val cell = getCellOrNull(4, 4)
            Assert.assertNotNull("The board of width 4 should contain the cell (4, 4).", cell)
            Assert.assertEquals(neighbourMessage(cell!!, UP), "(3, 4)", cell.getNeighbour(UP).toString())
            Assert.assertEquals(neighbourMessage(cell, LEFT), "(4, 3)", cell.getNeighbour(LEFT).toString())
            Assert.assertEquals(neighbourMessage(cell, DOWN), null, cell.getNeighbour(DOWN))
            Assert.assertEquals(neighbourMessage(cell, RIGHT), null, cell.getNeighbour(RIGHT))
        }
    }

    @Test
    fun test17TheSameCell() {
        val board = createSquareBoard(4)
        val first = board.getCell(1, 2)
        val second = board.getCellOrNull(1, 2)
        Assert.assertTrue("'getCell' and 'getCellOrNull' should return the same 'Cell' instances.\n" +
                "Create only 'width * width' cells; all the functions working with cells " +
                "should return existing cells instead of creating new ones.",
                first === second)
    }

    @Test
    fun test18TheSameCell() {
        val board = createSquareBoard(1)
        val first = board.getAllCells().first()
        val second = board.getCell(1, 1)
        Assert.assertTrue("'getAllCells' and 'getCell' should return the same 'Cell' instances.\n" +
                "Create only 'width * width' cells; all the functions working with cells " +
                "should return existing cells instead of creating new ones.",
                first === second)
    }

    @Test
    fun test19TheSameCell() {
        val board = createSquareBoard(4)
        val cell = board.getCell(1, 1)
        val first = board.run { cell.getNeighbour(RIGHT) }
        val second = board.getCell(1, 2)
        Assert.assertTrue("'getNeighbour' shouldn't recreate the 'Cell' instance.\n" +
                "Create only 'width * width' cells; all the functions working with cells " +
                "should return existing cells instead of creating new ones.",
                first === second)
    }

    @Test
    fun test20TheSameCell() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..1)
        val first = row[0]
        val second = board.getCell(1, 1)
        Assert.assertTrue("'getRow' shouldn't recreate the 'Cell' instances.\n" +
                "Create only 'width * width' cells; all the functions working with cells " +
                "should return existing cells instead of creating new ones.",
                first === second)
    }

    @Test
    fun test21TheSameCell() {
        val board = createSquareBoard(2)
        val column = board.getColumn(1..1, 2)
        val first = column[0]
        val second = board.getCell(1, 2)
        Assert.assertTrue("'getColumn' shouldn't recreate the 'Cell' instances.\n" +
                "Create only 'width * width' cells; all the functions working with cells " +
                "should return existing cells instead of creating new ones.",
                first === second)
    }
}
