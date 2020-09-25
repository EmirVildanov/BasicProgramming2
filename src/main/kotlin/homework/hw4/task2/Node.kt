package homework.hw4.task2

class Node() {

    private var operandValue: Int? = null
    private var operationValue: Char? = null
    var leftChild: Node? = null
        private set
    var rightChild: Node? = null
        private set

    constructor(leftChild: Node?, rightChild: Node?, operation: Char) : this() {
        this.leftChild = leftChild
        this.rightChild = rightChild
        this.operationValue = operation
    }

    constructor(operand: Int) : this() {
        operandValue = operand
    }

    fun calculateValue(): Int {
        val firstValue = if (leftChild?.operandValue != null) {
            leftChild?.operandValue
        } else {
            leftChild?.calculateValue()
        }
        require(firstValue != null) {
            "Null leftChild"
        }
        val secondValue = if (rightChild?.operandValue != null) {
            rightChild?.operandValue
        } else {
            rightChild?.calculateValue()
        }
        require(secondValue != null) {
            "Null leftChild"
        }
        return when (operationValue) {
            '+' -> firstValue + secondValue
            '-' -> firstValue - secondValue
            '*' -> firstValue * secondValue
            '/' -> firstValue / secondValue
            else -> 0
        }
    }
    override fun toString(): String {
        var stringTree = ""
        stringTree += "$operationValue "
        stringTree += if (leftChild?.operandValue != null) {
            "${leftChild?.operandValue} "
        } else {
            "(" + leftChild?.toString() + ") "
        }
        stringTree += if (rightChild?.operandValue != null) {
            "${rightChild?.operandValue} "
        } else {
            "(" + rightChild?.toString() + ") "
        }
        return stringTree
    }
}
