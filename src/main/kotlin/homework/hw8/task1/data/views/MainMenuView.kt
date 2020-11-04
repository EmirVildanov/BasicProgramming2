package homework.hw7.task1.views

import homework.hw7.task1.stylesheets.MainStylesheet
import homework.hw8.task1.data.controllers.LobbyController
import homework.hw8.task1.data.views.LobbyView
import javafx.scene.control.ToggleGroup
import tornadofx.*

class MainMenuView : View() {
    private val playerGroup = ToggleGroup()
    private val botGroup = ToggleGroup()

    private var selectedPlayerId = 0
    private val selectedBotId: Int
        get() = 1 - selectedPlayerId
    private var selectedBotType = "easy"

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
