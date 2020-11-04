package homework.hw7.task1.messages

class TurnServerMessage(command: String) : TextMessage(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "turn"
        const val requiredArgumentsNumber = 2

        fun compose(playerId: Int, position: Int): String {
            return "turn $playerId $position"
        }
    }

    val playerId = mustBePlayerId(arguments[0])
    val position = mustBePosition(arguments[1])
}
