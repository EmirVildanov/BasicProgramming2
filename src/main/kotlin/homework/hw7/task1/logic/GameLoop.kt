package homework.hw7.task1.logic

interface GameLoop {
    fun onGameStart()
    fun onTurnStart(playerId: Int)
    fun onTurnMade(playerId: Int, position: Int)
    fun onVictory(playerId: Int)
    fun onDraw()
    fun onError(exception: Exception)

    fun makeTurn(playerId: Int, position: Int)

    class IllegalTurnPositionException(message: String) : IllegalArgumentException(message)
    class PlayerCannotMakeTurnException(message: String) : IllegalArgumentException(message)
}
