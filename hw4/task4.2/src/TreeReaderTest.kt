import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

internal class TreeReaderTest {

    @Test
    fun sholudReadFileAndReturn4AsResult() {
        val file = File("test")
        assertEquals(4, TreeReader(file).value)
    }
}