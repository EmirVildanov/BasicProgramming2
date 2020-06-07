package homework.hw4.task1

class Bucket<K, V> {
    var conflictsNumber = 0
    private val keys = mutableSetOf<K?>()
    var elements = hashSetOf<BucketElement?>()

    inner class BucketElement(
        var key: K?,
        var value: V?,
        var hash: Int
    )

    fun addElement(key: K?, value: V?, hash: Int) {
        elements.add(BucketElement(key, value, hash))
        keys.add(key)
        ++conflictsNumber
    }
    fun remove() {
        elements.clear()
        keys.clear()
        conflictsNumber = 0
    }

    fun contains(key: K?) = keys.contains(key)

    override fun equals(other: Any?): Boolean {
        if (other !is Bucket<*, *> || elements.size != other.elements.size) {
            return false
        }
        var equalCheck = true
        for (i in 0 until elements.size) {
            if (elements.elementAt(i) != other.elements.elementAt(i)) {
                equalCheck = false
                break
            }
        }
        return equalCheck
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
