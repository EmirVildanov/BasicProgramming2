package homework.hw4.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class TreeReaderTest {

    @Test
    fun test() {
        val file = File("src/test/kotlin/homework/hw4/task2/fileWithMediumTree")
        val string = "(* (+ (/ 12 12) 1) (/ (- 6 (- 12 9)) 3))"
        val regex = Regex("[(].+[)]", DOT__MATCHES__ALL)
        val value = regex.findAll(string)
        for (matchedText in value) {
            val answer = matchedText.value
            print("")
        }
        println(value)
    }
    @Test
    fun shouldReadFileAndReturn4AsResult() {
        val file = File("src/test/kotlin/homework/hw4/task2/fileWithEasyTree")
        assertEquals(4, TreeReader(file).value)
    }

    @Test
    fun shouldCalculateResultOfTheLeftChildOfTheEasyTree() {
        val file = File("src/test/kotlin/homework/hw4/task2/fileWithEasyTree")
        assertEquals(2, TreeReader(file).root.leftChild!!.calculateValue())
    }

    @Test
    fun shouldReadFileAndReturn2AsResult() {
        val file = File("src/test/kotlin/homework/hw4/task2/fileWithMediumTree")
        assertEquals(2, TreeReader(file).value)
    }
}
