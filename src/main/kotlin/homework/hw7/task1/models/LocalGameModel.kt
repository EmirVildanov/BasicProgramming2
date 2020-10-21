package homework.hw7.task1.models

import homework.hw7.task1.GameApp.Companion.FIELD_SIZE
import homework.hw7.task1.logic.LocalGameLoop
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue
import java.lang.Exception

open class LocalGameModel : LocalGameLoop(), GameModel {
    override val field = List(FIELD_SIZE) { SimpleIntegerProperty(-1) }

    final override val activePlayerProperty = SimpleIntegerProperty(-1)
    override var activePlayer by activePlayerProperty

    final override val winnerProperty = SimpleIntegerProperty(-1)
    override var winner by winnerProperty

    final override val gameOverProperty = SimpleBooleanProperty(false)
    override var gameOver by gameOverProperty

    final override val waitingProperty = SimpleBooleanProperty(true)
    override var waiting by waitingProperty

    final override val errorMessageProperty = SimpleStringProperty(null)
    override var errorMessage by errorMessageProperty

    override fun onGameStart() {
        super.onGameStart()
        waiting = false
    }

    override fun onTurnStart(playerId: Int) {
        super.onTurnStart(playerId)
        activePlayer = playerId
    }

    override fun makeTurn(position: Int) {
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
