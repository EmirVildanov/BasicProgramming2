package homework.hw1.task1

import java.lang.IllegalArgumentException
import kotlin.collections.toMutableList

fun rearrangeArrayParts(array: MutableList<Int>, firstBoard: Int) {
    if (firstBoard > array.size) {
        throw IllegalArgumentException("First board is greater than array size")
    }
    array.subList(0, firstBoard).reverse()
    array.subList(firstBoard, array.size).reverse()
    array.subList(0, array.size).reverse()
}

fun main() {
    print("Enter the first board : ")
    val firstBoard = readLine()?.toIntOrNull()
    print("Enter the second board : ")
    val secondBoard = readLine()?.toIntOrNull()
    if (firstBoard == null || firstBoard < 0) {
        throw IllegalArgumentException("Wrong first board argument")
    }
    if (secondBoard == null || firstBoard < 0) {
        throw IllegalArgumentException("Wrong second board argument")
    }
    val length = firstBoard + secondBoard
    print("Enter $length elements of the list : ")
    val array: MutableList<Int>? = readLine()?.split(' ')?.map { it.toInt() }?.toMutableList()
    if (array == null || array.size != firstBoard + secondBoard) {
        println("Null input array")
        return
    }
    try {
        rearrangeArrayParts(array, firstBoard)
        print("The answer is : ${array.joinToString(" ")}")
    } catch (exception: IllegalArgumentException) {
        print("FirstBoard is greater than array size")
    }
}
