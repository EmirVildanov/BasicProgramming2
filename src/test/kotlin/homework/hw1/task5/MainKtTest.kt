package homework.hw1.task5

import countNonEmptyLines
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class MainKtTest {

    @Test
    fun shouldReturn0OnEmptyFile() {
        assertEquals(0, countNonEmptyLines(File("src/test/kotlin/hwFirst/taskFive/testEmptyFile")))
    }

    @Test
    fun shouldReturn3OnFileWith3NonEmptyLines() {
        assertEquals(3, countNonEmptyLines(File("src/test/kotlin/hwFirst/taskFive/testFileWith3NonEmptyLines")))
    }

    @Test
    fun shouldReturn1OnFileWithOneNonEmptyLineAndNewLineSymbol() {
        assertEquals(1, countNonEmptyLines(File("src/test/kotlin/hwFirst/taskFive/testFileWithEmptyLastLine")))
    }

    @Test
    fun shouldReturn7OnFileWithManyEmptyLinesContainingDifferentEmptySymbols() {
        assertEquals(
            7,
            countNonEmptyLines(File("src/test/kotlin/hwFirst/taskFive/" +
                    "testFileWithManyEmptyLinesContainingDifferentEmptySymbols"))
        )
    }
}
