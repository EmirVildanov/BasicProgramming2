package homework.hw7.task1

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class TestStyle : Stylesheet() {

    companion object {
        val tackyButton by cssclass()

        private val topColor = Color.RED
        private val rightColor = Color.DARKGREEN
        private val leftColor = Color.ORANGE
        private val bottomColor = Color.PURPLE

        val gameMenu by cssclass()
        val centerAlignment by cssclass()
        val cellSelected by cssclass()

        const val MENU__FONT_SIZE = 2.5
        val SELECTED_COLOR = Color.RED
    }

    init {
        tackyButton {
            rotate = 10.deg
            borderColor += box(topColor,rightColor,bottomColor,leftColor)
            fontFamily = "Comic Sans MS"
            fontSize = 20.px
        }

        gameMenu {
            fontSize = MENU__FONT_SIZE.em
            alignment = Pos.CENTER
        }

        centerAlignment {
            alignment = Pos.CENTER
        }

        cellSelected {
            fill = SELECTED_COLOR
        }
    }
}
