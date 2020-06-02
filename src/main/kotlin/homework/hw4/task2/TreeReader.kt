package homework.hw4.task2

import java.io.BufferedReader
import java.io.File
import java.lang.IllegalArgumentException

class TreeReader(file: File) {
    lateinit var root: Node
    var value = 0

    class Node() {

        private var operandValue: Int? = null
        private var operationValue: Char? = null
        var leftChild: Node? = null
        var rightChild: Node? = null

        constructor(leftChild: Node?, rightChild: Node?, operation: Char) :this() {
            this.leftChild = leftChild
            this.rightChild = rightChild
            this.operationValue = operation
        }

        constructor(operand: Int) : this() {
            operandValue = operand
        }

        fun calculateValue(): Int {
            val firstValue: Int
            if (leftChild?.operandValue != null) {
                firstValue = leftChild?.operandValue ?: throw IllegalArgumentException("Null leftChild")
            } else {
                firstValue = leftChild?.calculateValue() ?: throw IllegalArgumentException("Null leftChild")
            }
            val secondValue: Int
            if (rightChild?.operandValue != null) {
                secondValue = rightChild?.operandValue ?: throw IllegalArgumentException("Null rightChild")
            } else {
                secondValue = rightChild?.calculateValue() ?: throw IllegalArgumentException("Null rightChild")
            }
            return when (operationValue) {
                '+' -> firstValue + secondValue
                '-' -> firstValue - secondValue
                '*' -> firstValue * secondValue
                '/' -> firstValue / secondValue
                else -> 0
            }
        }

        private fun getTreeView(): String {
            var stringTree = ""
            stringTree += "$operationValue "
            stringTree += if (leftChild?.operandValue != null) {
                "${leftChild?.operandValue} "
            } else {
                "(" + leftChild?.getTreeView() + ") "
            }
            stringTree += if (rightChild?.operandValue != null) {
                "${rightChild?.operandValue} "
            } else {
                "(" + rightChild?.getTreeView() + ") "
            }
            return stringTree
        }

        fun print() {
            println(getTreeView())
        }
    }

    private fun isNumber(string: String): Boolean {
        for (element in string) {
            if (!element.isDigit()) {
                return false
            }
        }
        return true
    }

    //calls only if treeString contasins children
    fun splitUpTree(treeString: String): Node {
//        var left: Node?
//        var right: Node?
//        val copyString = treeString.removePrefix("(").removeSuffix(")")
//        val operation = copyString[0]
//        var index = 2
//        if (copyString[index].isDigit()) {
//            while (index < copyString.length && copyString[index].isDigit()) {
//                ++index
//            }
//            left = Node(copyString.substring(2, index).toInt())
//            ++index
//            if (copyString[index].isDigit()) {
//                right = Node(copyString.substring(index, copyString.length).toInt())
//            } else {
//                right = splitUpTree(copyString.substring(index, copyString.length))
//            }
//            return Node(left, right, operation)
//        } else {
//            ++index
//            val braces = arrayOf(1, 0)
//            while (braces[0] != braces[1]) {
//                if (copyString[index] == '(') {
//                    ++braces[0]
//                } else if (copyString[index] == ')') {
//                    ++braces[1]
//                }
//                index++
//            }
//            left = splitUpTree(copyString.substring(2, index))
//            ++index
//            if (copyString[index].isDigit()) {
//                right = Node(copyString.substring(index, copyString.length).toInt())
//            } else {
//                right = splitUpTree(copyString.substring(index, copyString.length))
//            }
//            return Node(left, right, operation)
//        }

        val regex = "(?<!^..)(\\([*-\\/\\+0-9 ()]+\\))(?=$)" +
                "|((?<=^..)(\\([*-\\/\\+0-9 ()]+\\))(?= (\\d$" +
                "|\\([+-\\/*])))|\\d+" +
                "|^[+-\\/*]"
        val treePattern = Regex(regex)
        val listOfOperators = treePattern.findAll(
            treeString.removePrefix("(").removeSuffix(")")
        ).toList().map { it.value }
        val leftChild: Node?
        val rightChild: Node?
        if (listOfOperators[0] == "+" || listOfOperators[0] == "-"
            || listOfOperators[0] == "*" || listOfOperators[0] == "/" ) {
            if (isNumber(listOfOperators[1])) {
                leftChild = Node(listOfOperators[1].toInt())
            } else {
                leftChild = splitUpTree(listOfOperators[1])
            }
            if (isNumber(listOfOperators[2])) {
                rightChild = Node(listOfOperators[2].toInt())
            } else {
                rightChild = splitUpTree(listOfOperators[2])
            }
            return Node(leftChild, rightChild, listOfOperators[0][0])
        } else {
            return Node(listOfOperators[0].toInt())
        }
    }

    init {
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        bufferedReader.close()
        if (isNumber(inputString)) {
            root = Node(inputString.toInt())
        } else {
            root = splitUpTree(inputString)
            value = root.calculateValue()
        }
    }

    fun print() {
        print("The tree is : ")
        root.print()
    }
}
