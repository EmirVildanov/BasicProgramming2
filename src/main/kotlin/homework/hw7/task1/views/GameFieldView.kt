package homework.hw7.task1.views

import homework.hw7.task1.GameApp
import homework.hw7.task1.controllers.GameController
import homework.hw7.task1.stylesheets.MainStylesheet
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*

class GameFieldView : View() {
    private val controller: GameController by inject()
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

    override fun onDock() {
        controller.onDraw {
            showEndGameMessage("It's a Draw")
        }
        controller.onVictory { player ->
            showEndGameMessage("${controller.getPlayerFigure(player).toUpperCase()}'s win")
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
