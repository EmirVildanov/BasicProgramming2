package homework.hw4.task2

import java.io.BufferedReader
import java.io.File

class TreeReader(file: File) {
    var root: Node
    var value = 0

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
        if (isOperation(listOfOperators[0])) {
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
