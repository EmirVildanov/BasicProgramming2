import java.io.InputStream
import java.io.OutputStream
import java.io.Serializable

const val alphabetSize = 26

class Trie : Serializable{

    var vertices = mutableListOf<Vertex>()
    var size = 1
        get() = field

    init {
        vertices.add(Vertex())
    }

    class Vertex() {
        val next = Array(alphabetSize) {-1}
        var isLeaf = false
    }

    fun add(element: String): Boolean {
        if (this.contains(element)) {
            return false
        }
        var currentVertexIndex = 0
        for (char in element) {
            val currentCharIndex = char.toInt() - 'a'.toInt()
            if (vertices[currentVertexIndex].next[currentCharIndex] == -1) {
                vertices.add(Vertex())
                vertices[currentVertexIndex].next[currentCharIndex] = size++
            }
            currentVertexIndex = vertices[currentVertexIndex].next[currentCharIndex]
        }
        vertices[currentVertexIndex].isLeaf = true
        return true
    }

    fun contains(element: String): Boolean {
        var currentVertexIndex = 0
        for (char in element) {
            val currentCharIndex = char.toInt() - 'a'.toInt()
            if (vertices[currentVertexIndex].next[currentCharIndex] == -1) {
                return false
            }
            currentVertexIndex = vertices[currentVertexIndex].next[currentCharIndex]
        }
        return vertices[currentVertexIndex].isLeaf
    }

    fun remove(element: String): Boolean {
        if (!contains(element)) {
            return false
        }
        var currentVertexIndex = 0
        for (char in element) {
            val currentCharIndex = char.toInt() - 'a'.toInt()
            if (vertices[currentVertexIndex].next[currentCharIndex] == -1) {
                return false
            }
            currentVertexIndex = vertices[currentVertexIndex].next[currentCharIndex]
        }
        return true
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        TODO()
    }

    fun serialize(out: OutputStream) {

    }

    fun deserialize(input: InputStream) {

    }
}