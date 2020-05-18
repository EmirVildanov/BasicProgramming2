package homework.hw3.task1

import java.lang.IllegalArgumentException

class AVLTree<K, V> : Map<K, V> where K : Comparable<K> {

    companion object {
        private const val BALANCE_FACTOR_MINUS_TWO = -2
        private const val BALANCE_FACTOR_ERROR_VALUE = -3
        private const val BALANCE_FACTOR_TWO = 2
    }

    private var root: Node<K, V>? = null
    override val entries: MutableSet<Map.Entry<K, V>> = mutableSetOf()
    override val keys: MutableSet<K> = mutableSetOf()
    override var size: Int = 0
    override val values: MutableList<V> = mutableListOf()

    data class Node<K, V>(
        override var key: K,
        override var value: V
    ) : Map.Entry<K, V> {

        var height: Int = 1
        var leftChild: Node<K, V>? = null
        var rightChild: Node<K, V>? = null
        var parent: Node<K, V>? = null
    }

    inner class Balancer {
        fun findNewRoot(node: Node<K, V>?): Node<K, V>? {
            if (node == null) {
                return null
            }
            var minRightElement = node.rightChild
            while (minRightElement?.leftChild != null) {
                minRightElement = minRightElement.leftChild
            }
            return minRightElement
        }

        private fun getNodeHeight(node: Node<K, V>?): Int = node?.height ?: 0

        private fun getNodeBalanceFactor(node: Node<K, V>?): Int =
            getNodeHeight(node?.rightChild) - getNodeHeight(node?.leftChild) ?: BALANCE_FACTOR_ERROR_VALUE

        private fun updateHeight(node: Node<K, V>?) {
            if (node == null) {
                return
            }
            val rightChildHeight = getNodeHeight(node.rightChild)
            val leftChildHeight = getNodeHeight(node.leftChild)
            if (rightChildHeight > leftChildHeight) {
                node.height = rightChildHeight + 1
            } else {
                node.height = leftChildHeight + 1
            }
        }

        private fun rotateRight(node: Node<K, V>): Node<K, V> {
            if (node == null) {
                return null
            }
            //, side: KProperty<Pair<K, V>>
            val newNode = node.leftChild
            newNode?.parent = node.parent
            node.leftChild = newNode?.rightChild
            if (node.leftChild != null) {
                node.leftChild?.parent = node
            }
            when (node) {
                root -> return newNode
                node.parent?.leftChild -> node.parent?.leftChild = newNode
                else -> node.parent?.rightChild = newNode
            }
            newNode?.rightChild = node
            node.parent = newNode
            updateHeight(node)
            updateHeight(newNode)
            return newNode
        }

        private fun rotateLeft(node: Node<K, V>): Node<K, V> {
            val newNode = node.rightChild
            newNode?.parent = node.parent
            node.rightChild = newNode?.leftChild
            if (node.rightChild != null) {
                node.rightChild?.parent = node
            }
            when (node) {
                root -> root = newNode
                node.parent?.leftChild -> node.parent?.leftChild = newNode
                else -> node.parent?.rightChild = newNode
            }
            newNode?.leftChild = node
            node.parent = newNode
            updateHeight(node)
            updateHeight(newNode)
            return newNode
        }

        fun balance(node: Node<K, V>): Node<K, V> {
            return when (getNodeBalanceFactor(node)) {
                BALANCE_FACTOR_TWO -> {
                    if (getNodeBalanceFactor(node.rightChild) < 0) {
                        node.rightChild = rotateRight(node.rightChild!!)
                    }
                    rotateLeft(node)
                }
                Companion.BALANCE_FACTOR_MINUS_TWO -> {
                    if (getNodeBalanceFactor(node.leftChild) > 0) {
                        node.leftChild = rotateLeft(node.leftChild!!)
                    }
                    rotateRight(node)
                }
                else -> {
                    node
                }
            }
        }

        fun balanceParents(node: Node<K, V>) {
            var currentPair = node
            while (currentPair != root) {
                balance(currentPair.parent!!)
                if (currentPair != root) {
                    currentPair = currentPair.parent!!
                }
            }
        }
    }

    private fun processPrint(node: Node<K, V>?, currentString: String): String {
        if (node == null) {
            currentString += "null "
        } else {
            currentString += "("
            currentString += "${node.key} "
            processPrint(node.leftChild, currentString)
            processPrint(node.rightChild, currentString)
            currentString += ") "
        }
        return currentString
    }

    override fun toString(): String {
        var currentString = ""
        return processPrint(root, currentString) + "\n"
    }

    inner class Deleter {
        fun findElementToDelete(value: K): Node<K, V>? {
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

        fun deleteLeftChild(currentNode: Node<K, V>?) {
            if (currentNode == null) {
                return
            }
            if (currentNode.leftChild == null && currentNode.rightChild == null) {
                currentNode.parent?.leftChild = null
                --size
            } else if (currentNode.leftChild == null) {
                currentNode.parent?.leftChild = currentNode.rightChild
                currentNode.rightChild?.parent = currentNode.parent
                --size
            } else if (currentNode.rightChild == null) {
                currentNode.parent?.leftChild = currentNode.leftChild
                currentNode.leftChild?.parent = currentNode.parent
                --size
            } else {
                val newRoot = Balancer().findNewRoot(currentNode) ?: return
                val rememberKey = newRoot.key
                remove(rememberKey)
                currentNode.key = rememberKey
            }
        }

        fun deleteRightChild(currentNode: Node<K, V>?) {
            if (currentNode == null) {
                return
            }
            if (currentNode.leftChild == null && currentNode.rightChild == null) {
                currentNode.parent?.rightChild = null
                --size
            } else if (currentNode.leftChild == null) {
                currentNode.parent?.rightChild = currentNode.rightChild
                currentNode.rightChild?.parent = currentNode.parent
                --size
            } else if (currentNode.rightChild == null) {
                currentNode.parent?.rightChild = currentNode.leftChild
                currentNode.leftChild?.parent = currentNode.parent
                --size
            } else {
                val newRoot = Balancer().findNewRoot(currentNode) ?: return
                val rememberKey = newRoot.key
                remove(rememberKey)
                currentNode.key = rememberKey
                currentNode.value = newRoot.value
            }
        }
    }

    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun containsValue(value: V): Boolean = values.contains(value)

    override fun isEmpty(): Boolean = root == null

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
        val newPair = Node(key, value)
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
            other.values.forEach {
                if (!values.contains(it)) {
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
