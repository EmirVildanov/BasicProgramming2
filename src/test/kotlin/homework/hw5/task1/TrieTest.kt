package homework.hw5.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import java.util.Scanner

internal class TrieTest {
    private val resourcesPath = "src/test/resources/homework.hw5.task1/"
    @Test
    fun shouldReturnSizeOfATrie() {
        val trie = Trie()
        trie.add("ba")
        trie.add("ba")
        trie.add("basfgs")
        trie.remove("asdf")
        assertEquals(2, trie.size)
    }
    @Test
    fun shouldCheckTrueAdding() {
        val trie = Trie()
        trie.add("ba")
        assertTrue(trie.add("bal"))
    }
    @Test
    fun shouldCheckFalseAdding() {
        val trie = Trie()
        trie.add("bas")
        assertFalse(trie.add("bas"))
    }
    @Test
    fun shouldCheckTrueRemoving() {
        val trie = Trie()
        trie.add("ba")
        assertTrue(trie.remove("ba"))
    }
    @Test
    fun shouldCheckFalseRemoving() {
        val trie = Trie()
        trie.add("bas")
        assertFalse(trie.remove("ba"))
    }
    @Test
    fun shouldAddOneWordToTrie() {
        val trie = Trie()
        trie.add("ba")
        assertTrue(trie.contains("ba"))
    }
    @Test
    fun shouldCountWordsStaringWithPrefix() {
        val trie = Trie()
        trie.add("h")
        trie.add("asdf")
        trie.add("hi")
        trie.add("hiccups")
        trie.add("hoaksf")
        trie.add("his")
        trie.add("him")
        assertEquals(4, trie.howManyStartWithPrefix("hi"))
    }
    @Test
    fun shouldDeserializeTrieFromFile() {
        val trie = Trie()
        val file = File(resourcesPath + "testRead")
        trie.readObject(file.inputStream())
        val wordsArray = mutableListOf("m", "mi", "mas", "mias", "mos")
        for (i in 0 until wordsArray.size) {
            assertTrue(trie.contains(wordsArray[i]))
        }
    }
    @Test
    fun shouldRewriteTrieWithWordsFromFile() {
        val trie = Trie()
        val previousWordsArray = mutableListOf("aa", "asdf", "bgsd")
        previousWordsArray.forEach { trie.add(it) }
        val file = File(resourcesPath + "testRead")
        trie.readObject(file.inputStream())
        val wordsArray = mutableListOf("m", "mi", "mas", "mias", "mos")
        wordsArray.forEach {
            assertTrue(trie.contains(it))
        }
        previousWordsArray.forEach {
            assertFalse(trie.contains(it))
        }
    }
    @Test
    fun shouldAddWordsToFile() {
        val trie = Trie()
        val wordsArray = mutableListOf("aa", "asdf", "bgsd")
        wordsArray.forEach { trie.add(it) }
        val writeFile = File(resourcesPath + "testWrite")
        trie.writeObject(writeFile.outputStream())
        val scanner = Scanner(writeFile)
        val fileWordsAfterSerializing = scanner.nextLine().split(" ")
        for (i in fileWordsAfterSerializing.indices) {
            assertEquals(wordsArray[i], fileWordsAfterSerializing[i])
        }
    }
}
