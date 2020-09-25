package homework.hw4.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class TreeReaderTest {

    private val resourcesPath = "src/test/resources/homework.hw4.task2/"

    @Test
    fun shouldCalculateResultOfTheLeftChildOfTheEasyTree() {
        val file = File(resourcesPath + "fileWithTestTree1.txt")
        assertEquals(2, TreeReader(file).root.leftChild?.calculateValue())
    }

    @Test
    fun shouldReadFileAndReturn4AsResult() {
        val file = File(resourcesPath + "fileWithTestTree1.txt")
        assertEquals(4, TreeReader(file).value)
    }
    @Test
    fun shouldReadFileAndReturn2AsResult() {
        val file = File(resourcesPath + "fileWithTestTree2.txt")
        assertEquals(2, TreeReader(file).value)
    }
    @Test
    fun shouldReadFileAndReturn1AsResult() {
        val file = File(resourcesPath + "fileWithTestTree3.txt")
        assertEquals(1, TreeReader(file).value)
    }
    @Test
    fun shouldReadFileAndReturn19600AsResult() {
        val file = File(resourcesPath + "fileWithTestTree4.txt")
        assertEquals(19600, TreeReader(file).value)
    }
}
