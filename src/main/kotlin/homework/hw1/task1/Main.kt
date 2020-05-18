package homework.hw1.task1

import kotlin.collections.toMutableList

fun rearrangeArrayParts(array: MutableList<Int>, firstBoard: Int) {
    array.subList(0, firstBoard).reverse()
    array.subList(firstBoard, array.size).reverse()
    array.subList(0, array.size).reverse()
}

fun main() {
    print("Enter the first board : ")
    val firstBoard = readLine()?.toInt()
    print("Enter the second board : ")
    val secondBoard = readLine()?.toInt()
    if (firstBoard != null && secondBoard != null) {
        val length = firstBoard + secondBoard
        print("Enter $length elements of the list : ")
        val array: MutableList<Int>? = readLine()?.split(' ')?.map { it.toInt() }?.toMutableList()
        if (array != null) {
            rearrangeArrayParts(array, firstBoard)
            print("The answer is : ${array.joinToString(" ")}")
        }
    }
}
