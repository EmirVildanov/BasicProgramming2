package homework.hw4.task1.hashFunction

interface HashFunction<K> {
    fun getHash(element: K): Int
}
