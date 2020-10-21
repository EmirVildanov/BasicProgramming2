package homework.hw7.task1.bot

import javafx.beans.property.SimpleIntegerProperty

open class EasyBot(
    private val gameField: List<SimpleIntegerProperty>,
    open val playerId: Int,
    open val botId: Int
) : Bot {
    override fun makeTurn(): SimpleIntegerProperty {
        return pickCell() ?: throw IllegalStateException("The bot cannot pick any of the cells")
    }

    open fun pickCell(): SimpleIntegerProperty? {
        return randomlyPickCell()
    }

    fun randomlyPickCell(): SimpleIntegerProperty? {
        val freeCells = gameField.filter { it.value == -1 }
        return if (freeCells.isEmpty()) null else freeCells.random()
    }
}
