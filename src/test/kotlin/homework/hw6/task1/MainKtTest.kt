package homework.hw6.task1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertArrayEquals

internal class MainKtTest {
    @Test
    fun shouldSortEasyArray() {
        val array = listOf(345, 2314, 324532, 2134).toIntArray()
        quickSort(array, 0, array.size - 1)
        val testArray = listOf(345, 2134, 2314, 324532).toIntArray()
        assertArrayEquals(array, testArray)
    }
    @Test
    fun shouldSortAsyncEasyArray() {
        val array = listOf(345, 2314, 324532, 2134).toIntArray()
        runBlocking {
            launch {
                asyncQuickSort(array, 0, array.size - 1)
            }
        }
        val testArray = listOf(345, 2134, 2314, 324532).toIntArray()
        assertArrayEquals(array, testArray)
    }
}
