package homework.hw8.task1.readyProject.views

import homework.hw7.task1.controllers.GameController
import homework.hw8.task1.readyProject.controllers.LobbyController
import io.ktor.util.KtorExperimentalAPI
import tornadofx.View
import tornadofx.vbox
import tornadofx.label
import tornadofx.button
import tornadofx.onChange
import tornadofx.action
import tornadofx.Scope
import tornadofx.find

class LobbyView : View() {
    private val controller: LobbyController by inject()
    @KtorExperimentalAPI
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

    @KtorExperimentalAPI
    override fun onDock() {
        super.onDock()
        controller.connect()
    }

    @KtorExperimentalAPI
    private fun startGame() {
        val gameScope = Scope()
        val game = GameController(controller.model)
        setInScope(game, gameScope)
        replaceWith(find(GameFieldView::class, gameScope))
    }
}
