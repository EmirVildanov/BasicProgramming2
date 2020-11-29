package homework.hw4.task1

import homework.hw4.task1.hashFunction.IntCharValuesSumHashFunctionForString
import homework.hw4.task1.hashFunction.PolynomialHashFunctionForString
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class HashtableTest {

    private val resourcesPath = "src/test/resources/homework.hw4.task1/"
    private val startHashtableSize = 16
    @Test
    fun shouldTestEmptyWithoutDoingAnything() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        assertTrue(hashtable.size == 0)
    }
    @Test
    fun shouldTestRemovingFunction() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        hashtable.put("First", "Smth")
        hashtable.remove("First")
        assertTrue(hashtable.size == 0)
    }
    @Test
    fun shouldNotDoNothingReadingEmptyFile() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        putFile(hashtable, File(resourcesPath + "emptyTestFile.txt"))
        assertTrue(hashtable.size == 0)
    }
    @Test
    fun shouldAddElementsFromFile() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        putFile(hashtable, File(resourcesPath + "testFileWIthTwoValues.txt"))
        assertEquals(2, hashtable.getNotEmptyEntries().size)
    }
    @Test
    fun shouldAddFileThatWillMadeHashtableToExpend() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        putFile(hashtable, File(resourcesPath + "testFileThatWillMakeHashtableToExpand.txt"))
        assertTrue(hashtable.size > startHashtableSize)
    }
    @Test
    fun shouldRedefineHashValuesAfterChangingHashFunction() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        val anotherHashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        putFile(anotherHashtable, File(resourcesPath + "testFileThatWillMakeHashtableToExpand.txt"))
        putFile(hashtable, File(resourcesPath + "testFileThatWillMakeHashtableToExpand.txt"))
        anotherHashtable.changeHashFunction(PolynomialHashFunctionForString())
        val firstHashCodesArray = mutableListOf<Int>()
        val secondHashCodesArray = mutableListOf<Int>()
        hashtable.getNotEmptyEntries().forEach {
            it.getElements().forEach {
                firstHashCodesArray.add(it.hashCode)
            }
        }
        anotherHashtable.getNotEmptyEntries().forEach {
            it.getElements().forEach {
                secondHashCodesArray.add(it.hashCode)
            }
        }
        assertNotEquals(firstHashCodesArray, secondHashCodesArray)
    }

    @Test
    fun shouldWorkProperlyIfUserTriesToRemoveElementThatIsNotInTheTable() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        hashtable.put("Test", "Value")
        assertEquals(null, hashtable.remove("AnotherTest"))
    }
    @Test
    fun shouldLeaveAllEntriesAfterChangingHashFunction() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        hashtable.put("first", "one")
        hashtable.put("second", "two")
        hashtable.put("third", "three")
        val previousEntries = hashtable.getNotEmptyEntries()
        val previousBucketElements = previousEntries.flatMap { it -> it.getElements() }
        hashtable.changeHashFunction(PolynomialHashFunctionForString())
        val currentEntries = hashtable.getNotEmptyEntries()
        val currentBucketElements = currentEntries.flatMap { it -> it.getElements() }
        for (i in previousBucketElements.indices) {
            println(previousBucketElements[i])
            println(currentBucketElements[i])
            assertTrue(previousBucketElements.contains(currentBucketElements[i]))
        }
    }
    @Test
    fun shouldTestFindFunction() {
        val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
        hashtable.put("Test", "Value")
        assertEquals("Value", hashtable.get("Test"))
    }
}
