package homework.hw8.task1.readyProject.views

import homework.hw7.task1.GameApp
import homework.hw7.task1.controllers.GameController
import homework.hw8.task1.readyProject.stylesheets.MainStylesheet
import io.ktor.util.KtorExperimentalAPI
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.View
import tornadofx.flowpane
import tornadofx.button
import tornadofx.addClass
import tornadofx.action
import tornadofx.onChange
import tornadofx.alert

class GameFieldView : View() {
    private val controller: GameController by inject()
    @KtorExperimentalAPI
    override val root = flowpane {
        for (i in 0 until GameApp.FIELD_SIZE) {
            val currentButton = controller.buttonsData[i]
            button(currentButton.text) {
                addClass(MainStylesheet.gameFieldButton)
                action {
                    controller.makeTurn(i)
                }
                isDisable = currentButton.isDisabled.value
                currentButton.isDisabled.onChange {
                    isDisable = it
                }
            }
        }
    }

    @KtorExperimentalAPI
    override fun onDock() {
        controller.onDraw {
            showEndGameMessage("It's a Draw")
        }
        controller.onVictory { player ->
            showEndGameMessage("${controller.getPlayerFigure(player).cellString.toUpperCase()}'s win")
        }
        controller.onError {
            showEndGameMessage(it)
        }
        controller.startGame()
    }

    private fun showEndGameMessage(text: String) {
        alert(Alert.AlertType.INFORMATION, text, "Return to the main menu", ButtonType.OK, actionFn = {
            replaceWith<MainMenuView>()
        })
        replaceWith<MainMenuView>()
    }
}
