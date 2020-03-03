import java.util.Scanner

fun recursiveFactorial(number: Int): Int {
    return if (number == 0) {
        1
    } else {
        recursiveFactorial(number - 1) * number
    }
}

fun iterativeFactorial(number: Int): Int {
    var answer = 1
    for (i in 1..number) {
        answer *= i
    }
    return answer
}

fun main() {
    val scan = Scanner(System.`in`)
    print("Enter the number: ")
    val input: Int = scan.nextInt()
    println("The recursive function answer is: ${recursiveFactorial(input)}")
    println("The iterative function answer is: ${iterativeFactorial(input)}")
}