package homework.hw7.task1.bot

import homework.hw7.task1.GameApp.Companion.CELLS_NUMBER
import homework.hw7.task1.GameApp.Companion.FIELD_SIZE
import homework.hw7.task1.logic.FieldManager
import javafx.beans.property.SimpleIntegerProperty

class HardBot(
    private val gameField: List<SimpleIntegerProperty>,
    override val playerId: Int,
    override val botId: Int
) : EasyBot(gameField, playerId, botId) {

    private val idValuesField: List<Int>
        get() = gameField.map { it.value }

    override fun makeTurn(): SimpleIntegerProperty {
        return pickCell() ?: throw IllegalStateException("The bot cannot pick any of the cells")
    }

    override fun pickCell(): SimpleIntegerProperty? {
        val fieldManager = FieldManager(idValuesField, CELLS_NUMBER)
        val options = listOfNotNull(
            findVictoriousCellForBot(fieldManager),
            findVictoriousCellForPlayer(fieldManager),
            pickMiddle(),
            randomlyPickCell()
        )
        return if (options.isEmpty()) null else options.first()
    }

    private fun getCellForBotByIndex(cellIndex: Int?): SimpleIntegerProperty? {
        return if (cellIndex == null) null else gameField[cellIndex]
    }

    private fun findVictoriousCellForBot(fieldManager: FieldManager): SimpleIntegerProperty? {
        val almostCapturedLineByBot = fieldManager.getAlmostCapturedLineBy(botId)
        return getCellForBotByIndex(almostCapturedLineByBot?.firstFreeCell?.index)
    }

    private fun findVictoriousCellForPlayer(fieldManager: FieldManager): SimpleIntegerProperty? {
        val almostCapturedLineByPlayer = fieldManager.getAlmostCapturedLineBy(playerId)
        return getCellForBotByIndex(almostCapturedLineByPlayer?.firstFreeCell?.index)
    }

    private fun pickMiddle(): SimpleIntegerProperty? {
        val middleIndex = FIELD_SIZE / 2
        return if (gameField[middleIndex].value == -1) {
            gameField[middleIndex]
        } else {
            null
        }
    }
}
