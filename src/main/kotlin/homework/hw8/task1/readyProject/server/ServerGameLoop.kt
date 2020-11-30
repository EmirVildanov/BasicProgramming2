package homework.hw8.task1.readyProject.server

import homework.hw7.task1.GameApp
import homework.hw8.task1.readyProject.logic.FieldManager
import homework.hw7.task1.messages.GameEndedMessage
import homework.hw7.task1.messages.GameStartedMessage
import homework.hw7.task1.messages.TurnServerMessage
import java.lang.Exception
import java.lang.IllegalArgumentException

class ServerGameLoop(private val playersManager: GameServer.PlayersManager) {
    private val field = MutableList(GameApp.FIELD_SIZE) { -1 }
    private var activePlayer = -1
    private var gameEnded = false
    private var winner = -1
    var gameOn = false

    fun makeTurn(playerId: Int, position: Int) {
        if (activePlayer != playerId) {
            throw PlayerCannotMakeTurn()
        }
        if (field[position] != -1) {
            throw IllegalTurnPosition()
        }
        onTurnMade(playerId, position)
        checkIfGameEnded()
        if (!gameEnded) {
            onTurnStart(getNextPlayer())
        }
    }

    fun onGameStart() {
        println("Game started")
        field.replaceAll { -1 }
        gameEnded = false
        activePlayer = -1
        onTurnStart(0)
        gameOn = true
        playersManager.notifyEach { GameStartedMessage.compose(it) }
    }

    fun onTurnStart(playerId: Int) {
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

    fun onVictory(playerId: Int) {
        println("Player #$playerId won")
        winner = playerId
        gameEnded = true
        gameOn = false
        playersManager.notifyAll(GameEndedMessage.compose(playerId))
        playersManager.kickAll()
    }

    fun onTie() {
        println("Tie!")
        winner = -1
        gameEnded = true
        gameOn = false
        playersManager.notifyAll(GameEndedMessage.compose(-1))
        playersManager.kickAll()
    }

    fun onError(exception: Exception) {
        println("Error occurred in the LocalGameLoop $exception")
    }

    private fun checkIfGameEnded() {
        val fieldManager = FieldManager(field, GameApp.CELLS_NUMBER)
        val victoriousPlayer = fieldManager.getWinner()
        if (victoriousPlayer != null) {
            onVictory(victoriousPlayer)
        } else {
            if (field.none { it == -1 }) {
                onTie()
            }
        }
    }

    private fun getNextPlayer(): Int {
        return 1 - activePlayer
    }

    class IllegalTurnPosition : IllegalArgumentException()

    class PlayerCannotMakeTurn : IllegalArgumentException()
}
