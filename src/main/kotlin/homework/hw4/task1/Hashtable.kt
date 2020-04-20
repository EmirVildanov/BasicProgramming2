package homework.hw4.task1

import java.io.File
import kotlin.math.abs

const val FIRST_BUCKETS_NUMBER = 20
const val LOAD_FACTOR_MAXIMUM = 0.9
const val HASH_PRIME_NUMBER_FOR_FIRST_HASH_FUNCTION = 3
const val FIRST_HELP_VALUE_FOR_FIRST_HASH_FUNCTION = 63689
const val SECOND_HASH_HELP_VALUE_FOR_FIRST_HASH_FUNCTION = 378551

class Hashtable {
    private var bucketsArray = mutableListOf<Bucket>()
    private var loadFactor = 0.0
    var hashFunction: (String) -> Int = ::firstHashFunction
    val words = mutableSetOf<String>()
    var expansionNumber: Int = 0

    init {
        for (i in 0 until FIRST_BUCKETS_NUMBER) {
            this.bucketsArray.add(Bucket())
        }
    }

    class Bucket {
        var conflictsNumber = 0
        var words = mutableListOf<String>()
    }

    private fun firstHashFunction(string: String): Int {
        var numberPower = 1
        var hashNumber = 0
        for (element in string) {
            hashNumber += (element - 'a' + 1) * numberPower
            numberPower *= HASH_PRIME_NUMBER_FOR_FIRST_HASH_FUNCTION
        }
        return abs(hashNumber % bucketsArray.size)
    }

    private fun secondHashFunction(string: String): Int {
        var firstHashHelpNumber = FIRST_HELP_VALUE_FOR_FIRST_HASH_FUNCTION
        var hashNumber = 0

        string.forEach {
            hashNumber = hashNumber * firstHashHelpNumber + it.toInt()
            firstHashHelpNumber *= SECOND_HASH_HELP_VALUE_FOR_FIRST_HASH_FUNCTION
        }
        return abs(hashNumber % bucketsArray.size)
    }

    private fun redefineHashValues() {
        val wordsArray = ArrayList<String>(words.size)
        bucketsArray.forEach {
            if (it.words.isNotEmpty()) {
                wordsArray.addAll(it.words)
                it.words.clear()
                it.conflictsNumber = 0
            }
        }
        wordsArray.forEach {
            this.add(it)
        }
    }

    fun add(currentWord: String) {
        if (loadFactor >= LOAD_FACTOR_MAXIMUM) {
            for (i in 0 until FIRST_BUCKETS_NUMBER) {
                bucketsArray.add(Bucket())
            }
            this.expansionNumber += 1
            loadFactor = words.size.toDouble() / bucketsArray.size.toDouble()
            redefineHashValues()
        }
        val hashIndex = hashFunction(currentWord)
        if (bucketsArray[hashIndex].words.isNotEmpty()) {
            bucketsArray[hashIndex].conflictsNumber += 1
        }
        bucketsArray[hashIndex].words.add(currentWord)
        words.add(currentWord)
        loadFactor = words.size.toDouble() / bucketsArray.size.toDouble()
    }

    fun addFromFile(file: File) {
        val bufferedReader = file.bufferedReader()
        val words = mutableSetOf<String>()
        bufferedReader.useLines { lines -> lines.forEach { words.addAll(it.split(" ")) } }
        words.forEach { this.add(it) }
    }

    fun remove(word: String): Boolean {
        if (!words.contains(word)) {
            return false
        }
        bucketsArray.forEach {
            if (it.words.contains(word)) {
                it.words.remove(word)
            }
        }
        words.remove(word)
        loadFactor = words.size.toDouble() / bucketsArray.size.toDouble()
        return true
    }

    fun changeHashFunction(functionNumber: Int) {
        when (functionNumber) {
            1 -> this.hashFunction = ::firstHashFunction
            2 -> this.hashFunction = ::secondHashFunction
        }
        redefineHashValues()
    }

    fun printStatistics() {
        println("Load factor is : $loadFactor")
        println("The number of expansions is : $expansionNumber")
        var maxConfictsNumber = 0
        bucketsArray.forEach {
            if (it.conflictsNumber > maxConfictsNumber) {
                maxConfictsNumber = it.conflictsNumber
            }
        }
        println("The max number of searching attempts is : $maxConfictsNumber")
        print("Number of conflicts in each bucket: ")
        bucketsArray.forEach { print("${it.conflictsNumber} ") }
        println("")
        println("The number of added words is : ${words.size}")
        println("The number of empty buckets is : ${bucketsArray.size - words.size}")
        println("Words are: ")
        bucketsArray.forEach { it -> it.words.forEach { print("$it ") } }
        println(" \n")
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Hashtable) {
            this.words.containsAll(words)
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
