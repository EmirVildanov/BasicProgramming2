package homework.hw6.task1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertArrayEquals

internal class QuickSortTest {
    private val quickSort = QuickSort()

    @Test
    fun shouldSortEasyArray1() {
        val array = intArrayOf(345, 2314, 324532, 2134)
        quickSort.usualQuickSort(array, 0, array.size - 1)
        val testArray = intArrayOf(345, 2134, 2314, 324532)
        assertArrayEquals(array, testArray)
    }
    @Test
    fun shouldSortEasyArray2() {
        val array = intArrayOf(1, 4, 2, 5, 3, 7, 6, 10, 8, 9)
        quickSort.usualQuickSort(array, 0, array.size - 1)
        val testArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        assertArrayEquals(array, testArray)
    }
    @Test
    fun shouldSortEmptyArray() {
        val array = intArrayOf()
        quickSort.usualQuickSort(array, 0, array.size - 1)
        val testArray = intArrayOf()
        assertArrayEquals(array, testArray)
    }
    @Test
    fun shouldSortArrayThatAlreadySorted() {
        val array = intArrayOf(21, 321, 2134, 324532)
        quickSort.usualQuickSort(array, 0, array.size - 1)
        val testArray = intArrayOf(21, 321, 2134, 324532)
        assertArrayEquals(array, testArray)
    }
    @Test
    fun shouldSortAsyncEasyArray1() {
        val array = intArrayOf(345, 2314, 324532, 2134)
        runBlocking {
            launch {
                quickSort.asyncQuickSort(array, 0, array.size - 1)
            }
        }
        val testArray = intArrayOf(345, 2134, 2314, 324532)
        assertArrayEquals(array, testArray)
    }

    @Test
    fun shouldSortAsyncEasyArray2() {
        val array = intArrayOf(321, 450, 211, 45, 32, 56, 125, 567, 32)
        runBlocking {
            launch {
                quickSort.asyncQuickSort(array, 0, array.size - 1)
            }
        }
        val testArray = intArrayOf(32, 32, 45, 56, 125, 211, 321, 450, 567)
        assertArrayEquals(array, testArray)
    }

    @Test
    fun shouldSortAsyncEmptyArray() {
        val array = intArrayOf()
        runBlocking {
            launch {
                quickSort.asyncQuickSort(array, 0, array.size - 1)
            }
        }
        val testArray = intArrayOf()
        assertArrayEquals(array, testArray)
    }
    @Test
    fun shouldSortAsyncArrayThatIsAlreadySorted() {
        val array = intArrayOf(1, 1, 1, 1, 1)
        runBlocking {
            launch {
                quickSort.asyncQuickSort(array, 0, array.size - 1)
            }
        }
        val testArray = intArrayOf(1, 1, 1, 1, 1)
        assertArrayEquals(array, testArray)
    }
}
