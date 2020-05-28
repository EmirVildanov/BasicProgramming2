package tests.test2.views

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tests.test2.Game
import tornadofx.*

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
            style {
                minWidth = 100.px
                minHeight = 100.px
                fontWeight = FontWeight.EXTRA_LIGHT
                fontSize = 3.em
                borderColor += box(
                    top = Color.GREY,
                    right = Color.GREY,
                    bottom = Color.GREY,
                    left = Color.GREY
                )
                backgroundColor += c("white")
            }
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

    private fun disable() {
        isDisabledProperty.set(true)
        if (Game().checkWin()) {
            runLater(DELAY) {
                close()
                find<WinView>().openWindow()
            }
        }
    }
}
