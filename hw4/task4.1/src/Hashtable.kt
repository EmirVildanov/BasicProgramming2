import java.io.File
import kotlin.math.abs

/*
Реализовать класс для работы с хеш-таблицей (на списках).
Общение с пользователем должно происходит в интерактивном режиме:
добавить значение в хеш-таблицу, удалить значение из хеш-таблицы,
 поиск значения в хеш-таблице, показать статистику по хеш-таблице
 (общее число ячеек, load factor, число конфликтов, максимальная длина списка в конфликтных ячейках и т.п.), з
 аполнить хеш-таблицу содержимым файла,
 выбрать хеш-функцию для подсчета хеша (из заранее заданных в коде).
  Смена хэш-функции должна происходить во время работы программы,
в класс используемая хеш-функция должна передаваться из клиентского кода.
 */

const val firstBucketsNumber = 20
const val hashPrimeNumber = 3

fun firstHashFunction(string: String, size: Int): Int {
    var numberPower = 1;
    var hashNumber = 0;
    for (element in string) {
        hashNumber += (element - 'a' + 1) * numberPower
        numberPower *= hashPrimeNumber
    }
    return abs(hashNumber % size); //abs because in case of big words it will return negative answer
}

//fun secondHashFunction(string: String, size: Int): Int {
//    var numberPower = 1;
//    var hashNumber = 0;
//    for (element in string)
//    {
//        hashNumber += (element - 'a' + 1) * numberPower
//        numberPower *= hashPrimeNumber
//    }
//    return abs(hashNumber % size); //abs because in case of big words it will return negative answer
//}

class Hashtable() {
    private var bucketsArray = ArrayList<Bucket>(firstBucketsNumber)
    private var loadFactor = 0
    private var hashFunction: (String, Int) -> Int = ::firstHashFunction
    private var size = firstBucketsNumber
    private var wordsNumber = 0
    private val attemptsArray = mutableListOf<Int>() //for statistics
    private val expansionNumber: Int = 0//how many times we reallocated our array


    class Bucket(var value: String) {
        var valuePower = 0
        fun isEmpty(): Boolean {
            return value == "Empty"
        }

    }

    private fun redefineHashValues() {
        val wordsArray = ArrayList<Bucket>(wordsNumber);
        var arrayIndex = 0;
        var hashValue = 0;
        for (i in 0 until size) {
            val currentString = bucketsArray[i];
            if (!currentString.isEmpty()) {
                bucketsArray[i] = Bucket("Empty")
                wordsArray[arrayIndex] = currentString;
                ++arrayIndex;
            }
        }
        for (i in 0 until wordsNumber) {
            val currentWord = wordsArray[i]
            hashValue = hashFunction(currentWord.value, size)
            var attemptsNumber = 1
            var insertCheck = false
            while (!insertCheck) {
                while (hashValue + attemptsNumber < size) {
                    if (bucketsArray[hashValue + attemptsNumber].isEmpty()) {
                        bucketsArray[hashValue + attemptsNumber] = currentWord
                        insertCheck = true;
                        break;
                    }
                    hashValue += attemptsNumber * attemptsNumber;
                }
                hashValue %= size;
                attemptsNumber = 0;
            }
        }
    }

    private fun startInsertProcess(currentWord: String) {
        var hashIndex = hashFunction(currentWord, size)
        var attemptsNumber = 1
        var rememberAttemptsNumber = 1
        var insertCheck = false;
        while (!insertCheck) {
            while (hashIndex + attemptsNumber < size) {
                if (bucketsArray[hashIndex + attemptsNumber].value == currentWord) {
                    bucketsArray[hashIndex + attemptsNumber].valuePower += 1
                    attemptsArray[attemptsArray.size - 1] = rememberAttemptsNumber;
                    insertCheck = true;
                    break;
                } else if (bucketsArray[hashIndex + attemptsNumber].isEmpty()) {
                    bucketsArray[hashIndex + attemptsNumber].value = currentWord;
                    attemptsArray[attemptsArray.size - 1] = rememberAttemptsNumber
                    ++wordsNumber
                    insertCheck = true
                    break;
                }
                ++attemptsNumber
                hashIndex += attemptsNumber * attemptsNumber;
            }
            hashIndex = hashIndex % size;
            rememberAttemptsNumber += attemptsNumber;
            attemptsNumber = 0;
        }
    }

    fun add(currentWord: String) {
        while (loadFactor >= 0.7) {
            redefineHashValues();
        }
        attemptsArray.add(0)
        startInsertProcess(currentWord);
        loadFactor = wordsNumber / size
    }

    fun addFromFile(file: File) {

    }

    fun remove(word: String) {

    }

    fun findVale(word: String) {

    }

    fun changeHashFunction(functionNumber: Int) {

    }

//functions to print statistics

    private fun findMaxAttemptsNumber(): Int {
        var maxNumber = 0;
        for (i in 0 until attemptsArray.size) {
            if (attemptsArray[i] > maxNumber) {
                maxNumber = attemptsArray[i];
            }
        }
        return maxNumber;
    }

    private fun findAverageAttemptsNumber(): Float {
        val averageNumber: Float
        var sum = 0
        for (i in 0 until attemptsArray.size) {
            sum += attemptsArray[i]
        }
        averageNumber = (sum / attemptsArray.size).toFloat()
        return averageNumber
    }

    private fun printWords() {
        for (i in 0 until size) {
            if (!bucketsArray[i].isEmpty()) {
                val charWord = bucketsArray[i];
                println("$charWord, ${bucketsArray[i].valuePower}")
            }
        }
    }

    fun printStatistics() {
        println("Load factor is : $loadFactor")
        println("The number of extensions is : $expansionNumber")
        println("The max number of searching attempts is : ${findMaxAttemptsNumber()}")
        println("The average number of searching attempts is : ${findAverageAttemptsNumber()}")
        attemptsArray.forEach { print(it) }
        println("The number of added words is : $wordsNumber")
        println("The number of empty buckets is : ${size - wordsNumber}")
        printWords();
        println("");
    }
}