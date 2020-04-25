package homework.hw4.task2

import java.io.BufferedReader
import java.io.File

class TreeReader(file: File) {
    var root: Node = Node()
    var value = 0

    class Node {
        private var operandValue: Int? = null
        private var operationValue: Char? = null
        var leftChild: Node? = null
        var rightChild: Node? = null

        private fun isNumber(string: String): Boolean {
            for (element in string) {
                if (!element.isDigit()) {
                    return false
                }
            }
            return true
        }

        fun calculateValue(): Int {
            val firstValue: Int = if (leftChild!!.operandValue != null) {
                leftChild!!.operandValue!!
            } else {
                leftChild!!.calculateValue()
            }
            val secondValue: Int = if (rightChild!!.operandValue != null) {
                rightChild!!.operandValue!!
            } else {
                rightChild!!.calculateValue()
            }
            return when (operationValue) {
                '+' -> firstValue + secondValue
                '-' -> firstValue - secondValue
                '*' -> firstValue * secondValue
                '/' -> firstValue / secondValue
                else -> 0
            }
        }

        fun splitUpTree(treeString: String) {
            val operation = treeString[1]
            operationValue = operation
            val bracesPattern = "[(].+[)]".toRegex()
            val treePattern = "[(][-+*/]\\s+(\\d+|[(].+[)])\\s+(\\d+|[(].+[)])[)]".toRegex()
            val firstPart = treePattern.find(treeString)?.groupValues?.get(1)
            val secondPart = treePattern.find(treeString)?.groupValues?.get(2)
            if (firstPart != null) {
                leftChild = Node()
                if (isNumber(firstPart)) {
                    leftChild!!.operandValue = firstPart.toInt()
                } else {
                    leftChild!!.splitUpTree(firstPart)
                }
            }
            if (secondPart != null) {
                rightChild = Node()
                if (isNumber(secondPart)) {
                    rightChild!!.operandValue = secondPart.toInt()
                } else {
                    rightChild!!.splitUpTree(secondPart)
                }
            }
        }

        private fun getTreeView(): String {
            var stringTree = ""
            stringTree += "$operationValue "
            stringTree += if (leftChild!!.operandValue != null) {
                "${leftChild!!.operandValue} "
            } else {
                "(" + leftChild!!.getTreeView() + ") "
            }
            stringTree += if (rightChild!!.operandValue != null) {
                "${rightChild!!.operandValue} "
            } else {
                "(" + rightChild!!.getTreeView() + ") "
            }
            return stringTree
        }

        fun print() {
            println(getTreeView())
        }
    }

    init {
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        bufferedReader.close()
        root.splitUpTree(inputString)
        value = root.calculateValue()
    }

    fun print() {
        print("The tree is : ")
        root.print()
    }
}
