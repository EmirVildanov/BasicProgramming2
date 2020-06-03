package homework.hw4.task1

import java.io.File
import java.lang.IllegalArgumentException

class Hashtable<K, V> {

    val entries = mutableSetOf<Bucket<K, V>?>()
    private val keys = mutableSetOf<K>()
    private val values = mutableSetOf<V>()
    var size = 0

    private var loadFactor = 0.0
    private var hashFunction: (Int) -> Int = ::firstHashFunction
    var expansionNumber: Int = 0

    init {
        for (i in 0 until FIRST_BUCKETS_NUMBER) {
            entries.add(null)
        }
    }

    companion object {
        const val FIRST_BUCKETS_NUMBER = 16
        const val LOAD_FACTOR_MAXIMUM = 0.75
        const val HASH_NUMBER1_FOR_FIRST_HASH_FUNCTION = 20
        const val HASH_NUMBER2_FOR_FIRST_HASH_FUNCTION = 12
        const val HASH_NUMBER3_FOR_FIRST_HASH_FUNCTION = 7
        const val HASH_NUMBER4_FOR_FIRST_HASH_FUNCTION = 4
        const val HASH_NUMBER1_FOR_SECOND_HASH_FUNCTION = 63689
        const val HASH_NUMBER2_FOR_SECOND_HASH_FUNCTION = 378551
    }

    private fun firstHashFunction(hashCode: Int): Int {
        val hash = hashCode xor (hashCode ushr HASH_NUMBER1_FOR_FIRST_HASH_FUNCTION xor
                (hashCode ushr HASH_NUMBER2_FOR_FIRST_HASH_FUNCTION))
        return hash xor (hash ushr HASH_NUMBER3_FOR_FIRST_HASH_FUNCTION) xor
                (hash ushr HASH_NUMBER4_FOR_FIRST_HASH_FUNCTION)
    }

    private fun secondHashFunction(hashCode: Int): Int {
        val hash = hashCode * HASH_NUMBER1_FOR_SECOND_HASH_FUNCTION + hashCode
        return hash * HASH_NUMBER2_FOR_SECOND_HASH_FUNCTION
    }

    fun changeHashFunction(functionNumber: Int) {
        when (functionNumber) {
            1 -> this.hashFunction = ::firstHashFunction
            2 -> this.hashFunction = ::secondHashFunction
        }
        refill()
    }

    private fun refill() {
        val elements = mutableListOf<Bucket<K, V>?>()
        elements.addAll(entries)
        entries.clear()
        elements.forEach {
            it?.elements?.forEach {
                put(it?.key, it?.value)
            }
        }
    }

    private fun resize() {
        for (i in 0 until entries.size) {
            entries.add(Bucket())
        }
        this.expansionNumber += 1
        refill()
    }

    private fun indexFor(hash: Int, length: Int): Int {
        return hash and length - 1
    }

    fun put(key: K?, value: V?): V? {
        if (key == null) {
            putForNullKey(value)
        }
        val hash = firstHashFunction(key.hashCode())
        val position = indexFor(hash, entries.size)
        val currentBucket = entries.elementAt(position)
        currentBucket?.elements?.forEach { element ->
            if (element?.hash == hash && element.key == key) {
                val oldValue: V? = element.value
                element.value = value
                ++size
                return oldValue
            }
        }
        currentBucket?.addElement(key, value, hash)
        if (loadFactor >= LOAD_FACTOR_MAXIMUM) {
            resize()
        }
        loadFactor = size.toDouble() / entries.size.toDouble()
        return null
    }

    private fun putForNullKey(value: V?): V? {
        entries.elementAt(0)?.elements?.forEach { element ->
            if (element?.key == null) {
                val oldValue: V? = element?.value
                element?.value = value
                ++size
                return oldValue
            }
        }
        entries.elementAt(0)?.addElement(null, value, 0)
        return null
    }

    // Works only for Hashtable<String, String>
    fun putFile(file: File) {
        val bufferedReader = file.bufferedReader()
        val elements = mutableSetOf<Bucket<K, V>.BucketElement>()
        bufferedReader.useLines {
            lines -> lines.forEach { line ->
                val currentLineElements = line.split(" ")
                currentLineElements.forEach { pair ->
                    val regex = Regex("\\[(.+), (.+)]")
                    if (!regex.containsMatchIn(pair)) {
                        throw IllegalArgumentException("Wrong data file")
                    }
                    val key = regex.find(pair)?.groupValues?.get(1)
                    val value = regex.find(pair)?.groupValues?.get(2)
                    this.put(key, value)
                }
            }
        }
        elements.forEach { this.put(it.key, it.value) }
    }

    fun remove(key: K) {
        if (!keys.contains(key)) {
            return
        }
        entries.forEach {
            if (it?.contains(key) == true) {
                val hash = firstHashFunction(key.hashCode())
                val position = indexFor(hash, entries.size)
                val currentBucket = entries.elementAt(position)
                entries.remove(currentBucket)
                it.remove()
            }
        }
        keys.remove(key)
        loadFactor = size.toDouble() / entries.size.toDouble()
    }

    fun containsKey(key: K) = keys.contains(key)

    fun containsValue(value: V) = values.contains(value)

    fun get(key: K): V? {
        val hash = firstHashFunction(key.hashCode())
        val position = indexFor(hash, entries.size)
        val currentBucket = entries.elementAt(position)
        currentBucket?.elements?.forEach { element ->
            if (element?.hash == hash && element.key == key) {
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
        var equalCheck = true
        for (i in 0 until size) {
            if (entries.elementAt(i) != other.entries.elementAt(i)) {
                equalCheck = false
                break
            }
        }
        return equalCheck
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun printStatistics() {
        println("Load factor is : $loadFactor")
        println("The number of expansions is : $expansionNumber")
        var maxConflictsNumber = 0
        entries.forEach {
            if (it?.conflictsNumber ?: 0 > maxConflictsNumber) {
                maxConflictsNumber = it?.conflictsNumber ?: 0
            }
        }
        println("The max number of searching attempts is : $maxConflictsNumber")
        print("Number of conflicts in each bucket: ")
        entries.forEach { print("${it?.conflictsNumber} ") }
        println("")
        println("The number of added words is : $size")
        println("The number of empty buckets is : ${entries.size - size}")
        println("Elements are: ")
        entries.forEach {
            it?.elements?.forEach {
                if (it != null) {
                    print("[${it.key} - ${it.value}]")
                }
            }
        }
        println(" \n")
    }
}
