package tests.test2

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tests.test2.views.GameView
import tests.test2.views.WinView
import tornadofx.hbox
import tornadofx.launch
import tornadofx.isInt
import tornadofx.App
import tornadofx.Fragment
import tornadofx.button
import tornadofx.paddingAll
import tornadofx.action
import tornadofx.runLater
import tornadofx.seconds

fun main(args: Array<String>) {
    var inputCheck = false
    if (args.size == 1) {
        if (args[0].isInt() && args[0].toInt() % 2 == 0) {
            Game().main(args)
            inputCheck = true
        }
    }
    if (!inputCheck) {
        println("Wrong arguments")
        println(args[0])
    }
}

class Game : App() {
    override val primaryView = GameView::class

    companion object Info {
        val currentlyUnusedNumbers = mutableListOf<Int>()
        val numbersArray = mutableListOf<Int>()
        val tilesArray = mutableListOf<Tile>()
        var firstTileClicked = false
        var firstClickedButtonIndex = -1
        var fieldSide = 0
    }

    fun fillRandomNumberArray() {
        for (currentIndex in 0 until fieldSide * fieldSide / 2) {
            currentlyUnusedNumbers.add(currentIndex)
            currentlyUnusedNumbers.add(currentIndex)
        }
        var currentNumber: Int
        var currentNumberIndex: Int
        for (currentIndex in 0 until fieldSide * fieldSide) {
            currentNumberIndex = (0 until currentlyUnusedNumbers.size).random()
            currentNumber = currentlyUnusedNumbers[currentNumberIndex]
            numbersArray.add(currentNumber)
            currentlyUnusedNumbers.removeAt(currentNumberIndex)
        }
    }

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
        var currentNumberIndex: Int
        for (i in 0 until fieldSide * fieldSide) {
            currentNumberIndex = (0 until currentlyUnusedNumbers.size).random()
            tilesArray[i].number = currentlyUnusedNumbers[currentNumberIndex]
            currentlyUnusedNumbers.removeAt(currentNumberIndex)
        }
        tilesArray.forEach {
            it.buttonText.set(" ")
            it.isDisabledProperty.set(false)
        }
    }

    fun main(args: Array<String>) {
        fieldSide = args[0].toInt()
        launch<Game>()
    }
}

class Tile(private val index: Int, var number: Int) : Fragment() {
    val buttonText = SimpleStringProperty(" ")
    var isDisabledProperty = SimpleBooleanProperty(false)
    private val currentTile = this
    companion object Constants {
        const val PAD_ALL = 15
        val DELAY = 0.5.seconds
    }

    override val root = hbox {
        paddingAll = PAD_ALL
        button {
            paddingAll = PAD_ALL
            textProperty().bind(buttonText)
            disableProperty().bind(isDisabledProperty)
            action {
                if (!Game.firstTileClicked) {
                    buttonText.set(number.toString())
                    Game.firstTileClicked = true
                    Game.firstClickedButtonIndex = index
                } else {
                    val firstClickedButton = Game.tilesArray[Game.firstClickedButtonIndex]
                    if (currentTile != firstClickedButton && number == firstClickedButton.number) {
                        firstClickedButton.buttonText.set(firstClickedButton.number.toString())
                        buttonText.set(number.toString())
                        disable()
                        firstClickedButton.disable()
                        Game.firstTileClicked = false
                        Game.firstClickedButtonIndex = -1
                    } else {
                        buttonText.set(number.toString())
                        runLater(DELAY) {
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
        if (Game().checkWin()) {
            runLater(DELAY) {
                close()
                find<WinView>().openWindow()
            }
        }
    }
}
