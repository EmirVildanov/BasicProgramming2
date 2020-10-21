package homework.hw4.task2

class Operator(
    private val operandString: String,
    private val leftChild: Node,
    private val rightChild: Node
) : Node {
    override fun calculateValue(): Double {
        val leftChildValue = leftChild.calculateValue()
        val rightChildValue = rightChild.calculateValue()
        return when (operandString) {
            "+" -> leftChildValue + rightChildValue
            "-" -> leftChildValue - rightChildValue
            "*" -> leftChildValue * rightChildValue
            "/" -> leftChildValue / rightChildValue
            else -> 0.0
        }
    }

    override fun toString(): String {
        var stringTree = ""
        stringTree += "$operandString "
        stringTree += if (leftChild is Operand) {
            "${leftChild.value} "
        } else {
            "($leftChild) "
        }
        stringTree += if (rightChild is Operand) {
            "${rightChild.value} "
        } else {
            "($rightChild) "
        }
        return stringTree
    }
}
