package tests.test2.views

import javafx.scene.text.Font
import tests.test2.Game
import tornadofx.View
import tornadofx.vbox
import tornadofx.label
import tornadofx.paddingAll
import tornadofx.button
import tornadofx.paddingHorizontal
import tornadofx.action

class WinView : View() {
    companion object Constants {
        const val FONT = 30.0
        const val PAD_ALL = 40
        const val PAD_HORIZONTAL = 200
    }
    override val root = vbox {
        label {
            text = "Congradulations!"
            font = Font(FONT)
        }
        paddingAll = PAD_ALL
        button {
            paddingHorizontal = PAD_HORIZONTAL
            text = "restart"
            action {
                Game().restart()
                close()
                find<GameView>().openWindow()
            }
        }
    }
}
