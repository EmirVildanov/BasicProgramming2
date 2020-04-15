import java.io.File
import kotlin.math.abs

/*
Общение с пользователем должно происходит в интерактивном режиме:
добавить значение в хеш-таблицу, удалить значение из хеш-таблицы,
 поиск значения в хеш-таблице, показать статистику по хеш-таблице,
 заполнить хеш-таблицу содержимым файла,
 выбрать хеш-функцию для подсчета хеша (из заранее заданных в коде).
  */

const val firstBucketsNumber = 20
const val hashPrimeNumberForFirstHashFunction = 3
const val firstHelpValueForSecondHashFunction = 63689
const val secondHelpValueForSecondHashFunction = 378551

class Hashtable() {
    private var bucketsArray = mutableListOf<Bucket>()
    private var loadFactor: Float = 0F
    private var hashFunction: (String) -> Int = ::firstHashFunction
    private var size = firstBucketsNumber
    private val words = mutableSetOf<String>()
    private var expansionNumber: Int = 0

    init {
        for (i in 0 until firstBucketsNumber) {
            this.bucketsArray.add(Bucket())
        }
    }

    class Bucket() {
        var conflictsNumber = 0
        var words = mutableListOf<String>()
    }

    private fun firstHashFunction(string: String): Int {
        var numberPower = 1;
        var hashNumber = 0;
        for (element in string) {
            hashNumber += (element - 'a' + 1) * numberPower
            numberPower *= hashPrimeNumberForFirstHashFunction
        }
        //abs because in case of big words it will return negative answer
        return abs(hashNumber % bucketsArray.size);
    }

    private fun secondHashFunction(string: String): Int {
        var firstHashHelpNumber = firstHelpValueForSecondHashFunction
        var hashNumber = 0

        string.forEach {
            hashNumber = hashNumber * firstHashHelpNumber + it.toInt()
            firstHashHelpNumber *= secondHelpValueForSecondHashFunction
        }
        //abs because in case of big words it will return negative answer
        return abs(hashNumber % bucketsArray.size)
    }

    private fun redefineHashValues() {
        val wordsArray = ArrayList<String>(words.size);
        bucketsArray.forEach {
            if (!it.words.isEmpty()) {
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
        if (loadFactor >= 0.9) {
            for (i in 0 until firstBucketsNumber) {
                bucketsArray.add(Bucket())
            }
            this.expansionNumber += 1
            redefineHashValues()
        }
        val hashIndex = hashFunction(currentWord)
        if (bucketsArray[hashIndex].words.isNotEmpty()) {
            bucketsArray[hashIndex].conflictsNumber += 1
        }
        bucketsArray[hashIndex].words.add(currentWord)
        words.add(currentWord)
        loadFactor = words.size.toFloat() / bucketsArray.size.toFloat()
    }

    fun addFromFile(file: File) {
        val bufferedReader= file.bufferedReader()
        val words = mutableSetOf<String>()
        bufferedReader.useLines { lines -> lines.forEach { words.addAll(it.split(" ")) } }
        words.forEach { this.add(it) }
    }

    fun remove(word: String): Boolean {
        if (!words.contains(word))
        {
            return false
        }
        bucketsArray.forEach {
            if (it.words.contains(word)) {
                it.words.remove(word)
            }
        }
        words.remove(word)
        loadFactor = words.size.toFloat() / bucketsArray.size.toFloat()
        return true
    }

    fun changeHashFunction(functionNumber: Int) {
        when (functionNumber) {
            1 -> this.hashFunction = ::firstHashFunction
            2 -> this.hashFunction = ::secondHashFunction
        }
        redefineHashValues()
    }

    fun find(word: String): Boolean {
        return words.contains(word)
    }

//functions to print statistics

    private fun findMaxConflictsNumber(): Int {
        var maxNumber = 0;
        bucketsArray.forEach {
            if (it.conflictsNumber > maxNumber) {
                maxNumber = it.conflictsNumber
            }
        }
        return maxNumber;
    }

    fun printStatistics() {
        println("Load factor is : $loadFactor")
        println("The number of expansions is : $expansionNumber")
        println("The max number of searching attempts is : ${findMaxConflictsNumber()}")
        print("Number of conflicts in each bucket: ")
        bucketsArray.forEach { print("${it.conflictsNumber} ") }
        println("")
        println("The number of added words is : ${words.size}")
        println("The number of empty buckets is : ${bucketsArray.size - words.size}")
        println("Words are: ")
        bucketsArray.forEach { it.words.forEach { print("$it ") } }
        println(" \n")
    }
}