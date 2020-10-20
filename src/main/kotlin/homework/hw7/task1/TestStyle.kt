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
        val fieldCell by cssclass()

        const val CELL_SIZE = 150

        const val MENU__FONT_SIZE = 2.5
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

        fieldCell {
            minHeight = CELL_SIZE.px
            minWidth = CELL_SIZE.px
            borderColor += box(
                top = Color.GREY,
                right = Color.GREY,
                bottom = Color.GREY,
                left = Color.GREY
            )
            backgroundColor += c("white")
        }
    }
}
