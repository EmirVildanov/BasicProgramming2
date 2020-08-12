package homework.hw4.task1

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class HashtableTest {

    companion object {
        private const val TEST_FILES_PATH = "src/main/resources/homework/hw4/task1/"
        private const val FIRST_HASHTABLE_SIZE = 16
    }
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
        putFile(hashtable, File(TEST_FILES_PATH + "emptyTestFile.txt"))
        assertTrue(hashtable.isEmpty())
    }
    @Test
    fun shouldAddElementsFromFile() {
        val hashtable = Hashtable<String, String>()
        val anotherHashtable = Hashtable<String, String>()
        putFile(anotherHashtable, File(TEST_FILES_PATH + "notEmptyTestFile.txt"))
        anotherHashtable.put("1", "test")
        anotherHashtable.put("2", "file")
        assertTrue(hashtable == anotherHashtable)
    }
    @Test
    fun shouldAddFileThatWillMadeHashtableToExpend() {
        val hashtable = Hashtable<String, String>()
        putFile(hashtable, File(TEST_FILES_PATH + "testFileThatWillMakeHashtableToExpand.txt"))
        assertTrue(hashtable.size > FIRST_HASHTABLE_SIZE)
    }
    @Test
    fun shouldRedefineHashValuesAfterChangingHashFunction() {
        val hashtable = Hashtable<String, String>()
        val anotherHashtable = Hashtable<String, String>()
        putFile(anotherHashtable,
            {}.javaClass.classLoader.getResource(TEST_FILES_PATH + "testFileThatWillMakeHashtableToExpand.txt")
            File(TEST_FILES_PATH + "testFileThatWillMakeHashtableToExpand.txt"))
        putFile(hashtable, File(TEST_FILES_PATH + "testFileThatWillMakeHashtableToExpand.txt"))
        anotherHashtable.changeHashFunction(2)
        val firstHashCodesArray = mutableListOf<Int>()
        val secondHashCodesArray = mutableListOf<Int>()
        hashtable.entries.forEach {
            it.elements.forEach {
                firstHashCodesArray.add(it.hashCode)
            }
        }
        anotherHashtable.entries.forEach {
            it.elements.forEach {
                secondHashCodesArray.add(it.hashCode)
            }
        }
        assertNotEquals(firstHashCodesArray, secondHashCodesArray)
    }

    @Test
    fun shouldWorkProperlyIfUserTriesToRemoveElementThatIsNotInTheTable() {
        val hashtable = Hashtable<String, String>()
        hashtable.put("Test", "Value")
        assertEquals(null, hashtable.remove("AnotherTest"))
    }
    @Test
    fun shouldLeaveAllEntriesAfterChangingHashFunction() {
        val hashtable = Hashtable<String, String>()
        hashtable.put("first", "one")
        hashtable.put("second", "two")
        hashtable.put("third", "three")
        val elements = hashtable.entries
        hashtable.changeHashFunction(2)
        assertEquals(elements, hashtable.entries)
    }
    @Test
    fun shouldTestContainsValueFunction() {
        val hashtable = Hashtable<String, String>()
        hashtable.put("Test", "Value")
        assertTrue(hashtable.containsValue("Value"))
    }
}
