package homework.hw2.task1

import java.util.Scanner

const val X_REPEATING_NUMBER = 3

fun main() {
    val scan = Scanner(System.`in`)
    print("Enter the hole line : ")
    val holeLine: String = scan.next()
    println("You have to delete ${cutOutLine(holeLine)} to get rid of the forbidden line")
}

fun cutOutLine(mainString: String): Int {
    var answer = 0
    var currentRepeatingCounter = 0
    var currentIndex = 0
    while (currentIndex < mainString.length) {
        if (mainString[currentIndex] == 'x') {
            while (currentIndex < mainString.length && mainString[currentIndex] == 'x') {
                ++currentRepeatingCounter
                ++currentIndex
            }
            answer += currentRepeatingCounter - currentRepeatingCounter % X_REPEATING_NUMBER
            currentRepeatingCounter = 0
        }
        ++currentIndex
    }
    return answer
}
