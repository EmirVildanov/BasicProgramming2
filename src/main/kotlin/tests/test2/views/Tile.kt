package tests.test2.views

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tests.test2.Game
import tornadofx.Fragment
import tornadofx.hbox
import tornadofx.button
import tornadofx.paddingAll
import tornadofx.runLater
import tornadofx.action
import tornadofx.seconds

class Tile(private val index: Int, var number: Int) : Fragment() {
    val buttonText = SimpleStringProperty(" ")
    var isDisabledProperty = SimpleBooleanProperty(false)
    private val currentTile = this
    companion object Constants {
        const val PAD_ALL = 15
        val DELAY = 0.5.seconds
    }

    override val root = hbox {
        paddingAll = PAD_ALL
        button {
            paddingAll = PAD_ALL
            textProperty().bind(buttonText)
            disableProperty().bind(isDisabledProperty)
            action {
                if (!Game.firstTileClicked) {
                    buttonText.set(number.toString())
                    Game.firstTileClicked = true
                    Game.firstClickedButtonIndex = index
                } else {
                    val firstClickedButton = Game.tilesArray[Game.firstClickedButtonIndex]
                    if (currentTile != firstClickedButton && number == firstClickedButton.number) {
                        firstClickedButton.buttonText.set(firstClickedButton.number.toString())
                        buttonText.set(number.toString())
                        disable()
                        firstClickedButton.disable()
                        Game.firstTileClicked = false
                        Game.firstClickedButtonIndex = -1
                    } else {
                        buttonText.set(number.toString())
                        runLater(DELAY) {
                            buttonText.set(" ")
                            firstClickedButton.buttonText.set(" ")
                            Game.firstTileClicked = false
                            Game.firstClickedButtonIndex = -1
                        }
                    }
                }
            }
        }
    }

    fun disable() {
        isDisabledProperty.set(true)
        if (Game().checkWin()) {
            runLater(DELAY) {
                close()
                find<WinView>().openWindow()
            }
        }
    }
}
