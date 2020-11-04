package homework.hw8.task1.data.server

import homework.hw7.task1.GameApp
import homework.hw7.task1.logic.FieldManager
import homework.hw7.task1.messages.GameEndedMessage
import homework.hw7.task1.messages.GameStartedMessage
import homework.hw7.task1.messages.TurnServerMessage

class ServerGameLoop(private val playersManager: GameServer.PlayersManager) {
    private val field = MutableList(GameApp.FIELD_SIZE) { -1 }
    private var activePlayer = -1
    private var gameEnded = false
    private var winner = -1
    var gameOn = false

    fun onGameStart() {
        println("Game started")
        field.replaceAll { -1 }
        gameEnded = false
        activePlayer = -1
        onTurnStart(0)
        gameOn = true
        playersManager.notifyEach { GameStartedMessage.compose(it) }
    }

    private fun onTurnStart(playerId: Int) {
        println("Turn for player #$playerId starts")
        activePlayer = playerId
    }

    fun onTurnMade(playerId: Int, position: Int) {
        println("Turn made by #$playerId to position $position")
        field[position] = playerId
        playersManager.notifyAll(TurnServerMessage.compose(playerId, position))
    }

    fun onPlayerDisconnected(playerId: Int) {
        println("Ending the game: player #$playerId disconnected")
        onVictory(1 - playerId)
    }

    private fun onVictory(playerId: Int) {
        println("Player #$playerId won")
        winner = playerId
        gameEnded = true
        gameOn = false
        playersManager.notifyAll(GameEndedMessage.compose(playerId))
        playersManager.kickAll()
    }

    private fun onDraw() {
        println("Draw!")
        winner = -1
        gameEnded = true
        gameOn = false
        playersManager.notifyAll(GameEndedMessage.compose(-1))
        playersManager.kickAll()
    }
    private fun checkIfGameEnded() {
        val fieldManager = FieldManager(field, GameApp.CELLS_NUMBER)
        val victoriousPlayer = fieldManager.getWinner()
        if (victoriousPlayer != null) {
            onVictory(victoriousPlayer)
        } else {
            if (field.none { it == -1 }) {
                onDraw()
            }
        }
    }

    private fun getNextPlayer(): Int {
        return 1 - activePlayer
    }

    fun onError(exception: Exception) {
        println("Error occurred in the LocalGameLoop $exception")
    }

    fun makeTurn(playerId: Int, position: Int) {
        if (activePlayer != playerId) {
            throw GameLoop.PlayerCannotMakeTurnException("layerCannotMakeTurn")
        }
        if (field[position] != -1) {
            throw GameLoop.IllegalTurnPositionException("IllegalTurnPosition")
        }
        onTurnMade(playerId, position)
        checkIfGameEnded()
        if (!gameEnded) {
            onTurnStart(getNextPlayer())
        }
    }
}
