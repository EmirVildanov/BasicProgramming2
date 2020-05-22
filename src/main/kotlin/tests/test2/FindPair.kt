package tests.test2

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.text.Font
import javafx.stage.Stage
import tornadofx.*

fun main(args: Array<String>) {
    val args = arrayOf("2")
    var inputCheck = false
    if (args.size == 1) {
        if (args[0].isInt() && args[0].toInt() % 2 == 0) {
//            launch<TestApp>()
//            Game.main(args)
            Game.main(args)
//            Application.launch(*args)
            inputCheck = true
        }
    }
    if (!inputCheck) {
        println("Wrong arguments")
        println(args[0])
    }
}

class TestApp() : App(GameView::class) {

}

object Game : App(GameView::class) {
    override val primaryView = GameView::class

    val currentlyUnusedNumbers = mutableListOf<Int>()
    val numbersArray = mutableListOf<Int>()
    val tilesArray = mutableListOf<Tile>()
    const val fieldSide = 2
    var firstTileClicked = false
    var firstClickedButtonIndex = -1

    fun checkWin(): Boolean {
        var answer = true
        for (i in tilesArray.indices) {
            if (tilesArray[i].isDisabledProperty.value == false) {
                answer = false
                break
            }
        }
        return answer
    }

    fun restart() {
        for (i in 0 until fieldSide * fieldSide / 2) {
            currentlyUnusedNumbers.add(i)
            currentlyUnusedNumbers.add(i)
        }
        var currentNumber = 0
        var currentNumberIndex = 0
        for (i in 0 until fieldSide * fieldSide) {
            currentNumberIndex = (0 until currentlyUnusedNumbers.size).random()
//            currentNumber = currentlyUnusedNumbers[currentNumberIndex]
//            numbersArray.add(currentNumber)
            tilesArray[i].number = currentNumber
            currentlyUnusedNumbers.removeAt(currentNumberIndex)
        }
        tilesArray.forEach {
            it.buttonText.set(" ")
            it.isDisabledProperty.set(false)
        }
    }

    fun main(args: Array<String>) {
        launch(*args)
    }

    override fun start(stage: Stage) {
        val number = parameters.named["number"]
//        stage.width = SIDE * FIELD_SIDE + 63.0
//        stage.height = SIDE * FIELD_SIDE + 63.0
    }
}

class GameView : View() {
    private fun fillRandomNumberArray() {
        for (currentIndex in 0 until Game.fieldSide * Game.fieldSide / 2) {
            Game.currentlyUnusedNumbers.add(currentIndex)
            Game.currentlyUnusedNumbers.add(currentIndex)
        }
        var currentNumber = 0
        var currentNumberIndex = 0
        for (currentIndex in 0 until Game.fieldSide * Game.fieldSide) {
            currentNumberIndex = (0 until Game.currentlyUnusedNumbers.size).random()
            currentNumber = Game.currentlyUnusedNumbers[currentNumberIndex]
            Game.numbersArray.add(currentNumber)
            Game.currentlyUnusedNumbers.removeAt(currentNumberIndex)
        }
    }

    override val root = vbox {
        fillRandomNumberArray()
        for (i in 0 until Game.fieldSide) {
            hbox {
                var currentIndex = 0
                for (j in 0 until Game.fieldSide) {
                    currentIndex = i * Game.fieldSide + j
                    val currentTile = Tile(currentIndex, Game.numbersArray[currentIndex])
                    add(currentTile)
                    Game.tilesArray.add(currentTile)
                }
            }
        }
    }
}

class Tile(val index: Int, var number: Int) : Fragment() {
    val buttonText = SimpleStringProperty(" ")
    var isDisabledProperty = SimpleBooleanProperty(false)

    override val root = hbox {
        paddingAll = 15
        button {
//            paddingHorizontal = 10
            paddingAll = 15
            textProperty().bind(buttonText)
            disableProperty().bind(isDisabledProperty)
            action {
                if (!Game.firstTileClicked) {
                    buttonText.set(number.toString())
                    Game.firstTileClicked = true
                    Game.firstClickedButtonIndex = index
                } else {
                    val firstClickedButton = Game.tilesArray[Game.firstClickedButtonIndex]
                    if (number == firstClickedButton.number) {
                        firstClickedButton.buttonText.set(firstClickedButton.number.toString())
                        buttonText.set(number.toString())
                        disable()
                        firstClickedButton.disable()
                        Game.firstTileClicked = false
                        Game.firstClickedButtonIndex = -1
                    } else {
                        buttonText.set(number.toString())
                        runLater(0.5.seconds) {
                            buttonText.set(" ")
                            firstClickedButton.buttonText.set(" ")
                            Game.firstTileClicked = false
                            Game.firstClickedButtonIndex = -1
                        }
                    }
                }
            }
        }
    }

    fun disable() {
        isDisabledProperty.set(true)
        if (Game.checkWin()) {
            runLater(0.5.seconds) {
                close()
                find<WinView>().openWindow()
            }
        }
    }
}

class WinView : View() {
    override val root = vbox {
        label {
            text = "Congradulations!"
            font = Font(30.0)
        }
        button {
//            paddingLeft = 40
            text = "restart"
            action {
                Game.restart()
                close()
                find<GameView>().openWindow()
            }
        }
    }
}
