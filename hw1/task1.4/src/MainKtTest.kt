import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MainKtTest {

    @Test
    fun shouldReturnTrueOnEmptyString() {
        assertTrue(checkIfPalindrome(""))
    }

    @Test
    fun shouldReturnTrueOnSimpleString() {
        assertTrue(checkIfPalindrome("amma"))
    }

    @Test
    fun shouldReturnFalseOnSimpleString() {
        assertFalse(checkIfPalindrome("ammm"))
    }

    @Test
    fun shouldReturnTrueOnSingleCharString() {
        assertTrue(checkIfPalindrome("a"))
    }

    @Test
    fun shouldReturnTrueOnPalindromeStringConsistingOfDigits() {
        assertTrue(checkIfPalindrome("5445"))
    }

    @Test
    fun shouldReturnFalseOnPalindromeStringConsistingOfDigits() {
        assertFalse(checkIfPalindrome("5434"))
    }

    @Test
    fun shouldReturnTrueOnSimpleStringContainingSpecialSymbols() {
        assertTrue(checkIfPalindrome("!am,//,ma!"))
    }
}