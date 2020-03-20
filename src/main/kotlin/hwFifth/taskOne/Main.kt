fun sum(a: Int, b: Int): Int {
    return a + b
}

fun main() {
    val trie = Trie()
    trie.add("hi")
    trie.add("his")
    trie.add("she")
    println(trie.contains("hi"))
    println(trie.contains("his"))
    println(trie.contains("she"))
}
