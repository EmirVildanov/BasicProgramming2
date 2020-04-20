package homework.hw4.task1

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class HashtableTest {
    @Test
    fun shouldAddElementInHashtable() {
        val hashtable = Hashtable()
        hashtable.add("Ba")
        assertTrue(hashtable.words.contains("Ba"))
    }
    @Test
    fun shouldNotDoNothingReadingEmptyFile() {
        val hashtable = Hashtable()
        val anotherHashtable = Hashtable()
        hashtable.addFromFile(File("src/test/kotlin/homework/hw4/task1/emptyTestFile"))
        assertTrue(hashtable == anotherHashtable)
    }
    @Test
    fun shouldRemoveElementFromHashtable() {
        val hashtable = Hashtable()
        val anotherHashtable = Hashtable()
        anotherHashtable.add("Ba")
        anotherHashtable.remove("Ba")
        assertTrue(hashtable == anotherHashtable)
    }
    @Test
    fun shouldAddElementsFromFile() {
        val hashtable = Hashtable()
        val anotherHashtable = Hashtable()
        anotherHashtable.addFromFile(File("src/test/kotlin/homework/hw4/task1/notEmptyTestFile"))
        anotherHashtable.add("test")
        anotherHashtable.add("file")
        assertTrue(hashtable == anotherHashtable)
    }
    @Test
    fun shouldAddFileThatWillMadeHashtableToExtend() {
        val hashtable = Hashtable()
        hashtable.addFromFile(File("src/test/kotlin/homework/hw4/task1/testFileThatWillMakeHashtableToExtand"))
        assertEquals(2, hashtable.expansionNumber)
    }
    @Test
    fun shouldRedefineHashValuesAfterChangingHashFunction() {
        val hashtable = Hashtable()
        val anotherHashtable = Hashtable()
        anotherHashtable.addFromFile(File("src/test/kotlin/homework/hw4/task1/testFileThatWillMakeHashtableToExtand"))
        hashtable.addFromFile(File("src/test/kotlin/homework/hw4/task1/testFileThatWillMakeHashtableToExtand"))
        anotherHashtable.changeHashFunction(2)
        val firstArray = mutableListOf<Int>()
        val secondArray = mutableListOf<Int>()
        hashtable.words.forEach { firstArray.add(hashtable.hashFunction(it)) }
        anotherHashtable.words.forEach { secondArray.add(anotherHashtable.hashFunction(it)) }
        assertNotEquals(firstArray.toIntArray(), secondArray.toIntArray())
    }
}
