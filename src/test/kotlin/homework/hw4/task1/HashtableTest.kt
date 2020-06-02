package homework.hw4.task1

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class HashtableTest {

    @Test
    fun shouldTestEmptyWithoutDoingAnything() {
        val hashtable = Hashtable<String, String>()
        assertTrue(hashtable.isEmpty())
    }
    @Test
    fun shouldTestRemovingFunction() {
        val hashtable = Hashtable<String, String>()
        hashtable.put("First", "Smth")
        hashtable.remove("First")
        assertTrue(hashtable.isEmpty())
    }
    @Test
    fun shouldAddElementInHashtable() {
        val hashtable = Hashtable<Int, String>()
        hashtable.put(1, "Smth")
        assertTrue(hashtable.containsKey(1))
    }
    @Test
    fun shouldNotDoNothingReadingEmptyFile() {
        val hashtable = Hashtable<String, String>()
        hashtable.putFile(File("src/test/kotlin/homework/hw4/task1/emptyTestFile"))
        assertTrue(hashtable.isEmpty())
    }
    @Test
    fun shouldAddElementsFromFile() {
        val hashtable = Hashtable<Int, String>()
        val anotherHashtable = Hashtable<Int, String>()
        anotherHashtable.putFile(File("src/test/kotlin/homework/hw4/task1/notEmptyTestFile"))
        anotherHashtable.put(1, "test")
        anotherHashtable.put(2, "file")
        assertTrue(hashtable == anotherHashtable)
    }
    @Test
    fun shouldAddFileThatWillMadeHashtableToExtend() {
        val hashtable = Hashtable<String, String>()
        hashtable.putFile(File("src/test/kotlin/homework/hw4/task1/testFileThatWillMakeHashtableToExtand"))
        assertEquals(2, hashtable.expansionNumber)
    }
    @Test
    fun shouldRedefineHashValuesAfterChangingHashFunction() {
        val hashtable = Hashtable<String, String>()
        val anotherHashtable = Hashtable<String, String>()
        anotherHashtable.putFile(File("src/test/kotlin/homework/hw4/task1/testFileThatWillMakeHashtableToExtand"))
        hashtable.putFile(File("src/test/kotlin/homework/hw4/task1/testFileThatWillMakeHashtableToExtand"))
        anotherHashtable.changeHashFunction(2)
        val firstArray = mutableListOf<Int>()
        val secondArray = mutableListOf<Int>()
        hashtable.entries.forEach { it ->
            it?.elements?.forEach {
                firstArray.add(it?.hash ?: 0)
            }
        }
        anotherHashtable.entries.forEach { it ->
            it?.elements?.forEach {
                secondArray.add(it?.hash ?: 0)
            }
        }
        assertNotEquals(firstArray.toIntArray(), secondArray.toIntArray())
    }
}
