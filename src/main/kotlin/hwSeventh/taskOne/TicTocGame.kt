package hwSeventh.taskOne

import javafx.beans.property.SimpleStringProperty
import javafx.scene.text.Font
import tornadofx.*

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

class MyApp : App(MyView::class)

const val SIDE = 150.0
var crossTurn = true

class MyView : View() {
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
    val fieldsArray = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
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

class Button : View() {
    override val root = hbox {
        button {
            rectangle {
                x = this.x
                y = this.y
                fill = c("FFFFFF")
                width = SIDE
                height = SIDE
            }
            action {
                if (crossTurn) {
                    polyline(0.0, 0.0, SIDE, SIDE, SIDE / 2, SIDE / 2, SIDE, 0.0, 0.0, SIDE)
                } else {
                    circle {
                        stroke = c("000000")
                        fill = null
                        centerX = SIDE / 2
                        centerY = SIDE / 2
                        radius = SIDE / 2
                    }
                }
                crossTurn = !crossTurn
            }
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
