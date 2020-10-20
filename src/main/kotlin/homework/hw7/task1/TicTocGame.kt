package homework.hw7.task1

import homework.hw7.task1.enums.BotDifficulty
import homework.hw7.task1.enums.CellType
import homework.hw7.task1.enums.GameOverCheck
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

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

val field = Array(FIELD_SIDE * FIELD_SIDE) { CellType.NOTHING }
val cellsArray = mutableListOf<Cell>()
val difficultyToggleGroup = ToggleGroup()

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

class MyApp : App(StartMenu::class, TestStyle::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

fun putFigure(currentCell: Cell): Boolean {
    if (currentCell.activeStatus) {
        if (crossTurn) {
            currentCell.add(Cross().root)
            currentCell.activeStatus = false
            field[currentCell.cellNumber] = CellType.CROSS
        } else {
            currentCell.add(Circle().root)
            currentCell.activeStatus = false
            field[currentCell.cellNumber] = CellType.CIRCLE
        }
        return true
    }
    return false
}

class StartMenu : View() {

    private var crossColor = SimpleObjectProperty(Color.RED)
    private var circleColor = SimpleObjectProperty(Color.BLACK)

    override val root = vbox {
        addClass(TestStyle.gameMenu)
        this.isFillWidth = true
        label {
            text = "TicToc"
        }
        label("Choose your side: ")
        hbox {
            addClass(TestStyle.centerAlignment)
            button {
                addClass(TestStyle.fieldCell)
                add(Cross(crossColor))
                action {
                    playerTurn = true
                    crossColor.set(Color.RED)
                    circleColor.set(Color.BLACK)
                }
            }
            button {
                addClass(TestStyle.fieldCell)
                add(Circle(circleColor))
                action {
                    playerTurn = false
                    crossColor.set(Color.BLACK)
                    circleColor.set(Color.RED)
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
                    PlayingField.openWindow()
                }
            }
        }
    }
}

object PlayingField : View() {
    override fun onBeforeShow() {
        super.onBeforeShow()
        if (!playerTurn) {
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
        if (!playerTurn) {
            Bot.makeBotTurn()
            crossTurn = !crossTurn
            playerTurn = true
        }
    }

    fun restart() {
        gameIsOver = false
        cellsArray.forEach {
            it.activeStatus = true
            field[it.cellNumber] = CellType.NOTHING
            cellsArray[it.cellNumber].add(RectangleCell())
        }
    }

    private fun checkDraw(): Boolean {
        field.forEach {
            if (it == CellType.NOTHING) {
                return false
            }
        }
        return true
    }

    fun checkGameOver(): GameOverCheck {
        val winner: GameOverCheck
        if (
            checkColumnGameOver(CellType.CROSS) ||
            checkRowGameOver(CellType.CROSS) ||
            checkDiagonalGameOver(CellType.CROSS)
        ) {
            winner = GameOverCheck.CROSS_WON
        } else if (
            checkColumnGameOver(CellType.CIRCLE) ||
            checkRowGameOver(CellType.CIRCLE) ||
            checkDiagonalGameOver(CellType.CIRCLE)
        ) {
            winner = GameOverCheck.CIRCLE_WON
        } else if (checkDraw()) {
            winner = GameOverCheck.DRAW
        } else {
            winner = GameOverCheck.NOTHING
        }
        return winner
    }

    private fun checkRowGameOver(pretender: CellType): Boolean {
        return field[0] == field[1] && field[1] == field[2] && field[2] == pretender ||
                field[3] == field[4] && field[4] == field[5] && field[5] == pretender ||
                field[6] == field[7] && field[7] == field[8] && field[8] == pretender
    }

    private fun checkColumnGameOver(pretender: CellType): Boolean {
        return field[0] == field[3] && field[3] == field[6] && field[6] == pretender ||
                field[1] == field[4] && field[4] == field[7] && field[7] == pretender ||
                field[2] == field[5] && field[5] == field[8] && field[8] == pretender
    }

    private fun checkDiagonalGameOver(pretender: CellType): Boolean {
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

    var activeStatus = true
    var type = CellType.NOTHING
        private set

    override val root = button {
        addClass(TestStyle.fieldCell)
        add(RectangleCell())
        action {
            if (!gameIsOver && playerTurn && activeStatus) {
                if (crossTurn) {
                    add(Cross())
                    field[cellNumber] = CellType.CROSS
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
                    add(Circle())
                    field[cellNumber] = CellType.CIRCLE
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
        playerTurn = false
        crossTurn = !crossTurn
        PlayingField.tryToEndGame()
        if (!gameIsOver) {
            Bot.makeBotTurn()
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
//            width = SIDE + 3
            width = SIDE
            height = SIDE
//            height = SIDE + 3
        }
    }
}

class Circle(private val circleColor: SimpleObjectProperty<Color>? = null) : Fragment() {
    override val root = vbox {
        val circle = circle {
            stroke = Color.BLACK
            fill = null
            centerX = SIDE / 2
            centerY = SIDE / 2
            radius = SIDE / 2 + 1
        }
        if (circleColor != null) {
            circle.strokeProperty().bind(circleColor)
        }
    }
}

class Cross(private val crossColor: SimpleObjectProperty<Color>? = null) : Fragment() {
    override val root = hbox {
        val cross = polyline(0.0, 0.0,
            SIDE,
            SIDE, SIDE / 2, SIDE / 2,
            SIDE, 0.0, 0.0,
            SIDE
        )
        if (crossColor != null) {
            cross.strokeProperty().bind(crossColor)
        }
    }
}
