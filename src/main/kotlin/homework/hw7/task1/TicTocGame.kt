package homework.hw7.task1

import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import tornadofx.*
import java.lang.Math.abs

var crossTurn = true
var playerTurn = true
var gameIsOver = false
var botDifficulty = 1

var firstPlayerTurnCheck = false
var firstPlayerTurnCellIndex = -1

var secondPlayerTurnCheck = false
var secondPlayerTurnCellIndex = -1

var lastPlayerTurn = -1

const val SIDE = 150.0
const val WORDS_FONT = 30.0
const val FIELD_SIDE = 3
const val BOT_DELAY_TIME = 0.5

val fieldsShapesArray = Array(FIELD_SIDE * FIELD_SIDE) { Cell.InsideShape.NOTHING }
val cellsArray = mutableListOf<Cell>()
val difficultyToggleGroup = ToggleGroup()

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

private fun printField() {
    for (i in 0 until FIELD_SIDE) {
        for (j in 0 until FIELD_SIDE) {
            if (fieldsShapesArray[i * 3 + j] == Cell.InsideShape.CROSS) {
                print(1)
            } else if (fieldsShapesArray[i * 3 + j] == Cell.InsideShape.CIRCLE) {
                print(2)
            } else {
                print(3)
            }
        }
        print('\n')
    }
    print('\n')
}

fun putFigure(currentCell: Cell): Boolean {
    if (currentCell.activeStatus) {
        if (crossTurn) {
            currentCell.add(CrossCell().root)
            currentCell.activeStatus = false
            fieldsShapesArray[currentCell.cellNumber] = Cell.InsideShape.CROSS
        } else {
            currentCell.add(CircleCell().root)
            currentCell.activeStatus = false
            fieldsShapesArray[currentCell.cellNumber] = Cell.InsideShape.CIRCLE
        }
        return true
    }
    return false
}

class MyApp : App(StartMenu::class) {
//    override val primaryView = StartMenu::class

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = SIDE * FIELD_SIDE + 63.0
        stage.height = SIDE * FIELD_SIDE + 63.0
    }
}

object Bot {
    private fun randomTurn(sideTurn: Boolean = false, cornerTurn: Boolean = false) {
        val randomCellsArray = cellsArray.shuffled()
        for (i in randomCellsArray.indices) {
            if (randomCellsArray[i].activeStatus) {
                if (!sideTurn || !cornerTurn || (sideTurn && i != 0 && i != 2 && i != 6 && i != 8) || (cornerTurn && i != 1 && i != 3 && i != 4 && i != 5 && i != 7)) {
                    putFigure(randomCellsArray[i])
                    break
                }
            }
        }
    }

    private fun distributeCells(permitted: MutableList<Int>, player: MutableList<Int>, bot: MutableList<Int>) {
        permitted.forEach {
            if (crossTurn) {
                fieldsShapesArray[it] = Cell.InsideShape.CIRCLE
                if (PlayingField.checkGameOver() == PlayingField.GameOverCheck.CIRCLE_WON) {
                    player.add(it)
                }
                fieldsShapesArray[it] = Cell.InsideShape.NOTHING

                fieldsShapesArray[it] = Cell.InsideShape.CROSS
                if (PlayingField.checkGameOver() == PlayingField.GameOverCheck.CROSS_WON) {
                    bot.add(it)
                }
                fieldsShapesArray[it] = Cell.InsideShape.NOTHING
            } else {
                fieldsShapesArray[it] = Cell.InsideShape.CROSS
                if (PlayingField.checkGameOver() == PlayingField.GameOverCheck.CROSS_WON) {
                    player.add(it)
                }
                fieldsShapesArray[it] = Cell.InsideShape.NOTHING

                fieldsShapesArray[it] = Cell.InsideShape.CIRCLE
                if (PlayingField.checkGameOver() == PlayingField.GameOverCheck.CIRCLE_WON) {
                    bot.add(it)
                }
                fieldsShapesArray[it] = Cell.InsideShape.NOTHING
            }
        }
    }

    private fun easyTurn() {
        val permittedCellsArray = mutableListOf<Int>()
        cellsArray.forEach { if (it.activeStatus) permittedCellsArray.add(it.cellNumber) }
        val playerWonCellsArray = mutableListOf<Int>()
        val botWonCellsArray = mutableListOf<Int>()
        distributeCells(permittedCellsArray, playerWonCellsArray, botWonCellsArray)
        permittedCellsArray.removeAll(playerWonCellsArray)
        permittedCellsArray.removeAll(botWonCellsArray)
        if (permittedCellsArray.isNotEmpty()) {
            putFigure(cellsArray[permittedCellsArray.shuffled()[0]])
        } else {
            randomTurn()
        }
    }

    private fun mediumTurn() {
        val permittedCellsArray = mutableListOf<Int>()
        cellsArray.forEach { if (it.activeStatus) permittedCellsArray.add(it.cellNumber) }
        val playerWonCellsArray = mutableListOf<Int>()
        val botWonCellsArray = mutableListOf<Int>()
        distributeCells(permittedCellsArray, playerWonCellsArray, botWonCellsArray)
        if (botWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[botWonCellsArray[0]])
        } else if (playerWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[playerWonCellsArray[0]])
        } else {
            randomTurn()
        }
    }

    private fun putFigureOnTheMostRemoteCell(): Boolean {
        val row = lastPlayerTurn / 3
        val column = lastPlayerTurn % 3
        val appropriateCellsArray = mutableListOf<Cell>()
        val maxDistance = 0
        for (i in 0 until FIELD_SIDE) {
            for (j in 0 until FIELD_SIDE) {
                if ((i * FIELD_SIDE + j) / 3 - row + (i * FIELD_SIDE + j) % 3 - column == maxDistance) {
                    appropriateCellsArray.add(cellsArray[(i * FIELD_SIDE + j)])
                } else if ((i * FIELD_SIDE + j) / 3 + (i * FIELD_SIDE + j) % 3 > maxDistance) {
                    appropriateCellsArray.clear()
                    appropriateCellsArray.add(cellsArray[(i * FIELD_SIDE + j)])
                }
            }
        }
        appropriateCellsArray.forEach {
            if (putFigure(it)) {
                return true
            }
        }
        return false
    }

    private fun checkOppositeSecondPlayerTurn(): Boolean {
        if (firstPlayerTurnCellIndex / 3 == 3 - secondPlayerTurnCellIndex / 3 && firstPlayerTurnCellIndex % 3 == 3 - secondPlayerTurnCellIndex % 3) {
            return true
        }
        return false
    }

    private fun hardTurn() {
        val permittedCellsArray = mutableListOf<Int>()
        cellsArray.forEach { if (it.activeStatus) permittedCellsArray.add(it.cellNumber) }
        val playerWonCellsArray = mutableListOf<Int>()
        val botWonCellsArray = mutableListOf<Int>()
        distributeCells(permittedCellsArray, playerWonCellsArray, botWonCellsArray)
        if (botWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[botWonCellsArray[0]])
        } else if (playerWonCellsArray.isNotEmpty()) {
            putFigure(cellsArray[playerWonCellsArray[0]])
        } else {
            if (crossTurn) {
                println("1")
                printField()
                if (fieldsShapesArray[4] == Cell.InsideShape.NOTHING) {
                    putFigure(cellsArray[4])
                } else if (!putFigureOnTheMostRemoteCell()) {
                    randomTurn()
                }
            } else {
                println("2")
                printField()
                if (fieldsShapesArray[4] == Cell.InsideShape.NOTHING) {
                    putFigure(cellsArray[4])
                } else if (fieldsShapesArray[4] == Cell.InsideShape.CIRCLE) {
                    println(firstPlayerTurnCellIndex)
                    println(secondPlayerTurnCellIndex)
                    if (firstPlayerTurnCellIndex == 0 || firstPlayerTurnCellIndex == 2 || firstPlayerTurnCellIndex == 6 || firstPlayerTurnCellIndex == 8) {
                        printField()
                        if (firstPlayerTurnCellIndex == 0) {
                            if (!putFigure(cellsArray[8])) {
                                randomTurn(true)
                            }
                        } else if (firstPlayerTurnCellIndex == 2) {
                            if (!putFigure(cellsArray[6])) {
                                randomTurn(true)
                            }
                        } else if (firstPlayerTurnCellIndex == 6) {
                            if (!putFigure(cellsArray[2])) {
                                randomTurn(true)
                            }
                        } else if (firstPlayerTurnCellIndex == 8) {
                            if (!putFigure(cellsArray[0])) {
                                randomTurn(sideTurn = true)
                            }
                        } else {
                            randomTurn()
                        }
                    } else {
                        if (secondPlayerTurnCellIndex == 0 || secondPlayerTurnCellIndex == 2 || secondPlayerTurnCellIndex == 6 || secondPlayerTurnCellIndex == 8) {
                            if (secondPlayerTurnCellIndex == 0) {
                                randomTurn(true)
                            } else if (secondPlayerTurnCellIndex == 2) {
                                randomTurn(true)
                            } else if (secondPlayerTurnCellIndex == 6) {
                                randomTurn(true)
                            } else if (secondPlayerTurnCellIndex == 8) {
                                randomTurn(sideTurn = true)
                            } else {
                                randomTurn()
                            }
                        } else if (checkOppositeSecondPlayerTurn()) {
                            randomTurn(cornerTurn = true)
                        } else {
                            when {
                                firstPlayerTurnCellIndex == 1 && secondPlayerTurnCellIndex == 3 -> putFigure(cellsArray[0])
                                firstPlayerTurnCellIndex == 1 && secondPlayerTurnCellIndex == 5 -> putFigure(cellsArray[2])
                                firstPlayerTurnCellIndex == 3 && secondPlayerTurnCellIndex == 1 -> putFigure(cellsArray[0])
                                firstPlayerTurnCellIndex == 3 && secondPlayerTurnCellIndex == 7 -> putFigure(cellsArray[6])
                                firstPlayerTurnCellIndex == 5 && secondPlayerTurnCellIndex == 1 -> putFigure(cellsArray[2])
                                firstPlayerTurnCellIndex == 5 && secondPlayerTurnCellIndex == 7 -> putFigure(cellsArray[8])
                                firstPlayerTurnCellIndex == 7 && secondPlayerTurnCellIndex == 3 -> putFigure(cellsArray[6])
                                firstPlayerTurnCellIndex == 7 && secondPlayerTurnCellIndex == 5 -> putFigure(cellsArray[8])
                            }
                        }
                    }
                } else {
                    if (fieldsShapesArray[0] == Cell.InsideShape.NOTHING) {
                        putFigure(cellsArray[0])
                    } else if (fieldsShapesArray[2] == Cell.InsideShape.NOTHING) {
                        putFigure(cellsArray[2])
                    } else if (fieldsShapesArray[6] == Cell.InsideShape.NOTHING) {
                        putFigure(cellsArray[6])
                    } else if (fieldsShapesArray[8] == Cell.InsideShape.NOTHING) {
                        putFigure(cellsArray[8])
                    } else {
                        randomTurn()
                    }
                }
            }
        }
    }

    fun makeBotTurn() {
        when (botDifficulty) {
            1 -> {
                easyTurn()
            }
            2 -> {
                mediumTurn()
            }
            3 -> {
                hardTurn()
            }
        }
    }
}

class StartMenu : View() {
    override val root = vbox {
        label {
            text = "TicToc"
            style {
                fontSize = WORDS_FONT.px
//                paddingHorizontal = 50
            }
        }
        label("Choose your side: ")
        hbox {
            button {
                add(CrossCell())
                action {
                    playerTurn = true
                }
            }
            button {
                add(CircleCell())
                action {
                    playerTurn = false
                }
            }
        }
        label("Choose level of difficulty: ")
        vbox {
            radiobutton {
                text = "Easy"
                isSelected = true
                toggleGroup = difficultyToggleGroup
                action {
                    botDifficulty = 1
                }
            }
            radiobutton {
                text = "Medium"
                toggleGroup = difficultyToggleGroup
                action {
                    botDifficulty = 2
                }
            }
            radiobutton {
                text = "Hard"
                toggleGroup = difficultyToggleGroup
                action {
                    botDifficulty = 3
                }
            }
            button {
                text = "Start game"
                action {
                    close()
//                    PlayingField.onRefresh()
                    PlayingField.openWindow()
                }
            }
        }
    }
}

object PlayingField : View() {
    init {
//        onRefresh()
    }

    override fun onRefresh() {
        for (i in 0 until FIELD_SIDE) {
            hbox {
                for (j in 0 until FIELD_SIDE) {
                    val currentCell = Cell(i * FIELD_SIDE + j)
                    add(currentCell)
                    currentCell.drawFigure(fieldsShapesArray[i * FIELD_SIDE + j])
                    cellsArray.add(currentCell)
                }
            }
        }
        if (!playerTurn) { // при рестарте поле не появляется снова, а возвращается
            println("ffffffffffffffffffffff")
            Bot.makeBotTurn()
        }
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        if (!playerTurn) { // при рестарте раотат вот это, а не то, что снизу
            Bot.makeBotTurn()
            crossTurn = !crossTurn
            playerTurn = true
        }
    }

    override val root = vbox {
        for (i in 0 until FIELD_SIDE) {
            hbox {
                for (j in 0 until FIELD_SIDE) {
                    val currentCell = Cell(i * FIELD_SIDE + j)
                    add(currentCell)
                    cellsArray.add(currentCell)
                }
            }
        }
        if (!playerTurn) { // при рестарте поле не появляется снова, а возвращается
            Bot.makeBotTurn()
            crossTurn = !crossTurn
            playerTurn = true
        }
    }

    fun restart() {
        gameIsOver = false
        cellsArray.forEach {
            it.activeStatus = true
            fieldsShapesArray[it.cellNumber] = Cell.InsideShape.NOTHING
            cellsArray[it.cellNumber].add(RectangleCell())
        }
    }

    enum class GameOverCheck(val checkType: Int) {
        CROSS_WON(1),
        CIRCLE_WON(2),
        DRAW(3),
        NOTHING(4)
    }

    private fun checkDraw(): Boolean {
        fieldsShapesArray.forEach {
            if (it == Cell.InsideShape.NOTHING) {
                return false
            }
        }
        return true
    }

    fun checkGameOver(): GameOverCheck {
        if (
            fieldsShapesArray[0] == fieldsShapesArray[1] && fieldsShapesArray[1] == fieldsShapesArray[2] && fieldsShapesArray[2] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[3] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[5] && fieldsShapesArray[5] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[6] == fieldsShapesArray[7] && fieldsShapesArray[7] == fieldsShapesArray[8] && fieldsShapesArray[8] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[0] == fieldsShapesArray[3] && fieldsShapesArray[3] == fieldsShapesArray[6] && fieldsShapesArray[6] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[1] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[7] && fieldsShapesArray[7] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[2] == fieldsShapesArray[5] && fieldsShapesArray[5] == fieldsShapesArray[8] && fieldsShapesArray[8] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[0] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[8] && fieldsShapesArray[8] == Cell.InsideShape.CROSS ||
            fieldsShapesArray[2] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[6] && fieldsShapesArray[6] == Cell.InsideShape.CROSS
        ) {
            return GameOverCheck.CROSS_WON
        } else if (
            fieldsShapesArray[0] == fieldsShapesArray[1] && fieldsShapesArray[1] == fieldsShapesArray[2] && fieldsShapesArray[2] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[3] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[5] && fieldsShapesArray[5] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[6] == fieldsShapesArray[7] && fieldsShapesArray[7] == fieldsShapesArray[8] && fieldsShapesArray[8] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[0] == fieldsShapesArray[3] && fieldsShapesArray[3] == fieldsShapesArray[6] && fieldsShapesArray[6] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[1] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[7] && fieldsShapesArray[7] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[2] == fieldsShapesArray[5] && fieldsShapesArray[5] == fieldsShapesArray[8] && fieldsShapesArray[8] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[0] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[8] && fieldsShapesArray[8] == Cell.InsideShape.CIRCLE ||
            fieldsShapesArray[2] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[6] && fieldsShapesArray[6] == Cell.InsideShape.CIRCLE
        ) {
            return GameOverCheck.CIRCLE_WON
        } else if (checkDraw()) {
            return GameOverCheck.DRAW
        }
        return GameOverCheck.NOTHING
    }

    fun tryToEndGame() {
        gameIsOver = true
        when (checkGameOver()) {
            GameOverCheck.CROSS_WON -> {
                close()
                GameOverView("Crosses won!").openWindow()
            }
            GameOverCheck.CIRCLE_WON -> {
                close()
                GameOverView("Circles won!").openWindow()
            }
            GameOverCheck.DRAW -> {
                close()
                GameOverView("Draw!").openWindow()
            }
            else -> {
                gameIsOver = false
            }
        }
    }
}

class Cell(val cellNumber: Int) : Fragment() {
    enum class InsideShape(val shapeIndex: Int) {
        NOTHING(0),
        CROSS(1),
        CIRCLE(2)
    }
    private var shapeIndex = InsideShape.NOTHING
    var activeStatus = true

    override val root = button {
        add(RectangleCell())
        action {
            if (!gameIsOver && playerTurn && activeStatus) {
                if (crossTurn) {
                    add(CrossCell())
                    shapeIndex = InsideShape.CROSS
                    fieldsShapesArray[cellNumber] = InsideShape.CROSS
                    if (!firstPlayerTurnCheck) {
                        firstPlayerTurnCellIndex = cellNumber
                        firstPlayerTurnCheck = true
                    }
                    if (firstPlayerTurnCheck && !secondPlayerTurnCheck) {
                        secondPlayerTurnCellIndex = cellNumber
                        secondPlayerTurnCheck = true
                    }
                    lastPlayerTurn = cellNumber
                } else {
                    add(CircleCell())
                    shapeIndex = InsideShape.CIRCLE
                    fieldsShapesArray[cellNumber] = InsideShape.CIRCLE
                    if (!firstPlayerTurnCheck) {
                        firstPlayerTurnCellIndex = cellNumber
                        firstPlayerTurnCheck = true
                    }
                    if (firstPlayerTurnCheck && !secondPlayerTurnCheck) {
                        secondPlayerTurnCellIndex = cellNumber
                        secondPlayerTurnCheck = true
                    }
                    lastPlayerTurn = cellNumber
                }
                activeStatus = false
                runLater(BOT_DELAY_TIME.seconds) { makeTurn() }
            }
        }
    }

    fun drawFigure(shape: InsideShape) {
        when (shape) {
            InsideShape.CROSS -> { add(RectangleCell()) }
            InsideShape.CIRCLE -> { add(CircleCell()) }
            InsideShape.NOTHING -> {}
        }
    }

    private fun makeTurn() {
        println("Cross turn is " + crossTurn)
        playerTurn = false
        crossTurn = !crossTurn
        PlayingField.tryToEndGame()
        if (!gameIsOver) {
            println("Cross turn is " + crossTurn)
            Bot.makeBotTurn()
            println("Bot make turn")
            runLater(BOT_DELAY_TIME.seconds) {
                PlayingField.tryToEndGame()
                if (gameIsOver) {
                    PlayingField.restart()
                } else {
                    crossTurn = !crossTurn
                    playerTurn = true
                }
            }
        } else {
            playerTurn = true
            crossTurn = !crossTurn
            PlayingField.restart()
        }
    }
}

class RectangleCell : Fragment() {
    override val root = vbox {
        rectangle {
            fill = Color.WHITE
            width = SIDE + 3
            height = SIDE + 3
        }
    }
}

class CircleCell : Fragment() {
    override val root = vbox {
        circle {
            stroke = Color.BLACK
            fill = null
            centerX = SIDE / 2
            centerY = SIDE / 2
            radius = SIDE / 2 + 1
        }
    }
}

class CrossCell : Fragment() {
    override val root = hbox {
        polyline(0.0, 0.0,
            SIDE,
            SIDE, SIDE / 2, SIDE / 2,
            SIDE, 0.0, 0.0,
            SIDE
        )
    }
}

class GameOverView(gameOverString: String) : View() {
    override val root = vbox {
        label {
            text = gameOverString
            font = Font.font(WORDS_FONT)
        }
        button {
            isCenterShape = true
            text = "restart"
            action {
                close()
                find<StartMenu>().openWindow()
            }
        }
    }
}
