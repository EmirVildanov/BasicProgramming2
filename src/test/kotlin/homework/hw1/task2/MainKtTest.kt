package homework.hw1.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

internal class MainKtTest {

    @Test
    fun shouldThrowIllegalArgumentException() {
        assertThrows<IllegalArgumentException> { recursiveFactorial(-5) }
    }

    @Test
    fun shouldReturnRecursiveFactorialOf0() {
        assertEquals(1, recursiveFactorial(0))
    }

    @Test
    fun shouldReturnRecursiveFactorialOf1() {
        assertEquals(1, recursiveFactorial(1))
    }

    @Test
    fun shouldReturnRecursiveFactorialOf2() {
        assertEquals(2, recursiveFactorial(2))
    }

    @Test
    fun shouldReturnRecursiveFactorialOf3() {
        assertEquals(6, recursiveFactorial(3))
    }

    @Test
    fun shouldReturnRecursiveFactorialOf4() {
        assertEquals(24, recursiveFactorial(4))
    }

    @Test
    fun shouldReturnIterativeFactorialOf0() {
        assertEquals(1, iterativeFactorial(0))
    }

    @Test
    fun shouldReturnIterativeFactorialOf1() {
        assertEquals(1, iterativeFactorial(1))
    }

    @Test
    fun shouldReturnIterativeFactorialOf2() {
        assertEquals(2, iterativeFactorial(2))
    }

    @Test
    fun shouldReturnIterativeFactorialOf3() {
        assertEquals(6, iterativeFactorial(3))
    }

    @Test
    fun shouldReturnIterativeFactorialOf4() {
        assertEquals(24, iterativeFactorial(4))
    }

    @Test
    fun shouldReturnRecursiveFactorialOf11() {
        assertEquals(39916800, recursiveFactorial(11))
    }
}
