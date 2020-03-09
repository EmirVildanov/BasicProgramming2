class AVLTree<K, V>(
    override val entries: Set<Map.Entry<K, V>>,
    override val keys: Set<K> = setOf(),
    override val size: Int,
    override val values: Collection<V> = arrayListOf()
) : Map<K, V> {

//    class Entry<K, V> : Entry<K, V> {
//        /**
//         * Returns the key of this key/value pair.
//         */
//        public val key: K
//
//        /**
//         * Returns the value of this key/value pair.
//         */
//        public val value: V
//    }
    
    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun containsValue(value: V): Boolean {
        return values.contains(value)
    }

    override fun get(key: K): V? {
        TODO("not implemented")
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    fun put(key: K, value: V): V? {

    }

    fun putAll(from: Map<out K, V>) {

    }

    fun remove(key: K): V? {
        TODO("Blah")
    }

    fun clear() {

    }

    private fun balance() {

    }
}