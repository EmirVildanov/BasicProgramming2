import org.junit.Test
import kotlin.test.assertEquals

internal class MainKtTest {

    @Test
    fun shouldReverseIntArray() {
        val array = intArrayOf(1, 2, 3, 4, 5)
        reverse(array, 0, array.size - 1)
        for (i in array.indices) {
            assertEquals(intArrayOf(5, 4, 3, 2, 1)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveIntArrayInHisFirstView() {
        val array = intArrayOf(1, 2, 3, 4, 5)
        reverse(array, array.size - 1, array.size - 1)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }

    @Test
    fun shouldAlsoLeaveIntArrayInHisFirstView() {
        val array = intArrayOf(1, 2, 3, 4, 5)
        reverse(array, 0, 0)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }

    @Test
    fun shouldReverseArrayOfNegativeNumbers() {
        val array = intArrayOf(-1, -2, -3, -4, -5)
        reverse(array, 0, array.size - 1)
        for (i in array.indices) {
            assertEquals(intArrayOf(-5, -4, -3, -2, -1)[i], array[i])
        }
    }

    @Test
    fun shouldRearrangeArrayParts() {
        val array = intArrayOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 3, 5)
        for (i in array.indices) {
            assertEquals(intArrayOf(4, 5, 1, 2, 3)[i], array[i])
        }
    }

    @Test
    fun shouldRearrangePartsOfNegativeArray() {
        val array = intArrayOf(-1, -2, -3, -4, -5)
        rearrangeArrayParts(array, 2, 5)
        for (i in array.indices) {
            assertEquals(intArrayOf(-3, -4, -5, -1, -2)[i], array[i])
        }
    }

    @Test
    fun shouldNotRearrangePartsOfArrayWithZeroFirstBoard() {
        val array = intArrayOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 0, 5)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }

    @Test
    fun shouldNotRearrangePartsOfArrayWithMaximumFirstBoard() {
        val array = intArrayOf(1, 2, 3, 4, 5)
        rearrangeArrayParts(array, 5, 5)
        for (i in array.indices) {
            assertEquals(intArrayOf(1, 2, 3, 4, 5)[i], array[i])
        }
    }
}