import java.io.File

fun main() {
    val table = Hashtable()
    val file = File("Test")
    if (!file.exists()) {
        print("\nFile not found\n")
        return
    }
    table.add("Ba")
    table.add("Bu")
    table.printStatistics()
}