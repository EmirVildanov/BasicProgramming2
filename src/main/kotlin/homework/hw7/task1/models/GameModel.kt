package homework.hw7.task1.models

import homework.hw7.task1.GameApp.Companion.FIELD_SIZE
import homework.hw7.task1.logic.GameLoop
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue
import java.lang.Exception

open class GameModel : GameLoop() {
    val field = List(FIELD_SIZE) { SimpleIntegerProperty(-1) }

    val activePlayerProperty = SimpleIntegerProperty(-1)
    var activePlayer by activePlayerProperty

    val winnerProperty = SimpleIntegerProperty(-1)
    var winner by winnerProperty

    val gameOverProperty = SimpleBooleanProperty(false)
    var gameOver by gameOverProperty

    val waitingProperty = SimpleBooleanProperty(true)
    var waiting by waitingProperty

    val errorMessageProperty = SimpleStringProperty(null)
    var errorMessage by errorMessageProperty

    override fun onGameStart() {
        super.onGameStart()
        waiting = false
    }

    override fun onTurnStart(playerId: Int) {
        super.onTurnStart(playerId)
        activePlayer = playerId
    }

    fun makeTurn(position: Int) {
        super.makeTurn(activePlayer, position)
    }

    override fun onTurnMade(playerId: Int, position: Int) {
        super.onTurnMade(playerId, position)
        field[position].set(playerId)
    }

    override fun onVictory(playerId: Int) {
        super.onVictory(playerId)
        winner = playerId
        gameOver = true
    }

    override fun onDraw() {
        super.onDraw()
        winner = -1
        gameOver = true
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        errorMessage = exception.message
    }
}
