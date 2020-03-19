import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @Test
    fun shouldReturn4() {
        assertEquals(4, sum(3, 1))
    }

    @Test
    fun shouldReturn7() {
        assertEquals(7, sum(3, 4))
    }

    @Test
    fun shouldReturn5() {
        assertEquals(5, sum(3, 2))
    }
}