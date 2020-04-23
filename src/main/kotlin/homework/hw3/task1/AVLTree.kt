package homework.hw3.task1

import java.lang.IllegalArgumentException

const val BALANCE_FACTOR_ERROR_VALUE = -3
const val BALANCE_FACTOR_TWO = 2
const val BALANCE_FACTOR_MINUS_TWO = -2

open class AVLTree<K, V>(
    private var root: Pair<K, V>? = null,
    override val entries: MutableSet<Map.Entry<K, V>> = mutableSetOf(),
    override val keys: MutableSet<K> = mutableSetOf(),
    override var size: Int = 0,
    override val values: MutableList<V> = mutableListOf()
) : Map<K, V> where K : Comparable<K> {

    class Pair<K, V>(
        override var key: K,
        override var value: V,
        var height: Int = 1,
        var leftChild: Pair<K, V>? = null,
        var rightChild: Pair<K, V>? = null,
        var parent: Pair<K, V>? = null
    ) : Map.Entry<K, V>

    inner class Balancer {
        fun findNewRoot(pair: Pair<K, V>?): Pair<K, V>? {
            if (pair == null) {
                return null
            }
            var minRightElement = pair.rightChild
            while (minRightElement?.leftChild != null) {
                minRightElement = minRightElement.leftChild
            }
            return minRightElement
        }

        private fun findHeight(pair: Pair<K, V>?): Int {
            if (pair == null) {
                return 0
            }
            return pair.height
        }

        private fun findBalanceFactor(pair: Pair<K, V>?): Int {
            if (pair == null) {
                return BALANCE_FACTOR_ERROR_VALUE
            }
            return findHeight(pair.rightChild) - findHeight(pair.leftChild)
        }

        private fun updateHeight(pair: Pair<K, V>?) {
            if (pair == null) {
                return
            }
            val rightChildHeight = findHeight(pair.rightChild)
            val leftChildHeight = findHeight(pair.leftChild)
            if (rightChildHeight > leftChildHeight) {
                pair.height = rightChildHeight + 1
            } else {
                pair.height = leftChildHeight + 1
            }
        }

        private fun rotate(pair: Pair<K, V>, side: String): Pair<K, V> {
            if (side != "left" && side != "right") {
                throw IllegalArgumentException("Wrong side argument")
            }
            val newNode: Pair<K, V>?
            if (side == "left") {
                newNode = pair.rightChild
                pair.rightChild = newNode?.leftChild
                if (pair.rightChild != null) {
                    pair.rightChild!!.parent = pair
                }
                when (pair) {
                    root -> root = newNode
                    pair.parent?.leftChild -> pair.parent?.leftChild = newNode
                    else -> pair.parent?.rightChild = newNode
                }
                newNode?.leftChild = pair
            } else {
                newNode = pair.leftChild
                pair.leftChild = newNode?.rightChild
                if (pair.leftChild != null) {
                    pair.leftChild!!.parent = pair
                }
                when (pair) {
                    root -> return newNode!!
                    pair.parent?.leftChild -> pair.parent?.leftChild = newNode
                    else -> pair.parent?.rightChild = newNode
                }
                newNode?.rightChild = pair
            }
            newNode?.parent = pair.parent
            pair.parent = newNode
            updateHeight(pair)
            updateHeight(newNode)
            return newNode!!
        }

        fun balance(pair: Pair<K, V>): Pair<K, V> {
            updateHeight(pair)
            return when (findBalanceFactor(pair)) {
                BALANCE_FACTOR_TWO -> {
                    if (findBalanceFactor(pair.rightChild) < 0) {
                        pair.rightChild = rotate(pair.rightChild!!, "right")
                    }
                    rotate(pair, "left")
                }
                BALANCE_FACTOR_MINUS_TWO -> {
                    if (findBalanceFactor(pair.leftChild) > 0) {
                        pair.leftChild = rotate(pair.leftChild!!, "left")
                    }
                    rotate(pair, "right")
                }
                else -> {
                    pair
                }
            }
        }

        fun balanceParents(pair: Pair<K, V>) {
            var currentPair = pair
            while (currentPair != root) {
                balance(currentPair.parent!!)
                if (currentPair != root) {
                    currentPair = currentPair.parent!!
                }
            }
        }
    }

    inner class Printer {
        private fun processPrint(pair: Pair<K, V>?) {
            if (pair == null) {
                print("null ")
            } else {
                print("(")
                print("${pair.key} ")
                processPrint(pair.leftChild)
                processPrint(pair.rightChild)
                print(") ")
            }
        }

        fun print() {
            print("The hole set : ")
            processPrint(root)
            print("\n")
        }
    }

    inner class Deleter {
        fun findElementToDelete(value: K): Pair<K, V>? {
            var currentPair = root
            loop@ while (true) {
                currentPair = when {
                    currentPair == null -> return null
                    value == currentPair.key -> break@loop
                    value < currentPair.key -> currentPair.leftChild
                    else -> currentPair.rightChild
                }
            }
            return currentPair
        }

        fun deleteRoot() {
            if (root != null) {
                if (root!!.leftChild == null && root!!.rightChild == null) {
                    root = null
                    --size
                } else if (root!!.leftChild == null) {
                    root = root!!.rightChild
                    --size
                } else if (root!!.rightChild == null) {
                    root = root!!.leftChild
                    --size
                } else {
                    val newRoot = Balancer().findNewRoot(root) ?: return
                    val rememberKey = newRoot.key
                    remove(rememberKey)
                    root!!.key = rememberKey
                    root!!.value = newRoot.value
                }
                if (root != null) {
                    root!!.parent = null
                }
            }
        }

        fun deleteLeftChild(currentPair: Pair<K, V>?) {
            if (currentPair == null) {
                return
            }
            if (currentPair.leftChild == null && currentPair.rightChild == null) {
                currentPair.parent?.leftChild = null
                --size
            } else if (currentPair.leftChild == null) {
                currentPair.parent?.leftChild = currentPair.rightChild
                currentPair.rightChild?.parent = currentPair.parent
                --size
            } else if (currentPair.rightChild == null) {
                currentPair.parent?.leftChild = currentPair.leftChild
                currentPair.leftChild?.parent = currentPair.parent
                --size
            } else {
                val newRoot = Balancer().findNewRoot(currentPair) ?: return
                val rememberKey = newRoot.key
                remove(rememberKey)
                currentPair.key = rememberKey
            }
        }

        fun deleteRightChild(currentPair: Pair<K, V>?) {
            if (currentPair == null) {
                return
            }
            if (currentPair.leftChild == null && currentPair.rightChild == null) {
                currentPair.parent?.rightChild = null
                --size
            } else if (currentPair.leftChild == null) {
                currentPair.parent?.rightChild = currentPair.rightChild
                currentPair.rightChild?.parent = currentPair.parent
                --size
            } else if (currentPair.rightChild == null) {
                currentPair.parent?.rightChild = currentPair.leftChild
                currentPair.leftChild?.parent = currentPair.parent
                --size
            } else {
                val newRoot = Balancer().findNewRoot(currentPair) ?: return
                val rememberKey = newRoot.key
                remove(rememberKey)
                currentPair.key = rememberKey
                currentPair.value = newRoot.value
            }
        }
    }

    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun containsValue(value: V): Boolean {
        return values.contains(value)
    }

    override fun isEmpty(): Boolean {
        return root == null
    }

    override fun get(key: K): V? {
        var currentPair = root
        while (true) {
            currentPair = when {
                currentPair == null -> return null
                currentPair == key -> return currentPair.value
                key < currentPair.key -> currentPair.leftChild
                else -> currentPair.rightChild
            }
        }
    }

    fun put(key: K, value: V): V? {
        val newPair = Pair(key, value)
        var checkInsert = false
        if (root == null) {
            root = newPair
            entries.add(newPair)
            ++size
            checkInsert = true
        }
        var currentPair = root
        var answerValue: V? = null
        while (!checkInsert) {
            if (currentPair == null) {
                return null
            }
            when {
                key.compareTo(currentPair.key) == -1 -> {
                    if (currentPair.leftChild == null) {
                        currentPair.leftChild = newPair
                        newPair.parent = currentPair
                        Balancer().balanceParents(newPair)
                        ++size
                        entries.add(newPair)
                        answerValue = null
                        checkInsert = true
                    }
                    currentPair = currentPair.leftChild
                }
                key.compareTo(currentPair.key) == 1 -> {
                    if (currentPair.rightChild == null) {
                        currentPair.rightChild = newPair
                        newPair.parent = currentPair
                        Balancer().balanceParents(newPair)
                        ++size
                        entries.add(newPair)
                        answerValue = null
                        checkInsert = true
                    }
                    currentPair = currentPair.rightChild
                }
                else -> {
                    answerValue = currentPair.value
                    currentPair.value = value
                    checkInsert = true
                }
            }
        }
        keys.add(key)
        return answerValue
    }

    fun putAll(from: Map<out K, V>) {
        from.forEach { this.put(it.key, it.value) }
    }

    fun remove(key: K): K? {
        val currentPair = Deleter().findElementToDelete(key) ?: return null
        when (currentPair) {
            root -> Deleter().deleteRoot()
            currentPair.parent!!.leftChild -> Deleter().deleteLeftChild(currentPair)
            else -> Deleter().deleteRightChild(currentPair)
        }
        if (currentPair.parent != null) {
            var rememberCurrentElementParent = currentPair.parent!!
            rememberCurrentElementParent = Balancer().balance(rememberCurrentElementParent)
            while (rememberCurrentElementParent != root) {
                rememberCurrentElementParent = Balancer().balance(rememberCurrentElementParent.parent!!)
            }
        }
        return currentPair.key
    }

    fun clear() {
        entries.forEach { remove(it.key) }
    }

    override fun equals(other: Any?): Boolean {
        var equalCheck = true
        if (other is AVLTree<*, *>) {
            if (values.size != other.size) {
                equalCheck = false
            }
            other.values.forEach { if (!values.contains(it)) {
                    equalCheck = false
                }
            }
        } else {
            throw IllegalArgumentException()
        }
        return equalCheck
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
