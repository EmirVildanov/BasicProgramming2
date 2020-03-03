import org.junit.Test
import kotlin.test.assertEquals

internal class MainKtTest {

    @Test
    fun shouldReturn0AsStringDoesNotContainsSubstring() {
        assertEquals(0, cutOutLine("mamama", "buba"))
    }

    @Test
    fun shouldReturn0AsSubstringIsUpperCasedMainStringLetter() {
        assertEquals(0, cutOutLine("B", "b"))
    }

    @Test
    fun shouldReturn1AsOnlyCentralCharHasToBeDeleted() {
        assertEquals(1, cutOutLine("mmmmalllll", "mal"))
    }

    @Test
    fun shouldReturn3As3CentralLettersOfForbiddenSubstringHaveToBeDeleted() {
        assertEquals(3, cutOutLine("abuabuabu", "abu"))
    }

    @Test
    fun shouldReturnStringLengthAsSubstringIsTheLetterStringConsistsOf() {
        assertEquals(5, cutOutLine("aaaaa", "a"))
    }

    @Test
    fun shouldReturn4AsStringIsAMirroringOfSubstring() {
        assertEquals(4, cutOutLine("aaaabbbb", "ab"))
    }

    @Test
    fun shouldReturn4AsStringIsAMirroringOfSubstringWith3ExtraLetters() {
        assertEquals(4, cutOutLine("aaaabbbbbbb", "ab"))
    }

    @Test
    fun shouldReturn2CheckingDeletingSubstringOfSpecialSymbols() {
        assertEquals(2, cutOutLine("input :>> blabla \n output :>> blabla", ":>>"))
    }
}