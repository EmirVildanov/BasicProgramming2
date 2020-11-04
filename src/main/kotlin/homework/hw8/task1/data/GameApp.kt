package homework.hw7.task1

import homework.hw7.task1.stylesheets.MainStylesheet
import homework.hw7.task1.stylesheets.MainStylesheet.Companion.BUTTON_WIDTH
import homework.hw7.task1.views.MainMenuView
import javafx.stage.Stage
import tornadofx.App

class GameApp : App(MainMenuView::class, MainStylesheet::class) {

    companion object {
        const val CELLS_NUMBER = 3
        const val FIELD_SIZE = CELLS_NUMBER * CELLS_NUMBER
        const val APP_HEIGHT = 400.0
        const val APP_WIDTH = (BUTTON_WIDTH * CELLS_NUMBER).toDouble()
    }

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = APP_WIDTH + 13.0
        stage.height = APP_HEIGHT
        stage.isResizable = false
    }
}
