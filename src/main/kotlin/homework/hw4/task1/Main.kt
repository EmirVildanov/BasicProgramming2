package homework.hw4.task1

import java.io.File

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
//    putFile(table, file)
    table.printStatistics()
    /*
    [my,file] [by,bo]
[end,of]
[file,yes]
     */
    table.put("my", "file")
    table.put("by", "bo")
    table.put("end", "of")
    table.put("file", "yes")
    table.changeHashFunction(2)
    table.printStatistics()
    table.remove("my")
    println(table.containsKey("my"))
    println(table.containsKey("file"))

    var testString = "test"
    for (i in 0 until 10) {
        table.put(i.toString(), testString)
        testString += "bla"
    }

    print(hashtable())

    table.printStatistics()
}
