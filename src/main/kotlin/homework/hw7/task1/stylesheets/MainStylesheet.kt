package homework.hw7.task1.stylesheets

import homework.hw7.task1.GameApp.Companion.APP_WIDTH
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.em
import tornadofx.px
import tornadofx.c
import tornadofx.box

class MainStylesheet : Stylesheet() {

    companion object {
        val ticTacToeTitle by cssclass()
        val mainMenuButton by cssclass()

        val gameFieldButton by cssclass()
        val activePlayerLabel by cssclass()

        val gameSettingsBody by cssclass()
        val gameSettingsButton by cssclass()

        const val FONT_SIZE_MEDIUM = 1.5
        const val FONT_SIZE_BIG = 3

        const val BUTTON_WIDTH = 100
        const val BUTTON_HEIGHT = BUTTON_WIDTH
        const val BUTTON_BACKGROUND_COLOR = "white"
        val BUTTON_BORDER_COLOR = Color.GREY
    }

    init {
        ticTacToeTitle {
            fontSize = FONT_SIZE_BIG.em
        }
        mainMenuButton {
            minWidth = APP_WIDTH.px
        }
        activePlayerLabel {
            fontSize = FONT_SIZE_MEDIUM.em
        }
        gameFieldButton {
            minWidth = BUTTON_WIDTH.px
            maxWidth = BUTTON_WIDTH.px
            minHeight = BUTTON_HEIGHT.px
            maxHeight = BUTTON_HEIGHT.px
            fontWeight = FontWeight.EXTRA_LIGHT
            fontSize = FONT_SIZE_BIG.em
            borderColor += box(
                top = BUTTON_BORDER_COLOR,
                right = BUTTON_BORDER_COLOR,
                bottom = BUTTON_BORDER_COLOR,
                left = BUTTON_BORDER_COLOR
            )
            backgroundColor += c(BUTTON_BACKGROUND_COLOR)
        }
        gameSettingsBody {
            alignment = Pos.CENTER
            fontSize = FONT_SIZE_MEDIUM.em
        }
        gameSettingsButton {
            minWidth = (APP_WIDTH / 2).px
        }
    }
}
