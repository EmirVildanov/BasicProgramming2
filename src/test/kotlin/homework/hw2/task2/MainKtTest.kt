import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainKtTest {

    @Test
    fun shouldNotDoAnythingWithArray() {
        val array = arrayListOf(1, 2, 3, 4)
        deleteRepeatingElements(array)
        for (i in array.indices) {
            assertEquals(arrayListOf(1, 2, 3, 4)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveOnlyOneUnit() {
        val array = arrayListOf(1, 1, 1, 1)
        deleteRepeatingElements(array)
        for (i in array.indices) {
            assertEquals(arrayListOf(1)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveFirstThreeNumbers() {
        val array = arrayListOf(1, 11, 23, 23, 23, 23, 23)
        deleteRepeatingElements(array)
        for (i in array.indices) {
            assertEquals(arrayListOf(1, 11, 23)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveOnlyOneUnitInTheCenter() {
        val array = arrayListOf(2, 1, 1, 1, 1, 1, 3)
        deleteRepeatingElements(array)
        for (i in array.indices) {
            assertEquals(arrayListOf(2, 1, 3)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveAllNumbersOnes() {
        val array = arrayListOf(1, 1, 2, 2, 3, 3, 4, 4)
        deleteRepeatingElements(array)
        for (i in array.indices) {
            assertEquals(arrayListOf(1, 2, 3, 4)[i], array[i])
        }
    }

    @Test
    fun shouldLeaveOnlyTheRightPartOfTheMirroring() {
        val array = arrayListOf(4, 3, 2, 1, 1, 2, 3, 4)
        deleteRepeatingElements(array)
        for (i in array.indices) {
            assertEquals(arrayListOf(1, 2, 3, 4)[i], array[i])
        }
    }
}
