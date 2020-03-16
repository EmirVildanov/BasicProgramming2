import java.io.BufferedReader
import java.io.File

class TreeReader(private val file: File) {
    private var root: Node = Node()
    var value = 0
        get() = field

    class Node() {
        var operandValue: Int? = null
        var operationValue: Char? = null
        var leftChild: Node? = null
        var rightChild: Node? = null

        private fun isNumber(string: String): Boolean {
            for (element in string) {
                if (!element.isDigit())
                    return false
            }
            return true
        }

        fun calculateValue(): Int {
            var firstValue = 0
            var secondValue = 0
            if (leftChild!!.operandValue != null) {
                firstValue = leftChild!!.operandValue!!
            } else {
                firstValue = leftChild!!.calculateValue()
            }
            if (rightChild!!.operandValue != null) {
                secondValue = rightChild!!.operandValue!!
            } else {
                secondValue = rightChild!!.calculateValue()
            }
            when (operationValue) {
                '+' -> return firstValue + secondValue
                '-' -> return firstValue - secondValue
                '*' -> return firstValue * secondValue
                '/' -> return firstValue / secondValue
            }
            return 0
        }

        fun splitUpTree(treeString: String) {
            val operation = treeString[1]
            operationValue = operation
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
            if (leftChild!!.operandValue != null) {
                stringTree += "${leftChild!!.operandValue} "
            } else {
                stringTree += "(" + leftChild!!.getTreeView() + ") "
            }
            if (rightChild!!.operandValue != null) {
                stringTree += "${rightChild!!.operandValue} "
            } else {
                stringTree += "(" + rightChild!!.getTreeView() + ") "
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