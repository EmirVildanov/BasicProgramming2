package tests.test2.views

import tests.test2.Game
import tests.test2.Tile
import tornadofx.View
import tornadofx.hbox
import tornadofx.vbox

class GameView : View() {
    override val root = vbox {
        Game().fillRandomNumberArray()
        for (i in 0 until Game.fieldSide) {
            hbox {
                var currentIndex: Int
                for (j in 0 until Game.fieldSide) {
                    currentIndex = i * Game.fieldSide + j
                    val currentTile = Tile(currentIndex, Game.numbersArray[currentIndex])
                    add(currentTile)
                    Game.tilesArray.add(currentTile)
                }
            }
        }
    }
}
