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
        val leftChild: Node?
        val rightChild: Node?
        return if (isOperation(listOfOperators[0])) {
            leftChild = if (isNumber(listOfOperators[1])) {
                Operand(listOfOperators[1].toInt())
            } else {
                splitUpTree(listOfOperators[1])
            }
            rightChild = if (isNumber(listOfOperators[2])) {
                Operand(listOfOperators[2].toInt())
            } else {
                splitUpTree(listOfOperators[2])
            }
            Operator(listOfOperators[0], leftChild, rightChild)
        } else {
            Operand(listOfOperators[0].toInt())
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

    private fun isOperation(char: String): Boolean {
        return when (char) {
            "+" -> true
            "-" -> true
            "/" -> true
            "*" -> true
            else -> false
        }
    }

    fun print() {
        print("The tree is : ")
        println(root.toString())
    }
}
