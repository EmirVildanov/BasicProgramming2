package homework.hw4.task2

import java.io.File

fun main() {
    val file = File("src/main/kotlin/homework/hw4/task2/test3")
    val reader = TreeReader(file)
    reader.print()
    println("The value of the tree is : ${reader.value}")
}
