package homework.hw4.task1

import java.io.File
import java.lang.IllegalArgumentException

fun putFile(hashtable: Hashtable<String, String>, file: File) {
    val bufferedReader = file.bufferedReader()
    bufferedReader.useLines { lines ->
        lines.forEach { line ->
            val currentLineElements = line.split(" ")
            currentLineElements.forEach { pair ->
                val regex = Regex("\\[(.+),(.+)]")
                if (!regex.containsMatchIn(pair)) {
                    throw IllegalArgumentException("Wrong data file")
                }
                val key = regex.find(pair)?.groupValues?.get(1)
                val value = regex.find(pair)?.groupValues?.get(2)
                hashtable.put(key, value)
            }
        }
    }
}

fun main() {
    val table = Hashtable<String, String>()
    val file = File("src/main/kotlin/homework/hw4/task1/test")
    if (!file.exists()) {
        println("File not found\n")
        return
    }
    table.put("key1", "first")
    table.put("key2", "second")
    table.printStatistics()
    putFile(table, file)
    table.printStatistics()
    table.changeHashFunction(2)
    table.printStatistics()
    table.remove("my")
    println(table.containsKey("my"))
    println(table.containsKey("file"))
}
