package homework.hw8.task1.data.models

import homework.hw7.task1.GameApp.Companion.FIELD_SIZE
import homework.hw8.task1.data.GameClient
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import java.net.ConnectException
import tornadofx.getValue
import tornadofx.setValue

class RemoteGameModel : GameLoop {
    private val client = GameClient()

    private var thisPlayerId: Int = -1

    private val field = List(FIELD_SIZE) { SimpleIntegerProperty(-1) }

    private val activePlayerProperty = SimpleIntegerProperty(-1)
    var activePlayer by activePlayerProperty

    private val winnerProperty = SimpleIntegerProperty(-1)
    var winner by winnerProperty

    private val gameOverProperty = SimpleBooleanProperty(false)
    var gameOver by gameOverProperty

    private val waitingProperty = SimpleBooleanProperty(true)
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
            onDraw()
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

    override fun onGameStart() {
        if (gameStarted) {
            return
        }
        gameStarted = true
        onTurnStart(0)
    }

    fun makeTurn(position: Int) {
        makeTurn(activePlayer, position)
    }

    override fun onTurnStart(playerId: Int) {
        activePlayer = playerId
        waiting = activePlayer != thisPlayerId
    }

    override fun onTurnMade(playerId: Int, position: Int) {
        field[position].set(playerId)
        waiting = true
        onTurnStart(1 - playerId)
    }

    override fun onVictory(playerId: Int) {
        winner = playerId
        gameOver = true
    }

    override fun onDraw() {
        winner = -1
        gameOver = true
    }

    override fun onError(exception: Exception) {
        errorMessage = "Error connecting"
    }

    override fun makeTurn(playerId: Int, position: Int) {
        client.makeTurn(position)
        onTurnMade(playerId, position)
    }
}
