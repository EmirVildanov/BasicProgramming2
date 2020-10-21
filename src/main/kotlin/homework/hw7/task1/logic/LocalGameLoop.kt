package homework.hw7.task1.logic

import homework.hw7.task1.GameApp.Companion.CELLS_NUMBER
import homework.hw7.task1.GameApp.Companion.FIELD_SIZE

open class LocalGameLoop : GameLoop {
    private val field = MutableList(FIELD_SIZE) { -1 }
    private var activePlayer = -1
    private var gameEnded = false
    private var winner = -1

    override fun onGameStart() {
        field.replaceAll { -1 }
        gameEnded = false
        activePlayer = -1
        onTurnStart(0)
    }

    override fun onTurnStart(playerId: Int) {
        activePlayer = playerId
    }

    override fun onTurnMade(playerId: Int, position: Int) {
        field[position] = playerId
    }

    private fun checkIfGameEnded() {
        val fieldManager = FieldManager(field, CELLS_NUMBER)
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

    override fun onVictory(playerId: Int) {
        winner = playerId
        gameEnded = true
    }

    override fun onDraw() {
        winner = -1
        gameEnded = true
    }

    override fun onError(exception: Exception) {
        println("Error occurred in the LocalGameLoop $exception")
    }

    override fun makeTurn(playerId: Int, position: Int) {
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
