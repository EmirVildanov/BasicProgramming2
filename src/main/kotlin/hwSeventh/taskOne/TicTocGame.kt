package hwSeventh.taskOne

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

class MyApp : App(StartMenu::class)

const val SIDE = 150.0
var crossTurn = true
val fieldsArray = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

class PlayField : View() {
    val colorsArray = mutableListOf(
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF"),
        SimpleStringProperty("FFFFFF")
    )
    val but: Button by inject()
    override val root = vbox {
        hbox {
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[0] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[0] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[1] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[1] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[2] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[2] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
        }
        hbox {
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[3] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[3] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[4] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[4] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[5] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[5] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
        }
        hbox {
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[6] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[6] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[7] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[7] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
            button {
                var readyStatus = true
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                }
                action {
                    if (readyStatus) {
                        if (crossTurn) {
                            polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                            fieldsArray[8] = 1
                        } else {
                            circle {
                                stroke = c("000000")
                                fill = null
                                centerX = SIDE / 2
                                centerY = SIDE / 2
                                radius = SIDE / 2
                            }
                            fieldsArray[8] = 2
                        }
                    }
                    readyStatus = false
                    crossTurn = !crossTurn
                    makeTurn()
                }
            }
        }
    }

    private fun makeTurn() {
        if (
            fieldsArray[0] == fieldsArray[1] && fieldsArray[1] == fieldsArray[2] && fieldsArray[2] == 1 ||
            fieldsArray[3] == fieldsArray[4] && fieldsArray[4] == fieldsArray[5] && fieldsArray[5] == 1  ||
            fieldsArray[0] == fieldsArray[7] && fieldsArray[7] == fieldsArray[8] && fieldsArray[8] == 1  ||
            fieldsArray[0] == fieldsArray[3] && fieldsArray[3] == fieldsArray[6] && fieldsArray[6] == 1  ||
            fieldsArray[1] == fieldsArray[4] && fieldsArray[4] == fieldsArray[7] && fieldsArray[7] == 1  ||
            fieldsArray[2] == fieldsArray[5] && fieldsArray[5] == fieldsArray[8] && fieldsArray[8] == 1  ||
            fieldsArray[0] == fieldsArray[4] && fieldsArray[4] == fieldsArray[8] && fieldsArray[8] == 1  ||
            fieldsArray[2] == fieldsArray[4] && fieldsArray[4] == fieldsArray[6] && fieldsArray[6] == 1
        )
        {
            replaceWith<CrossWon>()
        } else if (
            fieldsArray[0] == fieldsArray[1] && fieldsArray[1] == fieldsArray[2] && fieldsArray[2] == 2 ||
            fieldsArray[3] == fieldsArray[4] && fieldsArray[4] == fieldsArray[5] && fieldsArray[5] == 2  ||
            fieldsArray[0] == fieldsArray[7] && fieldsArray[7] == fieldsArray[8] && fieldsArray[8] == 2  ||
            fieldsArray[0] == fieldsArray[3] && fieldsArray[3] == fieldsArray[6] && fieldsArray[6] == 2  ||
            fieldsArray[1] == fieldsArray[4] && fieldsArray[4] == fieldsArray[7] && fieldsArray[7] == 2  ||
            fieldsArray[2] == fieldsArray[5] && fieldsArray[5] == fieldsArray[8] && fieldsArray[8] == 2  ||
            fieldsArray[0] == fieldsArray[4] && fieldsArray[4] == fieldsArray[8] && fieldsArray[8] == 2  ||
            fieldsArray[2] == fieldsArray[4] && fieldsArray[4] == fieldsArray[6] && fieldsArray[6] == 2
                ) {
            replaceWith<CircleWon>()
        }
    }
}

class StartMenu : View() {
    private val toggleGroup = ToggleGroup()
    override val root = vbox {
        val widthy = this.width
        label {
            text = "TicToc"
            style {
                fontSize = 30.px
                paddingHorizontal = 50
            }
        }
        label("Choose your side: ")
        hbox {
            button {
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                    polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                }
            }
            button {
                rectangle {
                    fill = c("FFFFFF")
                    width = SIDE
                    height = SIDE
                    circle {
                        stroke = c("000000")
                        fill = null
                        centerX = SIDE / 2
                        centerY = SIDE / 2
                        radius = SIDE / 2
                    }
                }
            }
        }
        label("Choose level of difficulty: ")
        vbox {
            radiobutton("Easy", toggleGroup)
            radiobutton("Medium", toggleGroup)
            radiobutton("Hard", toggleGroup)
        }
    }
}

class TestView : View() {
    override val root = tabpane {
        hbox {
            tab<Button> {}
            tab<Button> {}
            tab<Button> {}
        }
        hbox {
            tab<Button> {}
            tab<Button> {}
            tab<Button> {}
        }
        hbox {
            tab<Button> {}
            tab<Button> {}
            tab<Button> {}
        }
    }

//    private fun makeTurn() {
//        if (
//            fieldsArray[0] == fieldsArray[1] && fieldsArray[1] == fieldsArray[2] && fieldsArray[2] == 1 ||
//            fieldsArray[3] == fieldsArray[4] && fieldsArray[4] == fieldsArray[5] && fieldsArray[5] == 1  ||
//            fieldsArray[0] == fieldsArray[7] && fieldsArray[7] == fieldsArray[8] && fieldsArray[8] == 1  ||
//            fieldsArray[0] == fieldsArray[3] && fieldsArray[3] == fieldsArray[6] && fieldsArray[6] == 1  ||
//            fieldsArray[1] == fieldsArray[4] && fieldsArray[4] == fieldsArray[7] && fieldsArray[7] == 1  ||
//            fieldsArray[2] == fieldsArray[5] && fieldsArray[5] == fieldsArray[8] && fieldsArray[8] == 1  ||
//            fieldsArray[0] == fieldsArray[4] && fieldsArray[4] == fieldsArray[8] && fieldsArray[8] == 1  ||
//            fieldsArray[2] == fieldsArray[4] && fieldsArray[4] == fieldsArray[6] && fieldsArray[6] == 1
//        )
//        {
//            replaceWith<CrossWon>()
//        } else if (
//            fieldsArray[0] == fieldsArray[1] && fieldsArray[1] == fieldsArray[2] && fieldsArray[2] == 2 ||
//            fieldsArray[3] == fieldsArray[4] && fieldsArray[4] == fieldsArray[5] && fieldsArray[5] == 2  ||
//            fieldsArray[0] == fieldsArray[7] && fieldsArray[7] == fieldsArray[8] && fieldsArray[8] == 2  ||
//            fieldsArray[0] == fieldsArray[3] && fieldsArray[3] == fieldsArray[6] && fieldsArray[6] == 2  ||
//            fieldsArray[1] == fieldsArray[4] && fieldsArray[4] == fieldsArray[7] && fieldsArray[7] == 2  ||
//            fieldsArray[2] == fieldsArray[5] && fieldsArray[5] == fieldsArray[8] && fieldsArray[8] == 2  ||
//            fieldsArray[0] == fieldsArray[4] && fieldsArray[4] == fieldsArray[8] && fieldsArray[8] == 2  ||
//            fieldsArray[2] == fieldsArray[4] && fieldsArray[4] == fieldsArray[6] && fieldsArray[6] == 2
//        ) {
//            replaceWith<CircleWon>()
//        }
//    }

}

class TopView : View() {
    override val root = label("Top View")
}

class BottomView : View() {
    override val root = label("Top View")
}

class Filed : Fragment() {
    override val root = vbox {
        vbox {
            rectangle {
                fill = Color.WHITE
            }
        }
    }
}

class Button(private val cellNumber: Int) : View() {
    override val root = button {
        var readyStatus = true
        rectangle {
            fill = c("FFFFFF")
            width = SIDE
            height = SIDE
        }
        action {
            if (readyStatus) {
                if (crossTurn) {
                    polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                    fieldsArray[cellNumber - 1] = 1
                } else {
                    circle {
                        stroke = c("000000")
                        fill = null
                        centerX = SIDE / 2
                        centerY = SIDE / 2
                        radius = SIDE / 2
                    }
                    fieldsArray[cellNumber - 1] = 2
                }
            }
            readyStatus = false
            crossTurn = !crossTurn
            makeTurn()
        }
    }

    private fun makeTurn() {
        if (
            fieldsArray[0] == fieldsArray[1] && fieldsArray[1] == fieldsArray[2] && fieldsArray[2] == 1 ||
            fieldsArray[3] == fieldsArray[4] && fieldsArray[4] == fieldsArray[5] && fieldsArray[5] == 1  ||
            fieldsArray[0] == fieldsArray[7] && fieldsArray[7] == fieldsArray[8] && fieldsArray[8] == 1  ||
            fieldsArray[0] == fieldsArray[3] && fieldsArray[3] == fieldsArray[6] && fieldsArray[6] == 1  ||
            fieldsArray[1] == fieldsArray[4] && fieldsArray[4] == fieldsArray[7] && fieldsArray[7] == 1  ||
            fieldsArray[2] == fieldsArray[5] && fieldsArray[5] == fieldsArray[8] && fieldsArray[8] == 1  ||
            fieldsArray[0] == fieldsArray[4] && fieldsArray[4] == fieldsArray[8] && fieldsArray[8] == 1  ||
            fieldsArray[2] == fieldsArray[4] && fieldsArray[4] == fieldsArray[6] && fieldsArray[6] == 1
        )
        {
            replaceWith<CrossWon>()
        } else if (
            fieldsArray[0] == fieldsArray[1] && fieldsArray[1] == fieldsArray[2] && fieldsArray[2] == 2 ||
            fieldsArray[3] == fieldsArray[4] && fieldsArray[4] == fieldsArray[5] && fieldsArray[5] == 2  ||
            fieldsArray[0] == fieldsArray[7] && fieldsArray[7] == fieldsArray[8] && fieldsArray[8] == 2  ||
            fieldsArray[0] == fieldsArray[3] && fieldsArray[3] == fieldsArray[6] && fieldsArray[6] == 2  ||
            fieldsArray[1] == fieldsArray[4] && fieldsArray[4] == fieldsArray[7] && fieldsArray[7] == 2  ||
            fieldsArray[2] == fieldsArray[5] && fieldsArray[5] == fieldsArray[8] && fieldsArray[8] == 2  ||
            fieldsArray[0] == fieldsArray[4] && fieldsArray[4] == fieldsArray[8] && fieldsArray[8] == 2  ||
            fieldsArray[2] == fieldsArray[4] && fieldsArray[4] == fieldsArray[6] && fieldsArray[6] == 2
        ) {
            replaceWith<CircleWon>()
        }
    }
}

class CrossWon : View() {
    override val root = hbox {
        label {
            text = "Crosses won!"
            font = Font.font(54.0)
        }
    }
}

class CircleWon : View() {
    override val root = hbox {
        label {
            text = "Circles won!"
            font = Font.font(54.0)
        }
    }
}
