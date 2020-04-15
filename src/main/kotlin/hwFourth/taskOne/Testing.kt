import java.io.File

fun main() {
    val table = Hashtable()
    val file = File("src/main/kotlin/hwFourth/taskOne/Test")
    if (!file.exists()) {
        print("\nFile not found\n")
        return
    }
    table.add("first")
    table.add("ifrst")
    table.add("firts")
    table.add("second")
    table.printStatistics()
    table.addFromFile(file)
    table.printStatistics()
    table.changeHashFunction(2)
    table.printStatistics()
    table.remove("my")
    println(table.find("my"))
    println(table.find("file"))
}