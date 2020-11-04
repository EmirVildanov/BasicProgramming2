package homework.hw7.task1.models

import homework.hw7.task1.bot.Bot
import homework.hw7.task1.bot.EasyBot
import homework.hw7.task1.bot.HardBot
import homework.hw7.task1.enums.BotDifficulty
import javafx.beans.property.SimpleIntegerProperty

class GameWithBotModel(
    playerId: Int,
    private val botId: Int,
    botType: BotDifficulty
) : GameModel() {
    private val bot: Bot = when (botType) {
        BotDifficulty.EASY -> EasyBot(field, playerId, botId)
        BotDifficulty.HARD -> HardBot(field, playerId, botId)
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
