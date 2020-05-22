package tests.test2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue
import tests.test2.views.Tile

internal class GameTest {
    @Test
    fun shouldCheckWinIfAllTilesAreDisabled() {
        Game.tilesArray = mutableListOf(Tile(0, 1))
        Game.tilesArray.forEach { it.isDisabledProperty.set(true) }
        assertTrue(Game().checkWin())
    }
}
