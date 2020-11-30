package homework.hw4.task2

class Operand(val value: Int) : Node {
    override fun calculateValue() = value.toDouble()

    override fun toString(): String {
        return value.toString()
    }
}
