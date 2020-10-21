package homework.hw7.task1.models

import homework.hw7.task1.bot.Bot
import homework.hw7.task1.bot.EasyBot
import homework.hw7.task1.bot.HardBot
import javafx.beans.property.SimpleIntegerProperty

class GameWithBotModel(playerId: Int, private val botId: Int, botType: String) : LocalGameModel() {
    private val bot: Bot = when (botType) {
        "easy" -> EasyBot(field, playerId, botId)
        "hard" -> HardBot(field, playerId, botId)
        else -> EasyBot(field, playerId, botId)
    }

    override fun onTurnStart(playerId: Int) {
        super.onTurnStart(playerId)
        if (activePlayer == botId) {
            waiting = true
            makeTurn(botId, bot.makeTurn())
        } else {
            waiting = false
        }
    }

    private fun makeTurn(playerId: Int, cell: SimpleIntegerProperty) {
        val position = field.indexOf(cell)
        assert(position != -1)
        makeTurn(playerId, position)
    }
}
