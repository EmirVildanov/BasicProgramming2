package homework.hw4.task1

import java.io.File

fun main() {
    val table = Hashtable()
    val file = File("src/main/kotlin/homework/hw4/task1/Test")
    if (!file.exists()) {
        print("\nFile not found\n")
        return
    }
    table.add("first")
    table.add("second")
    table.printStatistics()
    table.addFromFile(file)
    table.printStatistics()
    table.changeHashFunction(2)
    table.printStatistics()
    table.remove("my")
    println(table.words.contains("my"))
    println(table.words.contains("file"))
}
