import java.io.File

fun countNonEmptyLines(file: File): Int {
    var answer = 0
    for (line in file.readLines()) {
        if (!line.isBlank()) {
            answer++
        }
    }
    return answer
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
