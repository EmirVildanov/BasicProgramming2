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
    if (firstBoard == null || firstBoard < 0) {
        println("Wrong first board argument")
        return
    }
    print("Enter the second board : ")
    val secondBoard = readLine()?.toIntOrNull()
    if (secondBoard == null || secondBoard < 0) {
        println("Wrong second board argument")
        return
    }
    print("Enter ${firstBoard + secondBoard} elements of the list : ")
    val array: MutableList<Int>? = readLine()?.split(' ')?.map { it.toInt() }?.toMutableList()
    if (array == null) {
        println("Null input array")
    } else if (array.size != firstBoard + secondBoard) {
        println("Input array size doesn't equals input arguments")
    } else {
        try {
            rearrangeArrayParts(array, firstBoard)
            print("The answer is : ${array.joinToString(" ")}")
        } catch (exception: IllegalArgumentException) {
            print("FirstBoard is greater than array size")
        }
    }
}
