package homework.hw4.task1

class FirstHashFunction : HashFunction {
    companion object {
        const val HASH_NUMBER1_FOR_FIRST_HASH_FUNCTION = 20
        const val HASH_NUMBER2_FOR_FIRST_HASH_FUNCTION = 12
        const val HASH_NUMBER3_FOR_FIRST_HASH_FUNCTION = 7
        const val HASH_NUMBER4_FOR_FIRST_HASH_FUNCTION = 4
    }
    override fun hashFunction(hashCode: Int): Int {
        val hash = hashCode xor (hashCode ushr HASH_NUMBER1_FOR_FIRST_HASH_FUNCTION xor
                (hashCode ushr HASH_NUMBER2_FOR_FIRST_HASH_FUNCTION))
        return hash xor (hash ushr HASH_NUMBER3_FOR_FIRST_HASH_FUNCTION) xor
                (hash ushr HASH_NUMBER4_FOR_FIRST_HASH_FUNCTION)
    }
}
