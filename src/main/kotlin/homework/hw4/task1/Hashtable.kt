package homework.hw4.task1

import homework.hw4.task1.hashFunction.HashFunction

class Hashtable<K, V>(
    private var hashFunction: HashFunction<K>,
    firstBucketsNumber: Int = 16,
    private var maxLoadFactor: Double = 0.75
) {

    private var entries = MutableList<Bucket<K, V>>(firstBucketsNumber) { Bucket(mutableSetOf()) }
    private val keys = mutableSetOf<K?>()
    private val values = mutableSetOf<V?>()
    var size = 0
    private var currentBucketsNumber = firstBucketsNumber
    private var loadFactor = 0.0
    private var expansionNumber: Int = 0

    fun changeHashFunction(newHashFunction: HashFunction<K>) {
        hashFunction = newHashFunction
        refill()
    }

    private fun refill() {
        val buckets = mutableListOf<Bucket<K, V>>()
        buckets.addAll(entries)
        entries.forEach { it.setNullConflictsNumber() }
        entries.clear()
        size = 0
        for (i in 0 until currentBucketsNumber) {
            entries.add(Bucket(mutableSetOf()))
        }
        buckets.forEach { bucket ->
            bucket.getElements().forEach {
                put(it.key, it.value)
            }
        }
    }

    private fun resize() {
        for (i in 0 until entries.size) {
            entries.add(Bucket(mutableSetOf()))
        }
        currentBucketsNumber = entries.size
        this.expansionNumber += 1
        refill()
    }

    fun put(key: K?, value: V?): V? {
        if (key == null) {
            putForNullKey(value)
            return null
        }
        var previousValue: V? = null
        val hash = hashFunction.getHash(key)
        val position = hash % entries.size
        val currentBucket = entries.elementAt(position)
        currentBucket.getElements().forEach { element ->
            if (element.key == key) {
                val deletingValue: V? = element.value
                element.value = value
                previousValue = deletingValue
            }
        }
        if (previousValue == null) {
            currentBucket.addElement(key, value, hash)
            keys.add(key)
            values.add(value)
            ++size
            if (loadFactor >= maxLoadFactor) {
                loadFactor = 0.0
                resize()
            }
            loadFactor = loadFactor()
        }
        return previousValue
    }

    private fun loadFactor() = size / entries.size.toDouble()

    private fun putForNullKey(value: V?): V? {
        entries.elementAt(0).getElements().forEach { element ->
            if (element.key == null) {
                val oldValue: V? = element.value
                element.value = value
                return oldValue
            }
        }
        entries.elementAt(0).addElement(null, value, 0)
        ++size
        return null
    }

    fun remove(key: K): V? {
        if (!keys.contains(key)) {
            return null
        }
        val hash = hashFunction.getHash(key)
        val position = hash % entries.size
        val currentBucket = entries.elementAt(position)
        keys.remove(key)
        val removingValue = currentBucket.remove(key)
        values.remove(removingValue)
        loadFactor = loadFactor()
        --size
        return removingValue
    }

    fun getNotEmptyEntries() = entries.filter { it.getElements().size > 0 }

    fun get(key: K): V? {
        val hash = hashFunction.getHash(key)
        val position = hash % entries.size
        val currentBucket = entries.elementAt(position)
        currentBucket.getElements().forEach { element ->
            if (element.key == key) {
                return element.value
            }
        }
        return null
    }

    fun printStatistics() {
        println("Load factor is : $loadFactor")
        println("The number of expansions is : $expansionNumber")
        println("The max number of searching attempts is : ${entries.map { it.conflictsNumber }.max()}")
        print("Number of conflicts in each bucket: ")
        entries.forEach { print("${it.conflictsNumber} ") }
        println("\nThe number of added words is : $size")
        println("The number of empty buckets is : ${entries.size - size}")
        println("Elements are: ")
        entries.forEach { it ->
            it.getElements().forEach {
                println("[${it.key} - ${it.value}]")
            }
        }
        println(" \n")
    }
}
