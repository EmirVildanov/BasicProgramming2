import java.util.Scanner

fun reverse(array: IntArray, start: Int, end: Int) {
    val length: Int = (end - start + 1) / 2
    for (i in 0 until length) {
        val temporary: Int = array[start + i]
        array[start + i] = array[end - i]
        array[end - i] = temporary
    }
}

fun rearrangeArrayParts(array: IntArray, firstBoard: Int, length: Int) {
    reverse(array, 0, firstBoard - 1)
    reverse(array, firstBoard, length - 1)
    reverse(array, 0, length - 1)
}

fun main() {
    val scan = Scanner(System.`in`)
    print("Enter the first board : ")
    val firstBoard: Int = scan.nextInt()
    print("Enter the second board : ")
    val secondBoard: Int = scan.nextInt()
    val length = firstBoard + secondBoard
    val array = IntArray(length) {0}
    print("Enter $length elements of the list : ")
    for (i in 0 until length) {
        array[i] = scan.nextInt()
    }
    rearrangeArrayParts(array, firstBoard, length)
    print("The answer is : ")
    array.forEach { print("$it ") }
}