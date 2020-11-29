package homework.hw5.task1

import java.io.InputStream
import java.io.OutputStream
import java.io.Serializable
import java.util.Scanner

class Trie : Serializable {

    private val root = Vertex()
    var size = 0
        private set

    fun add(element: String): Boolean {
        if (contains(element)) {
            return false
        }
        var currentNode: Vertex = root
        for (symbol in element) {
            currentNode = currentNode.children[symbol] ?: run {
                val newNode = Vertex()
                currentNode.children[symbol] = newNode
                newNode
            }
            currentNode.howManyStartWithPrefix++
        }
        size++
        currentNode.isWordEnd = true
        return true
    }

    fun remove(element: String): Boolean {
        if (!contains(element)) {
            return false
        }
        var currentNode = root
        var nextNode: Vertex? = root
        for (symbol in element) {
            nextNode = currentNode.children[symbol]
            nextNode?.run { howManyStartWithPrefix-- }
            if (nextNode?.howManyStartWithPrefix == 0) {
                currentNode.children[symbol]?.removeChildren()
                currentNode.children.remove(symbol)
                break
            } else {
                nextNode?.let { currentNode = it }
            }
        }
        nextNode?.let { it.isWordEnd = false }
        size--
        return true
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        var currentNode: Vertex? = root
        for (symbol in prefix) {
            currentNode = currentNode?.children?.get(symbol)
        }
        return currentNode?.howManyStartWithPrefix ?: 0
    }

    fun contains(element: String): Boolean {
        if (element.isEmpty()) {
            return root.isWordEnd
        }
        var currentNode: Vertex? = root
        for (symbol in element) {
            currentNode = currentNode?.children?.get(symbol)
        }
        return currentNode?.isWordEnd ?: false
    }
    fun writeObject(output: OutputStream) {
        val words = root.getWords()
        if (root.isWordEnd) {
            words.push("")
        }
        output.write((words.joinToString(" ") { it }).toByteArray())
        output.close()
    }

    fun readObject(input: InputStream) {
        val inputString = input.bufferedReader().readLine() ?: ""
        val scanner = Scanner(inputString)
        root.removeChildren()
        while (scanner.hasNext()) {
            val currentWords = scanner.nextLine().split(" ")
            currentWords.forEach {
                add(it)
            }
        }
        input.close()
    }
}
