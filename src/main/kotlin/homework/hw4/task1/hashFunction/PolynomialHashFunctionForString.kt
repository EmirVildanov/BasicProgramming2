package homework.hw4.task1.hashFunction

import kotlin.math.abs
import kotlin.math.pow

class PolynomialHashFunctionForString : HashFunction<String> {
    companion object {
        const val PRIME_NUMBER = 17569.0
    }
    override fun getHash(element: String): Int {
        return abs(element.mapIndexed { i, it -> (it.toInt() * PRIME_NUMBER.pow(i)).toInt() }.sum())
    }
}
