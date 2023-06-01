package games.ui

import games.game2048.newGame2048
import java.awt.Color

object Game2048Settings : GameSettings("Game 2048", Color(0xbbada0)) {
    private val emptyColor = Color(0xcdc1b4)
    private val colors: Map<Int, Color> = run {
        val colors = listOf(
                0xeee4da, 0xede0c8, 0xf2b179, 0xf59563, 0xf67c5f, 0xf65e3b,
                0xedcf72, 0xedcc61, 0xedc850, 0xedc53f, 0xedc22e)

        val values: List<Int> = (1..11).map { Math.pow(2.0, it.toDouble()).toInt() }
        values.zip(colors.map { Color(it) }).toMap()
    }

    override fun getBackgroundColor(value: Int) = colors[value] ?: emptyColor
    override fun getForegroundColor(value: Int) = if (value < 16) Color(0x776e65) else Color(0xf9f6f2)
}


fun main() {
    playGame(newGame2048(), Game2048Settings)
}