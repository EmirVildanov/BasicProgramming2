package homework.hw8.task1.readyProject.models

import homework.hw7.task1.GameApp.Companion.FIELD_SIZE
import homework.hw8.task1.readyProject.GameClient
import io.ktor.util.KtorExperimentalAPI
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import java.net.ConnectException
import tornadofx.getValue
import tornadofx.setValue

@KtorExperimentalAPI
class RemoteGameModel {
    private val client = GameClient()

    private var thisPlayerId: Int = -1

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

    val connectedProperty = SimpleBooleanProperty(false)
    var connected by connectedProperty

    val gameStartedProperty = SimpleBooleanProperty(false)
    var gameStarted by gameStartedProperty

    init {
        client.onConnect = {
            connected = true
        }
        client.onGameStart = {
            thisPlayerId = it
            onGameStart()
        }
        client.onTurnMade = { playerId, position ->
            if (playerId != thisPlayerId) {
                onTurnMade(playerId, position)
            }
        }
        client.onVictory = {
            onVictory(it)
        }
        client.onTie = {
            onTie()
        }
        client.onConnectionError = {
            onError(ConnectionLostException())
        }
        client.onDisconnect = {
            if (!gameOver) {
                onError(ConnectionLostException())
            }
        }
    }

    class ConnectionLostException : ConnectException()

    fun connect() {
        client.start()
    }

    fun disconnect() {
        client.close()
    }

    fun onGameStart() {
        if (gameStarted) {
            return
        }
        gameStarted = true
        onTurnStart(0)
    }

    fun makeTurn(position: Int) {
        makeTurn(activePlayer, position)
    }

    fun onTurnStart(playerId: Int) {
        activePlayer = playerId
        waiting = activePlayer != thisPlayerId
    }

    fun onTurnMade(playerId: Int, position: Int) {
        field[position].set(playerId)
        waiting = true
        onTurnStart(1 - playerId)
    }

    fun onVictory(playerId: Int) {
        winner = playerId
        gameOver = true
    }

    fun onTie() {
        winner = -1
        gameOver = true
    }

    fun onError(exception: Exception) {
        errorMessage = "Error connecting"
    }

    fun makeTurn(playerId: Int, position: Int) {
        client.makeTurn(position)
        onTurnMade(playerId, position)
    }

    class IllegalTurnPositionException(message: String) : IllegalArgumentException(message)

    class PlayerCannotMakeTurnException(message: String) : IllegalArgumentException(message)
}
