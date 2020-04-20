package homework.hw4.task2

import java.io.File

fun main() {
    val file = File("test")
    val reader = TreeReader(file)
    reader.print()
    println("The value of the tree is : ${reader.value}")
}
