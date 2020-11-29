package homework.hw4.task2

import java.io.BufferedReader
import java.io.File

class TreeReader(file: File) {
    var root: Node
    var value = 0.0

    init {
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        bufferedReader.close()
        if (isNumber(inputString)) {
            root = Operand(inputString.toInt())
        } else {
            root = splitUpTree(inputString)
            value = root.calculateValue()
        }
    }

    private fun splitUpTree(treeString: String): Node {
        val treePattern = ("(?<!^..)(\\([*-\\/\\\\+0-9 ()]+\\))(?=\$)" +
                "|((?<=^..)(\\([*-\\/\\\\+0-9 ()]+\\))(?= (\\d\$|\\([+-\\/*])))" +
                "|\\d+" +
                "|^[+-\\/*]").toRegex()
        val listOfOperators = treePattern.findAll(
            treeString.removePrefix("(").removeSuffix(")")
        ).toList().map { it.value }
        val firstOperator = listOfOperators[0]
        val secondOperator = listOfOperators[1]
        val thirdOperator = listOfOperators[2]
        return if (isNumber(firstOperator)) {
            Operand(firstOperator.toInt())
        } else {
            val leftChild = if (isNumber(secondOperator)) {
                Operand(secondOperator.toInt())
            } else {
                splitUpTree(secondOperator)
            }
            val rightChild = if (isNumber(thirdOperator)) {
                Operand(thirdOperator.toInt())
            } else {
                splitUpTree(thirdOperator)
            }
            Operator(firstOperator, leftChild, rightChild)
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

    fun print() {
        print("The tree is : ")
        println(root.toString())
    }
}
