import java.io.File

fun countNonEmptyLines(file: File): Int {
    return file.readLines().count { !it.isBlank() }
}

fun main() {
    val file = File("src/main/kotlin/hwFirst/taskFive/test.txt")
    if (!file.exists()) {
        print("\nFile not found\n")
        return
    }
    val numberOfLines = countNonEmptyLines(file)
    println("The number of non-empty lines in your file is : $numberOfLines")
}
