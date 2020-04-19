package homework.hw1.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertArrayEquals

internal class MainKtTest {

    @Test
    fun shouldRearrangeEasyArray() {
        val testArray = IntArray(6) { it + 1 }
        val assertingArray = IntArray(6)
        assertingArray[0] = 4
        assertingArray[1] = 5
        assertingArray[2] = 6
        assertingArray[3] = 1
        assertingArray[4] = 2
        assertingArray[5] = 3
        assertArrayEquals(assertingArray, rearrangeArrayParts(testArray, 3))
    }

    @Test
    fun shouldRearrangeMediumSizeArray() {
        val testArray = IntArray(10) { it + 1 }
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
        assertArrayEquals(assertingArray, rearrangeArrayParts(testArray, 7))
    }
}
