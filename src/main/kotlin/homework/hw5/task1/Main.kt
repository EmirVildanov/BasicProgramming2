package homework.hw5.task1

import java.io.File
import java.io.InputStream

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun main() {
    val trie = Trie()
    trie.add("hi")
    trie.add("him")
    println(trie.contains("hi"))
    trie.remove("hi")
    println(trie.contains("hi"))
    println(trie.size())
    println(trie.howManyStartWithPrefix("hi"))
    val outputStream = File("src/homework.hw5.task1.main/kotlin/hwFifth/taskOne/testWrite").outputStream()
    val inputStream: InputStream = File("src/homework.hw5.task1.main/kotlin/hwFifth/taskOne/testRead").inputStream()
    trie.serialize(outputStream)
    trie.deserialize(inputStream)

    println(trie.contains("m"))
    println(trie.contains("mias"))

    inputStream.close()
    outputStream.close()
}
