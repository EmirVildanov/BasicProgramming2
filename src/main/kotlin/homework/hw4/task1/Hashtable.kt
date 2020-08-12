package homework.hw4.task1

import org.junit.jupiter.api.Test

class Hashtable<K, V> {

    var entries = MutableList<Bucket<K, V>>(FIRST_BUCKETS_NUMBER) { Bucket(mutableSetOf()) }
        @Test get
    private val keys = mutableSetOf<K?>()
    private val values = mutableSetOf<V?>()
    var size = 0
    private var currentBucketsNumber = FIRST_BUCKETS_NUMBER

    private var loadFactor = 0.0
    private var hasher: HashFunction = FirstHashFunction()
    private var expansionNumber: Int = 0
    companion object {
        const val FIRST_BUCKETS_NUMBER = 16
        const val LOAD_FACTOR_MAXIMUM = 0.75
    }

    fun changeHashFunction(functionNumber: Int) {
        when (functionNumber) {
            1 -> this.hasher = FirstHashFunction()
            2 -> this.hasher = SecondHashFunction()
        }
        refill()
    }

    private fun refill() {
        val elements = mutableListOf<Bucket<K, V>>()
        elements.addAll(entries)
        entries.forEach { it.setNullConflictsNumber() }
        entries.clear()
        size = 0
        for (i in 0 until currentBucketsNumber) {
            entries.add(Bucket(mutableSetOf()))
        }
        elements.forEach { element ->
            element.elements.forEach {
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

    private fun indexFor(hash: Int, length: Int): Int {
        return hash and length - 1
    }

    fun put(key: K?, value: V?): V? {
        if (key == null) {
            putForNullKey(value)
            return null
        }
        var previousValue: V? = null
        val hash = hasher.hashFunction(key.hashCode())
        val position = indexFor(hash, entries.size)
        val currentBucket = entries.elementAt(position)
        currentBucket.elements.forEach { element ->
            if (element.key == key) {
                val deletingValue: V? = element.value
                element.value = value
                previousValue = deletingValue
            }
        }
        if (previousValue != null) {
            currentBucket.addElement(key, value, hash)
            keys.add(key)
            values.add(value)
            ++size
            if (loadFactor >= LOAD_FACTOR_MAXIMUM) {
                loadFactor = 0.0
                resize()
            }
            loadFactor = loadFactor()
        }
        return previousValue
    }

    private fun loadFactor() = size / entries.size.toDouble()

    private fun putForNullKey(value: V?): V? {
        entries.elementAt(0).elements.forEach { element ->
            if (element.key == null) {
                val oldValue: V? = element.value
                element.value = value
                ++size
                return oldValue
            }
        }
        entries.elementAt(0).addElement(null, value, 0)
        return null
    }

    fun remove(key: K): V? {
        if (!keys.contains(key)) {
            return null
        }
        val hash = hasher.hashFunction(key.hashCode())
        val position = indexFor(hash, entries.size)
        val currentBucket = entries.elementAt(position)
        keys.remove(key)
        val removingValue = currentBucket.remove(key)
        values.remove(removingValue)
        loadFactor = loadFactor()
        --size
        return removingValue
    }

    fun containsKey(key: K) = keys.contains(key)

    fun containsValue(value: V) = values.contains(value)

    fun get(key: K): V? {
        val hash = hasher.hashFunction(key.hashCode())
        val position = indexFor(hash, entries.size)
        val currentBucket = entries.elementAt(position)
        currentBucket.elements.forEach { element ->
            if (element.key == key) {
                return element.value
            }
        }
        return null
    }

    fun isEmpty() = size == 0

    override fun equals(other: Any?): Boolean {
        if (other !is Hashtable<*, *> || values.size != other.size) {
            return false
        }
        return entries == other.entries
    }

    override fun hashCode(): Int {
        return super.hashCode()
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
            it.elements.forEach {
                println("[${it.key} - ${it.value}]")
            }
        }
        println(" \n")
    }
}
