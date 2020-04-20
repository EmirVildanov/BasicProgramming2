import java.util.Scanner

fun checkIfPalindrome(input: String): Boolean {
    for (i in 0 until input.length / 2) {
        if (input[i] != input[input.length - i - 1]) {
            return false
        }
    }
    return true
}

fun main() {
    val scan: Scanner = Scanner(System.`in`)
    print("Enter the string: ")
    val input: String = scan.nextLine()
    val identicalChecking = checkIfPalindrome(input)
    if (identicalChecking) {
        print("Your string is palindrome")
    } else {
        print("Your string is not palindrome")
    }
}
