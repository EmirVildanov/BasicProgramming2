package homework.hw6.task1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun quickSort(items: IntArray, left: Int, right: Int) {
    var leftPointer = left
    var rightPointer = right
    if (leftPointer >= rightPointer) {
        return
    }
    val mainstayElement = items[leftPointer]
    val rememberLeft = leftPointer
    val rememberRight = rightPointer
    while (leftPointer < rightPointer) {
        while (items[leftPointer] < mainstayElement) {
            leftPointer += 1
        }
        while (items[rightPointer] > mainstayElement) {
            rightPointer -= 1
        }
        val temporary = items[leftPointer]
        items[leftPointer] = items[rightPointer]
        items[rightPointer] = temporary
        if (items[leftPointer] == items[rightPointer]) {
            ++leftPointer
        }
    }
    quickSort(items, rememberLeft, rightPointer - 1)
    quickSort(items, rightPointer + 1, rememberRight)
}

suspend fun asyncQuickSort(items: IntArray, left: Int, right: Int) {
    var leftPointer = left
    var rightPointer = right
    if (leftPointer >= rightPointer) {
        return
    }
    val mainstayElement = items[leftPointer]
    val rememberLeft = leftPointer
    val rememberRight = rightPointer
    while (leftPointer < rightPointer) {
        while (items[leftPointer] < mainstayElement) {
            leftPointer += 1
        }
        while (items[rightPointer] > mainstayElement) {
            rightPointer -= 1
        }
        val temporary = items[leftPointer]
        items[leftPointer] = items[rightPointer]
        items[rightPointer] = temporary
        if (items[leftPointer] == items[rightPointer]) {
            ++leftPointer
        }
    }
    asyncQuickSort(items, rememberLeft, rightPointer - 1)
    asyncQuickSort(items, rightPointer + 1, rememberRight)
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
            launch {
                asyncQuickSort(
                    secondTestArray, 0, secondTestArray.size - 1
                )
            }
        }
    }
    println("Total timeOne is $timeOne \nTotal timeTwo is $timeTwo")
}
