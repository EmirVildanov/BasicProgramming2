package homework.hw1.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

internal class MainKtTest {

    @Test
    fun shouldDoNothingWithIntArray() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, array.size)
        println(array)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveIntArrayInHisFirstView() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 0)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }

    @Test
    fun shouldAlsoLeaveIntArrayInHisFirstView() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 0)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }

    @Test
    fun shouldRearrangeArrayParts() {
        val array = mutableListOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 3)
        for (i in array.indices) {
            assertEquals(intArrayOf(4, 5, 1, 2, 3)[i], array[i])
        }
    }

    @Test
    fun shouldRearrangePartsOfNegativeArray() {
        val array = mutableListOf(-1, -2, -3, -4, -5)
        rearrangeArrayParts(array, 2)
        for (i in array.indices) {
            assertEquals(intArrayOf(-3, -4, -5, -1, -2)[i], array[i])
        }
    }

    @Test
    fun shouldRearrangeEasyArray() {
        val testArray = IntArray(6) { it + 1 }.toMutableList()
        val assertingArray = IntArray(6)
        assertingArray[0] = 4
        assertingArray[1] = 5
        assertingArray[2] = 6
        assertingArray[3] = 1
        assertingArray[4] = 2
        assertingArray[5] = 3
        rearrangeArrayParts(testArray, 3)
        for (i in testArray.indices) {
            assertEquals(assertingArray[i], testArray[i])
        }
    }

    @Test
    fun shouldRearrangeMediumSizeArray() {
        val testArray = IntArray(10) { it + 1 }.toMutableList()
        val assertingArray = IntArray(10)
        assertingArray[0] = 8
        assertingArray[1] = 9
        assertingArray[2] = 10
        assertingArray[3] = 1
        assertingArray[4] = 2
        assertingArray[5] = 3
        assertingArray[6] = 4
        assertingArray[7] = 5
        assertingArray[8] = 6
        assertingArray[9] = 7
        rearrangeArrayParts(testArray, 7)
        for (i in testArray.indices) {
            assertEquals(assertingArray[i], testArray[i])
        }
    }
}
