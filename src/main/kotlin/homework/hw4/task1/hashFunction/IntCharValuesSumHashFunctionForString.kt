package homework.hw4.task1.hashFunction

class IntCharValuesSumHashFunctionForString : HashFunction<String> {
    override fun getHash(element: String): Int {
        return element.map { it.toInt() }.sum()
    }
}
