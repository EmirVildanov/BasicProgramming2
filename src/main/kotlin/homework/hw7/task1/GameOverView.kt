package homework.hw7.task1

import javafx.scene.text.Font
import tornadofx.*

class GameOverView(gameOverString: String) : View() {
    override val root = vbox {
        addClass(TestStyle.gameMenu)
        label {
            text = gameOverString
            font = Font.font(WORDS_FONT)
        }
        button {
            text = "restart"
            action {
                close()
                find<StartMenu>().openWindow()
            }
        }
    }
}
