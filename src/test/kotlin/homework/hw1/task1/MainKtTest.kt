package homework.hw1.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

internal class MainKtTest {

    @Test
    fun shouldDoNothingWithIntArray() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, array.size)
        println(array)
        assertEquals(array, listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun shouldleaveIntArrayInHisFirstView() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 0)
        assertEquals(array, listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun shouldAlsoleaveIntArrayInHisFirstView() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 0)
        assertEquals(array, listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun shouldRearrangeArrayParts() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 3)
        assertEquals(array, listOf(4, 5, 1, 2, 3))
    }

    @Test
    fun shouldRearrangePartsOfNegativeArray() {
        val array = mutableListOf(-1, -2, -3, -4, -5)
        rearrangeArrayParts(array, 2)
        assertEquals(array, listOf(-3, -4, -5, -1, -2))
    }

    @Test
    fun shouldRearrangeEasyArray() {
        val testArray = IntArray(6) { it + 1 }.toMutableList()
        rearrangeArrayParts(testArray, 3)
        assertEquals(testArray, listOf(4, 5, 6, 1, 2, 3))
    }

    @Test
    fun shouldRearrangeMediumSizeArray() {
        val testArray = IntArray(10) { it + 1 }.toMutableList()
        rearrangeArrayParts(testArray, 7)
        assertEquals(testArray, listOf(8, 9, 10, 1, 2, 3, 4, 5, 6, 7))
    }
}
