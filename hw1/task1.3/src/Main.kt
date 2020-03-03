import java.util.Scanner

fun countSubstringOccurrence(firstString: String, secondString: String): Int {
    var answer = 0
    for (i in firstString.indices) {
        for (j in secondString.indices) {
            if (i + j > firstString.length - 1 || firstString[i + j] != secondString[j]) {
                break;
            }
            if (j == secondString.length - 1) {
                ++answer
            }
        }
    }
    return answer
}

fun main() {
    val scan = Scanner(System.`in`)
    print("Enter the first string: ")
    val firstString: String = scan.nextLine()
    print("Enter the second string: ")
    val secondString: String = scan.nextLine()
    val answer = countSubstringOccurrence(firstString, secondString)
    print("The answer is: $answer")
}