// drawing based on https://github.com/bulenkov/2048
package games.ui

import board.Direction
import games.game.Game
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

class PlayGame(val game: Game, val settings: GameSettings) : JPanel() {
    init {
        isFocusable = true
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (game.hasWon() == false && game.canMove()) {
                    val direction = when (e.keyCode) {
                        KeyEvent.VK_LEFT -> Direction.LEFT
                        KeyEvent.VK_RIGHT -> Direction.RIGHT
                        KeyEvent.VK_DOWN -> Direction.DOWN
                        KeyEvent.VK_UP -> Direction.UP
                        else -> null
                    }
                    if (direction != null) {
                        game.processMove(direction)
                    }
                }
                repaint()
            }
        })
        game.initialize()
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        g.color = settings.backgroundColor
        g.fillRect(0, 0, this.size.width, this.size.height)
        for (y in 1..4) {
            for (x in 1..4) {
                drawTile(g as Graphics2D, game[y, x] ?: 0, x - 1, y - 1)
            }
        }
    }

    private fun offsetCoors(arg: Int): Int {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN
    }

    private fun drawTile(g: Graphics2D, value: Int, x: Int, y: Int) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)

        val xOffset = offsetCoors(x)
        val yOffset = offsetCoors(y)
        g.color = settings.getBackgroundColor(value)
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14)
        g.color = settings.getForegroundColor(value)
        val size = if (value < 100) 36 else if (value < 1000) 32 else 24
        val font = Font(FONT_NAME, Font.BOLD, size)
        g.font = font

        val s = value.toString()
        val fm = getFontMetrics(font)

        val w = fm.stringWidth(s)
        val h = -fm.getLineMetrics(s, g).baselineOffsets[2].toInt()

        if (value != 0)
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2)

        if (game.hasWon() || game.canMove() == false) {
            g.color = Color(255, 255, 255, 30)
            g.fillRect(0, 0, width, height)
            g.color = Color(78, 139, 202)
            g.font = Font(FONT_NAME, Font.BOLD, 48)
            if (game.hasWon()) {
                g.drawString("You won!", 68, 150)
            }
            if (!game.canMove()) {
                g.drawString("Game over!", 45, 160)
            }
        }
        g.font = Font(FONT_NAME, Font.PLAIN, 18)
    }
}

private val FONT_NAME = "Arial"
private val TILE_SIZE = 64
private val TILES_MARGIN = 16

abstract class GameSettings(val name: String, val backgroundColor: Color) {
    abstract fun getBackgroundColor(value: Int): Color
    abstract fun getForegroundColor(value: Int): Color
}

fun playGame(game: Game, settings: GameSettings) {
    with(JFrame()) {
        title = settings.name
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setSize(340, 400)
        isResizable = false

        add(PlayGame(game, settings))

        setLocationRelativeTo(null)
        isVisible = true
    }
}