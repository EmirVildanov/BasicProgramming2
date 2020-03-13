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

class Hashtable() {

    const val firstBucketsNumber = 20
    const val hashPrimeNumber = 3

    private var bucketsArray = ArrayList<Bucket>(firstBucketsNumber)
    private var loadFactor = 0
    private var size = firstBucketsNumber
    private val wordsNumber = 0
    private val attemptsArray = ArrayList() //for statistics
    private val expansionNumber: Int //how many times we reallocated our array

    class Bucket() {

        fun isEmpty(currentString): Boolean {
            if (currentString == null) {
                return true;
            }
            val check = "Empty"
            if (compare(currentString, check)) {
                return true;
            }
            return false;
        }

    }

    private fun redefineHashValues() {
        val wordsArray = createStringArray(hashtable.wordsNumber);
        var arrayIndex = 0;
        var hashValue = 0;
        for (i in 0 until previousSize) {
            String * currentString = hashtable.bucketsArray[i];
            if (!isEmptyBucket(currentString)) {
                hashtable.bucketsArray[i] = createString("Empty");
                wordsArray[arrayIndex] = currentString;
                ++arrayIndex;
            }
        }
        for (i in 0 until wordsNumber)
        {
            var currentWord = wordsArray[i];
            hashValue = hashFunction(hashtable, currentWord);
            val attemptsNumber = 1;
            bool insertCheck = false;
            while (!insertCheck) {
                while (hashValue + attemptsNumber < hashtable.size) {
                    if (isEmptyBucket(hashtable.bucketsArray[hashValue + attemptsNumber])) {
                        deleteString(hashtable.bucketsArray[hashValue + attemptsNumber]);
                        hashtable.bucketsArray[hashValue + attemptsNumber] = currentWord;
                        insertCheck = true;
                        break;
                    }
                    ++attemptsNumber;
                    hashValue = hashValue + attemptsNumber * attemptsNumber;
                }
                hashValue = hashValue % hashtable.size;
                attemptsNumber = 0;
            }
        }
        free(wordsArray);
        return hashtable;
    }

//    String **extendBucketsArray(Hashtable *hashtable)
//    {
//        if (hashtable == null) {
//            return null;
//        }
//        int newLength = firstBucketsNumber *(hashtable.expansionNumber+2);
//        hashtable.bucketsArray = realloc(hashtable.bucketsArray, newLength * sizeof(String *));
//        int rememberSize = hashtable.size;
//        hashtable.size = newLength;
//        initializeBucketsArray(hashtable, rememberSize, hashtable.size);
//        ++hashtable.expansionNumber;
//        hashtable.loadFactor = (float) hashtable.wordsNumber / (float) hashtable.size;
//        return hashtable.bucketsArray;
//    }

    private fun startInsertProcess(String* currentWord)
    {
        if (hashtable == null) {
            return null;
        }
        int hashIndex = hashFunction (hashtable, currentWord);
        int attemptsNumber = 1;
        int rememberAttemptsNumber = 1;
        bool insertCheck = false;
        while (!insertCheck) {
            while (hashIndex + attemptsNumber < hashtable.size) {
                if (compare(hashtable.bucketsArray[hashIndex + attemptsNumber], currentWord)) {
                    changeStringPower(
                        hashtable.bucketsArray[hashIndex + attemptsNumber],
                        getStringPower(hashtable.bucketsArray[hashIndex + attemptsNumber]) + 1
                    );
                    hashtable.attemptsArray[hashtable.attemptsArraySize - 1] = rememberAttemptsNumber;
                    insertCheck = true;
                    deleteString(currentWord);
                    break;
                } else if (isEmptyBucket(hashtable.bucketsArray[hashIndex + attemptsNumber])) {
                    deleteString(hashtable.bucketsArray[hashIndex + attemptsNumber]);
                    hashtable.bucketsArray[hashIndex + attemptsNumber] = currentWord;
                    hashtable.attemptsArray[hashtable.attemptsArraySize - 1] = rememberAttemptsNumber;
                    ++hashtable.wordsNumber;
                    insertCheck = true;
                    break;
                }
                ++attemptsNumber;
                hashIndex = hashIndex + attemptsNumber * attemptsNumber;
            }
            hashIndex = hashIndex % hashtable.size;
            rememberAttemptsNumber += attemptsNumber;
            attemptsNumber = 0;
        }
        return hashtable;
    }

    private fun insertNewWord(String *currentWord)
    {
        while (hashtable.loadFactor >= 0.7) {
            int rememberSize = hashtable . size;
            hashtable.bucketsArray = extendBucketsArray(hashtable);
            hashtable = redefineHashValues(hashtable, rememberSize);
        }
        hashtable.attemptsArray = realloc(hashtable.attemptsArray, (hashtable.attemptsArraySize + 1) * sizeof(int));
        hashtable.attemptsArray[hashtable.attemptsArraySize] = 0;
        ++hashtable.attemptsArraySize;
        hashtable = startInsertProcess(hashtable, currentWord);
        hashtable.loadFactor = (float) hashtable . wordsNumber /(float) hashtable . size;
    }

//functions to print statistics

    private fun findMaxAttemptsNumber(): Int {
        int maxNumber = 0;
        for (int i = 0; i < hashtable.attemptsArraySize; ++i)
        {
            if (hashtable.attemptsArray[i] > maxNumber) {
                maxNumber = hashtable.attemptsArray[i];
            }
        }
        return maxNumber;
    }

    private fun findAverageAttemptsNumber(): Float {
        if (hashtable == null) {
            return -1;
        }
        float averageNumber = 0;
        int sum = 0;
        for (int i = 0; i < hashtable.attemptsArraySize; ++i)
        {
            sum += hashtable.attemptsArray[i];
        }
        averageNumber = (float) sum /(float) hashtable . attemptsArraySize;
        return averageNumber;
    }

    fun printHashtableWords(Hashtable *hashtable)
    {
        if (hashtable == null) {
            return;
        }
        for (int i = 0; i < hashtable.size; ++i)
        {
            if (!isEmptyBucket(hashtable.bucketsArray[i])) {
                char * charWord = toCharPtr(hashtable.bucketsArray[i]);
                println("%s . %d", charWord, getStringPower(hashtable.bucketsArray[i]));
                free(charWord);
            }
        }
    }

    fun printStatistics(Hashtable *hashtable)
    {
        println("Load factor is : %f ", hashtable.loadFactor);
        println("The number of extensions is : %d ", hashtable.expansionNumber);
        println("The max number of searching attempts is : %d ", findMaxAttemptsNumber(hashtable));
        println("The average number of searching attempts is : %f ", findAverageAttemptsNumber(hashtable));
        printIntArray(hashtable.attemptsArray, hashtable.attemptsArraySize);
        println("The number of added words is : %d ", hashtable.wordsNumber);
        println("The number of empty buckets is : %d ", hashtable.size - hashtable.wordsNumber);
        printHashtableWords(hashtable);
        println("");
    }
}