import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainKtTest {

    @Test
    fun shouldNotDoAnythingWithStringList() {
        val array = arrayListOf("ba", "ba", "bo", "ba")
        deleteRepeatingElements(array)
        assertEquals(deleteRepeatingElements(array), arrayListOf("bo", "ba"))
    }
    @Test
    fun shouldNotDoAnythingWithStringListOfEqualElements() {
        val array = arrayListOf("ba", "ba", "ba", "ba")
        deleteRepeatingElements(array)
        assertEquals(deleteRepeatingElements(array), arrayListOf("ba"))
    }
    @Test
    fun shouldNotDoAnythingWithList() {
        val array = arrayListOf(1, 2, 3, 4)
        deleteRepeatingElements(array)
        assertEquals(deleteRepeatingElements(array), arrayListOf(1, 2, 3, 4))
    }

    @Test
    fun shouldLeaveOnlyOneUnit() {
        val array = arrayListOf(1, 1, 1, 1)
        assertEquals(deleteRepeatingElements(array), arrayListOf(1))
    }

    @Test
    fun shouldLeaveFirstThreeNumbers() {
        val array = arrayListOf(1, 11, 23, 23, 23, 23, 23)
        assertEquals(deleteRepeatingElements(array), arrayListOf(1, 11, 23))
    }

    @Test
    fun shouldLeaveOnlyOneUnitInTheCenter() {
        val array = arrayListOf(2, 1, 1, 1, 1, 1, 3)
        assertEquals(deleteRepeatingElements(array), arrayListOf(2, 1, 3))
    }

    @Test
    fun shouldLeaveAllNumbersOnes() {
        val array = arrayListOf(1, 1, 2, 2, 3, 3, 4, 4)
        assertEquals(deleteRepeatingElements(array), arrayListOf(1, 2, 3, 4))
    }

    @Test
    fun shouldLeaveOnlyTheRightPartOfTheMirroring() {
        val array = arrayListOf(4, 3, 2, 1, 1, 2, 3, 4)
        assertEquals(deleteRepeatingElements(array), arrayListOf(1, 2, 3, 4))
    }
}
