package homework.hw7.task1.messages

import homework.hw7.task1.GameApp.Companion.FIELD_SIZE

open class TextMessage(
    name: String,
    requiredArgumentsNumber: Int,
    command: String
) {
    private val tokens = command.split(" ")
    protected val arguments = tokens.drop(1).mapNotNull { it.toIntOrNull() }

    init {
        if (tokens[0] != name) {
            throw IllegalMessageTypeException()
        }
        if (arguments.size != requiredArgumentsNumber) {
            throw IllegalNumberOfMessageArgumentsException()
        }
    }

    fun mustBePlayerId(integer: Int): Int {
        if (!(0..1).contains(integer)) {
            throw IllegalMessageArgumentSyntax("Must be a playerId, but was $integer")
        }
        return integer
    }

    fun mustBePlayerIdOrNegativeOne(integer: Int): Int {
        if (!(-1..1).contains(integer)) {
            throw IllegalMessageArgumentSyntax("Must be a playerId, but was $integer")
        }
        return integer
    }

    fun mustBePosition(integer: Int): Int {
        if (!(0 until FIELD_SIZE).contains(integer)) {
            throw IllegalMessageArgumentSyntax("Must be a field position, but was $integer")
        }
        return integer
    }

    class IllegalMessageTypeException : IllegalArgumentException()

    class IllegalNumberOfMessageArgumentsException : IllegalArgumentException()

    class IllegalMessageArgumentSyntax(msg: String) : java.lang.IllegalArgumentException(msg)
}
