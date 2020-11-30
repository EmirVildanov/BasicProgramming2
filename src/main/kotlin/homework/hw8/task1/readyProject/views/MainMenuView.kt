package homework.hw8.task1.readyProject.views

import homework.hw8.task1.readyProject.stylesheets.MainStylesheet
import homework.hw8.task1.readyProject.controllers.LobbyController
import javafx.scene.control.ToggleGroup
import tornadofx.View
import tornadofx.vbox
import tornadofx.button
import tornadofx.addClass
import tornadofx.action
import tornadofx.Scope
import tornadofx.find

class MainMenuView : View() {
    private val playerGroup = ToggleGroup()
    private val botGroup = ToggleGroup()

    private var selectedPlayerId = 0
        get() = 1 - field

    override val root = vbox {
        button("Multiplayer") {
            addClass(MainStylesheet.mainMenuButton)
            action {
                startGame()
            }
        }
    }

    private fun startGame() {
        val gameScope = Scope()
        val controller = LobbyController()
        setInScope(controller, gameScope)
        replaceWith(find(LobbyView::class, gameScope))
    }
}
