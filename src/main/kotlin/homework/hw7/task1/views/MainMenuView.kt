package homework.hw7.task1.views

import homework.hw7.task1.controllers.GameController
import homework.hw7.task1.enums.BotDifficulty
import homework.hw7.task1.models.GameWithBotModel
import homework.hw7.task1.stylesheets.MainStylesheet
import javafx.scene.control.ToggleGroup
import tornadofx.View
import tornadofx.button
import tornadofx.addClass
import tornadofx.action
import tornadofx.borderpane
import tornadofx.vbox
import tornadofx.label
import tornadofx.radiobutton
import tornadofx.hbox
import tornadofx.Scope
import tornadofx.find
import tornadofx.top
import tornadofx.bottom

class MainMenuView : View() {
    private val playerGroup = ToggleGroup()
    private val botGroup = ToggleGroup()

    private var selectedPlayerId = 0
    private val selectedBotId: Int
        get() = 1 - selectedPlayerId
    private var selectedBotType = BotDifficulty.EASY

    override val root = borderpane {
        top {
            vbox {
                addClass(MainStylesheet.gameSettingsBody)
                label("Choose your player")
                radiobutton("X", playerGroup) {
                    isSelected = true
                    action {
                        selectedPlayerId = 0
                    }
                }
                radiobutton("O", playerGroup) {
                    action {
                        selectedPlayerId = 1
                    }
                }
                label("Choose difficulty")
                radiobutton("Easy", botGroup) {
                    isSelected = true
                    action {
                        selectedBotType = BotDifficulty.EASY
                    }
                }
                radiobutton("Hard", botGroup) {
                    action {
                        selectedBotType = BotDifficulty.HARD
                    }
                }
            }
        }
        bottom {
            hbox {
                addClass(MainStylesheet.gameSettingsBody)
                button("Play") {
                    addClass(MainStylesheet.gameSettingsButton)
                    action {
                        startGame()
                    }
                }
            }
        }
    }

    private fun startGame() {
        val gameScope = Scope()
        val model = GameWithBotModel(selectedPlayerId, selectedBotId, selectedBotType)
        val game = GameController(model)
        setInScope(game, gameScope)
        replaceWith(find(GameFieldView::class, gameScope))
    }
}
