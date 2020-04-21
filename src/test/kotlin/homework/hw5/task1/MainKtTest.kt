package homework.hw5.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File

internal class MainKtTest {
    @Test
    fun shouldReturnSizeOfATrie() {
        val trie = Trie()
        trie.add("ba")
        trie.add("ba")
        trie.add("basfgs")
        trie.remove("asdf")
        assertEquals(7, trie.size())
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
        val file = File("src/test/kotlin/homework/hw5/task1/testRead")
        trie.deserialize(file.inputStream())
        val wordsArray = mutableListOf("m", "mi", "mas", "mias", "mos")
        for (i in 0 until wordsArray.size) {
            assertTrue(trie.contains(wordsArray[i]))
        }
    }
    @Test
    fun shouldRewriteTrieWithWordsFromFile() {
        val trie = Trie()
        val firstWordsArray = mutableListOf("aa", "asdf", "bgsd")
        firstWordsArray.forEach { trie.add(it) }
        val file = File("src/test/kotlin/homework/hw5/task1/testRead")
        trie.deserialize(file.inputStream())
        val wordsArray = mutableListOf("m", "mi", "mas", "mias", "mos")
        wordsArray.forEach {
            assertTrue(trie.contains(it))
        }
        firstWordsArray.forEach {
            assertFalse(trie.contains(it))
        }
    }
}
