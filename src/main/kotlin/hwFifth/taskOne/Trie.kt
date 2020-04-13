package hwFifth.taskOne

import java.io.InputStream
import java.io.OutputStream

const val alphabetSize = 26
const val firstVertexIndex = 0

class Trie : Serializable {

    var vertices = mutableListOf<Vertex>()
    var words = mutableListOf<String>()

    init {
        vertices.add(Vertex())
    }

    class Vertex() {
        val next: MutableList<Vertex?> = MutableList(alphabetSize) { null }
        var isLeaf = false

        fun countLeafChildrenNumber(vertices: MutableList<Vertex>): Int {
            if (this.hasChildren()) {
                var answer = 0
                this.next.forEach {
                    if (it != null) {
                        answer += if (it.isLeaf) {
                            1 + it.countLeafChildrenNumber(vertices)
                        } else {
                            it.countLeafChildrenNumber(vertices)
                        }
                    }
                }
                return answer
            }
            return 0
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
        var currentVertex = vertices[firstVertexIndex]
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
        var currentVertex = vertices[firstVertexIndex]
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
                break
            } else if (verticesArray[i + 1].countLeafChildrenNumber(vertices) == 1) {
                verticesArray[i].next.remove(verticesArray[i + 1])
                for (j in i + 1 until verticesArray.size) {
                    vertices.remove(verticesArray[j])
                }
                break
            }
        }
        return true
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        var currentVertex = vertices[firstVertexIndex]
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
        appropriateVerticesNumber += currentVertex.countLeafChildrenNumber(vertices)//kostyl
        val adsf = currentVertex.isLeaf
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