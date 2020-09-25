package homework.hw4.task1

data class Bucket<K, V>(
    private var elements: MutableSet<Bucket<K, V>.BucketElement>
) {
    var conflictsNumber = 0
    private val keys = mutableSetOf<K?>()

    inner class BucketElement(
        var key: K?,
        var value: V?,
        var hashCode: Int
    ) {

        override fun toString(): String {
            return "$key $value $hashCode"
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Bucket<*, *>.BucketElement) {
                return false
            }
            return this.key == other.key && this.value == other.value
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }

    fun addElement(key: K?, value: V?, hashCode: Int) {
        elements.add(BucketElement(key, value, hashCode))
        keys.add(key)
        ++conflictsNumber
    }

    fun remove(key: K?): V? {
        var removingValue: V? = null
        for (i in 0 until elements.size) {
            if (elements.elementAt(i).key == key) {
                removingValue = elements.elementAt(i).value
                elements.remove(elements.elementAt(i))
                break
            }
        }
        keys.remove(key)
        return removingValue
    }

    fun setNullConflictsNumber() {
        this.conflictsNumber = 0
    }

    fun contains(key: K?) = keys.contains(key)

    fun getElements() = elements

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
