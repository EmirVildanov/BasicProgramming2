package homework.hw5.task1

import java.util.Stack

class Vertex {
    val children = mutableMapOf<Char, Vertex>()
    var isWordEnd = false
    var howManyStartWithPrefix: Int = 0

    fun getWords(): Stack<String> {
        val result = Stack<String>()
        children.forEach {
            if (it.value.isWordEnd) {
                result.push("${it.key}")
            }
            for (word in it.value.getWords()) {
                result.push("${it.key}$word")
            }
        }
        return result
    }

    fun removeChildren() {
        if (children.isEmpty()) {
            return
        }
        children.clear()
    }
}
