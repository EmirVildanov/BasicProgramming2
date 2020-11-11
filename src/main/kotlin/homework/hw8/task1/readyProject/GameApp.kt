package homework.hw7.task1

import homework.hw8.task1.readyProject.stylesheets.MainStylesheet
import homework.hw8.task1.readyProject.stylesheets.MainStylesheet.Companion.BUTTON_WIDTH
import homework.hw8.task1.readyProject.views.MainMenuView
import javafx.stage.Stage
import tornadofx.App

class GameApp : App(MainMenuView::class, MainStylesheet::class) {

    companion object {
        const val CELLS_NUMBER = 3
        const val FIELD_SIZE = CELLS_NUMBER * CELLS_NUMBER
        const val APP_HEIGHT = 400.0
        const val APP_WIDTH = (BUTTON_WIDTH * CELLS_NUMBER).toDouble()
        const val LAMBDA_FOR_STUPID_CELLS_FIT_FIELD = 13.0
    }

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = APP_WIDTH + LAMBDA_FOR_STUPID_CELLS_FIT_FIELD
        stage.height = APP_HEIGHT
        stage.isResizable = false
    }
}
