package homework.hw7.task1.models

import homework.hw7.task1.logic.GameLoop
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

interface GameModel : GameLoop {
    val field: List<SimpleIntegerProperty>

    val activePlayerProperty: SimpleIntegerProperty
    var activePlayer: Int

    val winnerProperty: SimpleIntegerProperty
    var winner: Int

    val gameOverProperty: SimpleBooleanProperty
    var gameOver: Boolean

    val waitingProperty: SimpleBooleanProperty
    var waiting: Boolean

    val errorMessageProperty: SimpleStringProperty
    var errorMessage: String

    override fun onGameStart()
    fun makeTurn(position: Int)
}
