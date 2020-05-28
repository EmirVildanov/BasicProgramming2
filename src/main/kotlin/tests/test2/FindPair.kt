package tests.test2

import tests.test2.views.GameView
import tests.test2.views.Tile
import tornadofx.launch
import tornadofx.isInt
import tornadofx.App

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
        var tilesArray = mutableListOf<Tile>()
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
