package hwFifth.taskOne

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @Test
    fun shouldAddOneWordToTrie() {
        val trie = Trie()
        trie.add("ba")
        assertTrue(trie.contains("ba"))
    }
}