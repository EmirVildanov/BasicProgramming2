package homework.hw4.task1.hashFunction

class SecondHashFunction : HashFunction {
    companion object {
        const val HASH_NUMBER1_FOR_SECOND_HASH_FUNCTION = 63689
        const val HASH_NUMBER2_FOR_SECOND_HASH_FUNCTION = 378551
    }
    override fun hashFunction(hashCode: Int): Int {
        val hash = hashCode * HASH_NUMBER1_FOR_SECOND_HASH_FUNCTION + hashCode
        return hash * HASH_NUMBER2_FOR_SECOND_HASH_FUNCTION
    }
}
