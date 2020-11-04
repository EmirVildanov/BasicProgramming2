package homework.hw8.task1.data.views

import homework.hw7.task1.controllers.GameController
import homework.hw7.task1.views.GameFieldView
import homework.hw7.task1.views.MainMenuView
import homework.hw8.task1.data.controllers.LobbyController
import tornadofx.*

class LobbyView : View() {
    private val controller: LobbyController by inject()
    override val root = vbox {
        label(controller.statusText)
        button("Join") {
            isDisable = !controller.canJoinGame
            controller.canJoinGameProperty.onChange {
                isDisable = !it
            }
            action {
                startGame()
            }
        }
        button("Back") {
            action {
                controller.disconnect()
                replaceWith<MainMenuView>()
            }
        }
    }

    override fun onDock() {
        super.onDock()
        controller.connect()
    }

    private fun startGame() {
        val gameScope = Scope()
        val game = GameController(controller.model)
        setInScope(game, gameScope)
        replaceWith(find(GameFieldView::class, gameScope))
    }
}
