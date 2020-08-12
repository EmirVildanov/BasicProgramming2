package homework.hw4.task1

import java.io.File

fun main() {
    val table = Hashtable<String, String>()
    val file = File("src/main/resources/homework/hw4/task1/test.txt")
    if (!file.exists()) {
        println("File not found")
        return
    }
    table.put("key1", "first")
    table.put("key2", "second")
    table.printStatistics()
    table.changeHashFunction(2)
    table.printStatistics()
    table.changeHashFunction(1)
    putFile(table, file)
//    table.put("my", "file")
//    table.put("by", "bo")
//    table.put("end", "of")
//    table.put("file", "yes")
    table.printStatistics()
    table.remove("my")
    println(table.containsKey("my"))
    println(table.containsKey("file"))

    var testString = "test"
    for (i in 0 until 100) {
        table.put(i.toString(), testString)
        testString += "F"
    }
    table.printStatistics()
}
