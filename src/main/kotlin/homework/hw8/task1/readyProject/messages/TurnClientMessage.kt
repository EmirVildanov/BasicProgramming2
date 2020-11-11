package homework.hw7.task1.messages

class TurnClientMessage(command: String) : TextMessage(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "turn"
        const val requiredArgumentsNumber = 1

        fun compose(position: Int): String {
            return "turn $position"
        }
    }

    val position = mustBePosition(arguments[0])
}
