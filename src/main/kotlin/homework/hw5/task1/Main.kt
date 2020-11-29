package homework.hw5.task1

import java.io.File
import java.io.InputStream

fun main() {
    val resourcesPath = "src/main/resources/homework.hw5.task1/"
    val trie = Trie()
    trie.add("hi")
    trie.add("him")
    println(trie.contains("hi"))
    trie.remove("hi")
    println(trie.contains("hi"))
    println(trie.size)
    println(trie.howManyStartWithPrefix("hi"))
    val outputStream = File(resourcesPath + "testWrite").outputStream()
    val inputStream: InputStream = File(resourcesPath + "testRead").inputStream()
    trie.readObject(inputStream)
    trie.writeObject(outputStream)
    println(trie.contains("m"))
    println(trie.contains("mias"))
    println(trie.howManyStartWithPrefix("m"))
    inputStream.close()
    outputStream.close()
}
