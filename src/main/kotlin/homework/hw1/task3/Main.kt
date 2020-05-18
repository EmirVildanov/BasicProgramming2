package homework.hw1.task3

import java.util.Scanner

fun countSubstringOccurrence(firstString: String, secondString: String): Int {
    var answer = 0
    for (i in firstString.indices) {
        if (i + secondString.length > firstString.length) {
            break
        }
        if (secondString != "" && firstString.substring(i, i + secondString.length) == secondString) {
            answer++
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
