package homework.hw3.task1

import java.lang.IllegalArgumentException

class AVLTree<K, V> : Map<K, V> where K : Comparable<K> {

    var root: Node<K, V>? = null
    override val entries: MutableSet<Node<K, V>> = mutableSetOf()
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
        fun delete(node: Node<K, V>, side: String) {
            when {
                node.leftChild != null && node.rightChild != null -> {
                    val newRoot = findNewRoot(node)
                        ?: throw IllegalCallerException("Invalid situation: node has children")
                    deleteNode(newRoot.key)
                    node.key = newRoot.key
                    node.value = newRoot.value
                }
                else -> {
                    when (side) {
                        "root" -> deleteAsRoot()
                        "left" -> deleteAsLeftChild(node)
                        "right" -> deleteAsRightChild(node)
                        else -> throw IllegalArgumentException("Wrong side argument")
                    }
                }
            }
        }

        private fun deleteAsRoot() {
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
            }
            if (root != null) {
                root?.parent = null
            }
        }

        private fun deleteAsLeftChild(currentNode: Node<K, V>) {
            when {
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
            }
        }

        private fun deleteAsRightChild(currentNode: Node<K, V>) {
            when {
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
            }
        }
    }

    override fun containsKey(key: K) = keys.contains(key)

    override fun containsValue(value: V) = values.contains(value)

    override fun isEmpty(): Boolean = root == null

    override fun get(key: K): V? = root?.findNodeByKey(key)?.value

    fun put(key: K, value: V): V? {
        val newPair = Node(key, value)
        var insertCheck = false
        if (root == null) {
            ++size
            root = newPair
            insertCheck = true
        }
        var currentPair = root ?: throw IllegalArgumentException("Null root")
        var answerValue: V? = null
        while (!insertCheck) {
            when {
                key.compareTo(currentPair.key) == -1 -> {
                    if (currentPair.leftChild == null) {
                        ++size
                        currentPair.leftChild = newPair
                        newPair.parent = currentPair
                        newPair.balanceParents(this)
                        insertCheck = true
                    } else {
                        currentPair = currentPair.leftChild ?: throw IllegalArgumentException("Null leftChild")
                    }
                }
                key.compareTo(currentPair.key) == 1 -> {
                    if (currentPair.rightChild == null) {
                        ++size
                        currentPair.rightChild = newPair
                        newPair.parent = currentPair
                        newPair.balanceParents(this)
                        insertCheck = true
                    } else {
                        currentPair = currentPair.rightChild ?: throw IllegalArgumentException("Null rightChild")
                    }
                }
                else -> {
                    answerValue = currentPair.value
                    values.remove(answerValue)
                    entries.removeIf { it.key == key }
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
            root -> deleter.delete(root ?: throw IllegalArgumentException("Null root pointer"), "root")
            currentPair.parent?.leftChild -> deleter.delete(currentPair, "left")
            else -> deleter.delete(currentPair, "right")
        }
        if (currentPair.parent != null) {
            var rememberCurrentElementParent = currentPair.parent
            rememberCurrentElementParent = rememberCurrentElementParent?.balance(this)
            while (rememberCurrentElementParent != root) {
                rememberCurrentElementParent = rememberCurrentElementParent?.parent?.balance(this)
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
//        var equalCheck = true
//        for (i in 0 until size) {
//            if (entries.elementAt(i) != other.entries.elementAt(i)) {
//                equalCheck = false
//            }
//        }
        return entries == other.entries
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
