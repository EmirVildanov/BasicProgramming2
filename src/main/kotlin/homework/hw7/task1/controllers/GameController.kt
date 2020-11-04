package homework.hw7.task1.controllers

import homework.hw7.task1.enums.CellType
import homework.hw7.task1.models.GameWithBotModel
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.Controller
import tornadofx.booleanBinding
import tornadofx.stringBinding

class GameController(private val gameModel: GameWithBotModel) : Controller() {
    val buttonsData = gameModel.field.map { ButtonData(it) }

    inner class ButtonData(playerId: SimpleIntegerProperty) {
        val text = stringBinding(playerId) {
            getPlayerFigure(value).cellString
        }
        val isDisabled = booleanBinding(playerId, gameModel.waitingProperty) {
            playerId.value != -1 || gameModel.waitingProperty.value == true
        }
    }

    fun getPlayerFigure(playerId: Int): CellType {
        return when (playerId) {
            -1 -> CellType.NOTHING
            0 -> CellType.CROSS
            1 -> CellType.CIRCLE
            else -> CellType.NOTHING
        }
    }

    fun makeTurn(position: Int) {
        gameModel.makeTurn(position)
    }

    fun startGame() {
        gameModel.onGameStart()
    }

    private fun onGameEnd(listener: () -> Unit) {
        gameModel.gameOverProperty.addListener { _, _, newValue ->
            if (newValue == true) {
                listener()
            }
        }
    }

    fun onVictory(listener: (Int) -> Unit) {
        onGameEnd {
            if (gameModel.winner != -1) {
                listener(gameModel.winner)
            }
        }
    }

    fun onDraw(listener: () -> Unit) {
        onGameEnd {
            if (gameModel.winner == -1) {
                listener()
            }
        }
    }

    fun onError(listener: (String) -> Unit) {
        gameModel.errorMessageProperty.addListener { _, _, newValue ->
            if (newValue != null) {
                listener(newValue)
            }
        }
    }
}
