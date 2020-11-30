package homework.hw7.task1.controllers

import homework.hw8.task1.readyProject.enums.CellType
import homework.hw8.task1.readyProject.models.RemoteGameModel
import io.ktor.util.KtorExperimentalAPI
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.Controller
import tornadofx.booleanBinding
import tornadofx.stringBinding

class GameController @KtorExperimentalAPI constructor(private val gameModel: RemoteGameModel) : Controller() {
    @KtorExperimentalAPI
    val buttonsData = gameModel.field.map { ButtonData(it) }

    inner class ButtonData(playerId: SimpleIntegerProperty) {
        val text = stringBinding(playerId) {
            getPlayerFigure(value).cellString
        }
        @KtorExperimentalAPI
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

    @KtorExperimentalAPI
    fun makeTurn(position: Int) {
        gameModel.makeTurn(position)
    }

    @KtorExperimentalAPI
    fun startGame() {
        gameModel.onGameStart()
    }

    @KtorExperimentalAPI
    private fun onGameEnd(listener: () -> Unit) {
        gameModel.gameOverProperty.addListener { _, _, newValue ->
            if (newValue == true) {
                listener()
            }
        }
    }

    @KtorExperimentalAPI
    fun onVictory(listener: (Int) -> Unit) {
        onGameEnd {
            if (gameModel.winner != -1) {
                listener(gameModel.winner)
            }
        }
    }

    @KtorExperimentalAPI
    fun onDraw(listener: () -> Unit) {
        onGameEnd {
            if (gameModel.winner == -1) {
                listener()
            }
        }
    }

    @KtorExperimentalAPI
    fun onError(listener: (String) -> Unit) {
        gameModel.errorMessageProperty.addListener { _, _, newValue ->
            if (newValue != null) {
                listener(newValue)
            }
        }
    }
}
