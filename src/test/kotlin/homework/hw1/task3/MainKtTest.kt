package homework.hw1.task3

import countSubstringOccurrence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainKtTest {

    @Test
    fun shouldReturn0AsSubstringIsEmpty() {
        assertEquals(0, countSubstringOccurrence("abba", ""))
    }

    @Test
    fun shouldReturn0AsSubstringDoesNotContainsInString() {
        assertEquals(0, countSubstringOccurrence("abuba", "am"))
    }

    @Test
    fun shouldReturn1AsSubstringIsAFullString() {
        assertEquals(1, countSubstringOccurrence("ma", "ma"))
    }

    @Test
    fun shouldReturn3AsStringContains3Substrings() {
        assertEquals(3, countSubstringOccurrence("ololol", "ol"))
    }

    @Test
    fun shouldReturn3CountingNewLineSymbolSubstrrig() {
        assertEquals(3, countSubstringOccurrence("ma\n\n\n", "\n"))
    }

    @Test
    fun shouldReturn3CountingSpecialSymbols() {
        assertEquals(3, countSubstringOccurrence("2015;!54;!code;!", ";!"))
    }
}
