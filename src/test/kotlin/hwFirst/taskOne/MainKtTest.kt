package hwFirst.taskOne

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

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
    fun shouldThrowAnException() {
        val testArray = IntArray(6) { it + 1 }
        val assertingArray = IntArray(6)
        assertingArray[0] = 4
        assertingArray[1] = 5
        assertingArray[2] = 6
        assertingArray[3] = 1
        assertingArray[4] = 2
        assertingArray[5] = 3
        assertArrayEquals(assertingArray, rearrangeArrayParts(testArray, -1))
    }
}