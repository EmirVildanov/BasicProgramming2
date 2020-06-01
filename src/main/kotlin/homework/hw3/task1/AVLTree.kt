package homework.hw3.task1

import java.lang.IllegalArgumentException

class AVLTree<K, V> : Map<K, V> where K : Comparable<K> {

    var root: Node<K, V>? = null
    override val entries: MutableSet<Map.Entry<K, V>> = hashSetOf()
    override val keys: MutableSet<K> = mutableSetOf()
    override val values: MutableSet<V> = mutableSetOf()
    override var size: Int = 0
    private val deleter = Deleter()

    inner class Deleter {
        private fun findNewRoot(node: Node<K, V>?): Node<K, V>? {
            if (node == null) {
                return null
            }
            var minRightElement = node.rightChild
            while (minRightElement?.leftChild != null) {
                minRightElement = minRightElement.leftChild
            }
            return minRightElement
        }

        fun deleteRoot() {
            if (root != null) {
                when {
                    root?.leftChild == null && root?.rightChild == null -> {
                        root = null
                    }
                    root?.leftChild == null -> {
                        root = root?.rightChild
                    }
                    root?.rightChild == null -> {
                        root = root?.leftChild
                    }
                    else -> {
                        val newRoot = findNewRoot(root) ?: return
                        val rememberKey = newRoot.key
                        deleteNode(rememberKey)
                        root?.key = rememberKey
                        root?.value = newRoot.value
                    }
                }
                if (root != null) {
                    root?.parent = null
                }
            }
        }

        fun deleteLeftChild(currentNode: Node<K, V>?) {
            when {
                currentNode == null -> return
                currentNode.leftChild == null && currentNode.rightChild == null -> {
                    currentNode.parent?.leftChild = null
                }
                currentNode.leftChild == null -> {
                    currentNode.parent?.leftChild = currentNode.rightChild
                    currentNode.rightChild?.parent = currentNode.parent
                }
                currentNode.rightChild == null -> {
                    currentNode.parent?.leftChild = currentNode.leftChild
                    currentNode.leftChild?.parent = currentNode.parent
                }
                else -> {
                    val newRoot = findNewRoot(currentNode) ?: return
                    val rememberKey = newRoot.key
                    deleteNode(rememberKey)
                    currentNode.key = rememberKey
                }
            }
        }

        fun deleteRightChild(currentNode: Node<K, V>?) {
            when {
                currentNode == null -> {
                    return
                }
                currentNode.leftChild == null && currentNode.rightChild == null -> {
                    currentNode.parent?.rightChild = null
                }
                currentNode.leftChild == null -> {
                    currentNode.parent?.rightChild = currentNode.rightChild
                    currentNode.rightChild?.parent = currentNode.parent
                }
                currentNode.rightChild == null -> {
                    currentNode.parent?.rightChild = currentNode.leftChild
                    currentNode.leftChild?.parent = currentNode.parent
                }
                else -> {
                    val newRoot = findNewRoot(currentNode) ?: return
                    val rememberKey = newRoot.key
                    deleteNode(rememberKey)
                    currentNode.key = rememberKey
                    currentNode.value = newRoot.value
                }
            }
        }
    }

    override fun containsKey(key: K) = keys.contains(key)

    override fun containsValue(value: V) = values.contains(value)

    override fun isEmpty(): Boolean = root == null

    override fun get(key: K): V? = root?.findNodeByKey(key)?.value

    fun put(key: K, value: V): V? {
        val newPair = Node(key, value, this)
        var insertCheck = false
        if (root == null) {
            ++size
            root = newPair
            insertCheck = true
        }
        var currentPair = root
        var answerValue: V? = null
        while (!insertCheck) {
            if (currentPair == null) {
                return null
            }
            when {
                key.compareTo(currentPair.key) == -1 -> {
                    if (currentPair.leftChild == null) {
                        ++size
                        currentPair.leftChild = newPair
                        newPair.parent = currentPair
                        newPair.balanceParents()
                        insertCheck = true
                    }
                    currentPair = currentPair.leftChild
                }
                key.compareTo(currentPair.key) == 1 -> {
                    if (currentPair.rightChild == null) {
                        ++size
                        currentPair.rightChild = newPair
                        newPair.parent = currentPair
                        newPair.balanceParents()
                        insertCheck = true
                    }
                    currentPair = currentPair.rightChild
                }
                else -> {
                    answerValue = currentPair.value
                    currentPair.value = value
                    insertCheck = true
                }
            }
        }
        entries.add(newPair)
        keys.add(key)
        values.add(value)
        return answerValue
    }

    private fun deleteNode(key: K): Node<K, V>? {
        val currentPair = root?.findNodeByKey(key) ?: return null
        when (currentPair) {
            root -> deleter.deleteRoot()
            currentPair.parent?.leftChild -> deleter.deleteLeftChild(currentPair)
            else -> deleter.deleteRightChild(currentPair)
        }
        if (currentPair.parent != null) {
            var rememberCurrentElementParent = currentPair.parent
            rememberCurrentElementParent = rememberCurrentElementParent?.balance()
            while (rememberCurrentElementParent != root) {
                rememberCurrentElementParent = rememberCurrentElementParent?.parent?.balance()
            }
        }
        return currentPair
    }

    fun remove(key: K): K? {
        val keyNode = deleteNode(key) ?: return null
        --size
        entries.remove(keyNode)
        values.remove(keyNode.value)
        keys.remove(key)
        return keyNode.key
    }

    override fun equals(other: Any?): Boolean {
        if (other !is AVLTree<*, *>) {
            throw IllegalArgumentException("Illegal argument for comparing")
        }
        if (values.size != other.size) {
            return false
        }
        var equalCheck = true
        for (i in 0 until size) {
            if (entries.elementAt(i) != other.entries.elementAt(i)) {
                equalCheck = false
            }
        }
        return equalCheck
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
