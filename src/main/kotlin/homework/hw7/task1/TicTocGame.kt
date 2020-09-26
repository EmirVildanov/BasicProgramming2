package homework.hw7.task1

import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.stage.Stage
import tornadofx.*
import kotlin.math.sqrt
import kotlin.math.pow

var crossTurn = true
var playerTurn = true
var gameIsOver = false
var botDifficulty = BotDifficulty.EASY

var firstPlayerTurnCheck = false
var firstPlayerTurnCellIndex = -1

var secondPlayerTurnCheck = false
var secondPlayerTurnCellIndex = -1

var lastPlayerTurn = -1

const val SIDE = 150.0
const val WORDS_FONT = 30.0
const val FIELD_SIDE = 3
const val BOT_DELAY_TIME = 0.5

val CORNER_INDEXES = arrayOf(0, 2, 6, 8)
val NOT_CORNER_INDEXES = arrayOf(1, 3, 4, 5, 7)

val field = Array(FIELD_SIDE * FIELD_SIDE) { Cell.InsideShape.NOTHING }
val cellsArray = mutableListOf<Cell>()
val difficultyToggleGroup = ToggleGroup()

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

class MyApp : App(StartMenu::class, TestStyle::class) {
//    override val primaryView = StartMenu::class

    override fun start(stage: Stage) {
        super.start(stage)
//        stage.width = SIDE * FIELD_SIDE
//        stage.height = SIDE * FIELD_SIDE
    }

    init {
        reloadStylesheetsOnFocus()
    }
}

private fun printField() {
    for (i in 0 until FIELD_SIDE) {
        for (j in 0 until FIELD_SIDE) {
            if (field[i * FIELD_SIDE + j] == Cell.InsideShape.CROSS) {
                print(1)
            } else if (field[i * FIELD_SIDE + j] == Cell.InsideShape.CIRCLE) {
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
            currentCell.add(CrossCell(false).root)
            currentCell.activeStatus = false
            field[currentCell.cellNumber] = Cell.InsideShape.CROSS
        } else {
            currentCell.add(CircleCell(false).root)
            currentCell.activeStatus = false
            field[currentCell.cellNumber] = Cell.InsideShape.CIRCLE
        }
        return true
    }
    return false
}

class StartMenu : View() {
    override val root = vbox {
        addClass(TestStyle.gameMenu)
        this.isFillWidth = true
        label {
            text = "TicToc"
            style {
                fontSize = WORDS_FONT.px
            }
        }
        label("Choose your side: ")
        hbox {
            addClass(TestStyle.centerAlignment)
            button {
                add(CrossCell(playerTurn))
                action {
                    playerTurn = true
                }
            }
            button {
                add(CircleCell(!playerTurn))
                action {
                    playerTurn = false
                }
            }
        }
        label("Choose level of difficulty: ")
        vbox {
            addClass(TestStyle.centerAlignment)
            radiobutton {
                text = "Easy"
                isSelected = true
                toggleGroup = difficultyToggleGroup
                action {
                    botDifficulty = BotDifficulty.EASY
                }
            }
            radiobutton {
                text = "Medium"
                toggleGroup = difficultyToggleGroup
                action {
                    botDifficulty = BotDifficulty.MEDIUM
                }
            }
            radiobutton {
                text = "Hard"
                toggleGroup = difficultyToggleGroup
                action {
                    botDifficulty = BotDifficulty.HARD
                }
            }
            button {
                text = "Start game"
                action {
                    close()
                    PlayingField.onRefresh()
                    PlayingField.openWindow()
                }
            }
        }
    }
}

object PlayingField : View() {
//    init {
//        onRefresh()
//    }

//    override fun onRefresh() {
//        for (i in 0 until FIELD_SIDE) {
//            hbox {
//                for (j in 0 until FIELD_SIDE) {
//                    val currentCell = Cell(i * FIELD_SIDE + j)
//                    add(currentCell)
//                    currentCell.drawFigure(field[i * FIELD_SIDE + j])
//                    cellsArray.add(currentCell)
//                }
//            }
//        }
//        if (!playerTurn) { // при рестарте поле не появляется снова, а возвращается
//            println("ffffffffffffffffffffff")
//            Bot.makeBotTurn()
//        }
//    }

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
//        if (crossTurn && !playerTurn) {
//
//        }
        gameIsOver = false
        cellsArray.forEach {
            it.activeStatus = true
            field[it.cellNumber] = Cell.InsideShape.NOTHING
            cellsArray[it.cellNumber].add(RectangleCell())
        }
    }

    enum class GameOverCheck {
        CROSS_WON,
        CIRCLE_WON,
        DRAW,
        NOTHING
    }

    private fun checkDraw(): Boolean {
        field.forEach {
            if (it == Cell.InsideShape.NOTHING) {
                return false
            }
        }
        return true
    }

    fun checkGameOver(): GameOverCheck {
        val winner: GameOverCheck
        if (
            checkColumnGameOver(Cell.InsideShape.CROSS) ||
            checkRowGameOver(Cell.InsideShape.CROSS) ||
            checkDiagonalGameOver(Cell.InsideShape.CROSS)
        ) {
            winner = GameOverCheck.CROSS_WON
        } else if (
            checkColumnGameOver(Cell.InsideShape.CIRCLE) ||
            checkRowGameOver(Cell.InsideShape.CIRCLE) ||
            checkDiagonalGameOver(Cell.InsideShape.CIRCLE)
        ) {
            winner = GameOverCheck.CIRCLE_WON
        } else if (checkDraw()) {
            winner = GameOverCheck.DRAW
        } else {
            winner = GameOverCheck.NOTHING
        }
        return winner
    }

    private fun checkRowGameOver(pretender: Cell.InsideShape): Boolean {
        return field[0] == field[1] && field[1] == field[2] && field[2] == pretender ||
                field[3] == field[4] && field[4] == field[5] && field[5] == pretender ||
                field[6] == field[7] && field[7] == field[8] && field[8] == pretender
    }

    private fun checkColumnGameOver(pretender: Cell.InsideShape): Boolean {
        return field[0] == field[3] && field[3] == field[6] && field[6] == pretender ||
                field[1] == field[4] && field[4] == field[7] && field[7] == pretender ||
                field[2] == field[5] && field[5] == field[8] && field[8] == pretender
    }

    private fun checkDiagonalGameOver(pretender: Cell.InsideShape): Boolean {
        return field[0] == field[4] && field[4] == field[8] && field[8] == pretender ||
                field[2] == field[4] && field[4] == field[6] && field[6] == pretender
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
    enum class InsideShape {
        NOTHING,
        CROSS,
        CIRCLE
    }
    private var shapeIndex = InsideShape.NOTHING
    var activeStatus = true

    override val root = button {
        add(RectangleCell())
        action {
            if (!gameIsOver && playerTurn && activeStatus) {
                if (crossTurn) {
                    add(CrossCell(false))
                    shapeIndex = InsideShape.CROSS
                    field[cellNumber] = InsideShape.CROSS
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
                    add(CircleCell(false))
                    shapeIndex = InsideShape.CIRCLE
                    field[cellNumber] = InsideShape.CIRCLE
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

    private fun makeTurn() {
        println("Cross turn is $crossTurn")
        playerTurn = false
        crossTurn = !crossTurn
        PlayingField.tryToEndGame()
        if (!gameIsOver) {
            println("Cross turn is $crossTurn")
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

//class CrossCell:

class RectangleCell : Fragment() {
    override val root = vbox {
        rectangle {
            fill = Color.WHITE
            width = SIDE + 3
            height = SIDE + 3
        }
    }
}

class CircleCell(isSelected: Boolean) : Fragment() {
    override val root = vbox {
        if (isSelected) {
            println("Selection is working")
            addClass(TestStyle.cellSelected)
        }
        circle {
            stroke = Color.BLACK
            fill = null
            centerX = SIDE / 2
            centerY = SIDE / 2
            radius = SIDE / 2 + 1
        }
    }
}

class CrossCell(isSelected: Boolean) : Fragment() {
    override val root = hbox {
        if (isSelected) {
            addClass(TestStyle.cellSelected)
        }
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
        addClass(TestStyle.gameMenu)
        label {
            text = gameOverString
            font = Font.font(WORDS_FONT)
        }
        button {
            text = "restart"
            action {
                close()
                find<StartMenu>().openWindow()
            }
        }
    }
}
