package homework.hw5.task1

import java.io.InputStream
import java.io.OutputStream

const val ALPHABET_SIZE = 26
const val FIRST_VERTEX_INDEX = 0

class Trie : Serializable {

    private var vertices = mutableListOf<Vertex>()
    private var words = mutableListOf<String>()

    init {
        vertices.add(Vertex())
    }

    class Vertex {
        val next: MutableList<Vertex?> = MutableList(ALPHABET_SIZE) { null }
        var isLeaf = false

        fun countLeafChildrenNumber(vertices: MutableList<Vertex>): Int {
            return if (this.hasChildren()) {
                var answer = 0
                this.next.forEach {
                    if (it != null && it.isLeaf) {
                        answer += 1 + it.countLeafChildrenNumber(vertices)
                    } else if (it != null) {
                        answer += it.countLeafChildrenNumber(vertices)
                    }
                }
                answer
            } else { 0 }
        }

        fun hasChildren(): Boolean {
            next.forEach {
                if (it != null) {
                    return true
                }
            }
            return false
        }
    }

    fun size(): Int {
        return vertices.size
    }

    fun add(element: String): Boolean {
        if (this.contains(element)) {
            return false
        }
        words.add(element)
        var currentVertex = vertices[FIRST_VERTEX_INDEX]
        for (char in element) {
            val currentCharIndex = char.toInt() - 'a'.toInt()
            if (currentVertex.next[currentCharIndex] == null) {
                val newVertex = Vertex()
                vertices.add(newVertex)
                currentVertex.next[currentCharIndex] = newVertex
            }
            currentVertex = currentVertex.next[currentCharIndex]!!
        }
        currentVertex.isLeaf = true
        return true
    }

    fun contains(element: String): Boolean {
        return words.contains(element)
    }

    fun remove(element: String): Boolean {
        if (!contains(element)) {
            return false
        }
        words.remove(element)
        var currentVertex = vertices[FIRST_VERTEX_INDEX]
        val verticesArray = mutableListOf<Vertex>()
        for (char in element) {
            verticesArray.add(currentVertex)
            val currentCharIndex = char.toInt() - 'a'.toInt()
            currentVertex = currentVertex.next[currentCharIndex]!!
        }
        verticesArray.add(currentVertex)
        for (i in 0 until verticesArray.size - 1) {
            if (i + 1 == verticesArray.size - 1 && verticesArray[i + 1].isLeaf) {
                if (verticesArray[i + 1].hasChildren()) {
                    verticesArray[i + 1].isLeaf = false
                } else {
                    vertices.remove(verticesArray[i + 1])
                }
            } else if (verticesArray[i + 1].countLeafChildrenNumber(vertices) == 1) {
                verticesArray[i].next.remove(verticesArray[i + 1])
                for (j in i + 1 until verticesArray.size) {
                    vertices.remove(verticesArray[j])
                }
            }
        }
        return true
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        var currentVertex = vertices[FIRST_VERTEX_INDEX]
        var appropriateVerticesNumber = 0
        for (char in prefix) {
            val currentCharIndex = char.toInt() - 'a'.toInt()
            if (currentVertex.next[currentCharIndex] == null) {
                return appropriateVerticesNumber
            }
            currentVertex = currentVertex.next[currentCharIndex]!!
        }
        if (currentVertex.isLeaf) {
            appropriateVerticesNumber = 1
        }
        appropriateVerticesNumber += currentVertex.countLeafChildrenNumber(vertices)
        return appropriateVerticesNumber
    }

    private fun clear() {
        val temporaryArray = mutableListOf<String>()
        words.forEach { temporaryArray.add(it) }
        temporaryArray.forEach { this.remove(it) }
        words.clear()
    }

    override fun serialize(out: OutputStream) {
        words.forEach { out.write(it.toByteArray() + ' '.toByte()) }
    }

    override fun deserialize(input: InputStream) {
        val inputString = input.bufferedReader().use { it.readText() }
        val elements = inputString.split(" ")
        this.clear()
        elements.forEach { this.add(it) }
    }
}
