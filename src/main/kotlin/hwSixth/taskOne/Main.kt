package hwSixth.taskOne

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun quickSort(items: IntArray, left: Int, right: Int) {
    var left = left
    var right = right
    if (left >= right) {
        return
    }
    val mainstayElement = items[left]
    val rememberLeft = left
    val rememberRight = right
    while (left < right) {
        while (items[left] < mainstayElement) {
            left += 1
        }
        while (items[right] > mainstayElement) {
            right -= 1
        }
        val temporary = items[left]
        items[left] = items[right]
        items[right] = temporary
        if (items[left] == items[right]) {
            ++left
        }
    }
    quickSort(items, rememberLeft, right - 1)
    quickSort(items, right + 1, rememberRight)
}

suspend fun realAsyncQuickSort(items: IntArray, left: Int, right: Int) {
    var left = left
    var right = right
    if (left >= right) {
        return
    }
    val mainstayElement = items[left]
    val rememberLeft = left
    val rememberRight = right
    while (left < right) {
        while (items[left] < mainstayElement) {
            left += 1
        }
        while (items[right] > mainstayElement) {
            right -= 1
        }
        val temporary = items[left]
        items[left] = items[right]
        items[right] = temporary
        if (items[left] == items[right]) {
            ++left
        }
    }
    realAsyncQuickSort(items, rememberLeft, right - 1)
    realAsyncQuickSort(items, right + 1, rememberRight)
}

private const val SIZE_OF_ARRAY = 10000000
private const val MAX_NUMBER = 100000

fun main() {
    val numbersList = mutableListOf<Int>()
    for (i in 0 until SIZE_OF_ARRAY) {
        numbersList.add((0..MAX_NUMBER).random())
    }
    val firstTestArray = numbersList.toIntArray()
    val secondTestArray = numbersList.toIntArray()
    val timeOne = measureTimeMillis {
        quickSort(firstTestArray, 0, numbersList.size - 1)
    }
    val timeTwo = measureTimeMillis {
        runBlocking {
            launch { realAsyncQuickSort(secondTestArray, 0, secondTestArray.size - 1) }
        }
    }
    println("Total timeOne is $timeOne \nTotal timeTwo is $timeTwo")
}
