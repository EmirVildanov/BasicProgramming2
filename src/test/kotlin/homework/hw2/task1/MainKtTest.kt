package homework.hw2.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainKtTest {

    @Test
    fun shouldReturn0ForEmptyString() {
        val mainString = ""
        assertEquals(0, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn0AsMainStringDoesNotContainsForbiddenString() {
        val mainString = "aaaaaaaaa"
        assertEquals(0, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn1AsMainStringIsAForbiddenString() {
        val mainString = "xxx"
        assertEquals(3, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn2AsMainStringIsTwoForbiddenStringsWithASpace() {
        val mainString = "xxx xxx"
        assertEquals(6, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn0AsMainStringIsOnlyTwoCrosses() {
        val mainString = "xx"
        assertEquals(0, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn1InAStringContainingAnotherSymbols() {
        val mainString = "asdfsadfsdfhwtrnrxxxsadfsadf"
        assertEquals(3, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn2InAStringContainingAnotherSymbols() {
        val mainString = "asdxxxfsadfsdfhwtrnrxxxsadfsadf"
        assertEquals(6, cutOutLine(mainString))
    }
    @Test
    fun shouldReturn3InALongStringFullOfCrossesAndSpaces() {
        val mainString = "x xxx xxxx xx xx xxxxx"
        assertEquals(9, cutOutLine(mainString))
    }
}
