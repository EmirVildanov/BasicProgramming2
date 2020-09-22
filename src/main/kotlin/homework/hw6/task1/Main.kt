package homework.hw6.task1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun getRandomNumbersArray(arraySize: Int = 10000000, maxNumber: Int = 100000) =
    IntArray(arraySize) { (0..maxNumber).random() }

fun main() {
    val numbersArray = getRandomNumbersArray()
    val quickSort = QuickSort()
    val testArrayForUsualSort = numbersArray.clone()
    val testArrayForAsyncSort = numbersArray.clone()
    val usualQuickSortTime = measureTimeMillis {
        quickSort.usualQuickSort(testArrayForUsualSort, 0, numbersArray.lastIndex)
    }
    val asyncQuickSortTime = measureTimeMillis {
        runBlocking {
            launch {
                quickSort.asyncQuickSort(testArrayForAsyncSort, 0, numbersArray.lastIndex)
            }
        }
    }
    println("Total time of usual quick sort is $usualQuickSortTime " +
            "\nTotal time of async quick sort is $asyncQuickSortTime")
}
