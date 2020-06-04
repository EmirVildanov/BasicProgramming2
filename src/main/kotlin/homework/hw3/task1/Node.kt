package homework.hw3.task1

import java.lang.IllegalArgumentException
import java.lang.Integer.max

data class Node<K, V>(
    override var key: K,
    override var value: V
) : Map.Entry<K, V> where K : Comparable<K> {
    private var height: Int = 1
    var leftChild: Node<K, V>? = null
    var rightChild: Node<K, V>? = null
    var parent: Node<K, V>? = null

    companion object {
        private const val BALANCE_FACTOR_MINUS_TWO = -2
        private const val BALANCE_FACTOR_TWO = 2
    }

    fun findNodeByKey(key: K): Node<K, V>? {
        var currentPair: Node<K, V>? = this
        while (true) {
            currentPair = when {
                currentPair == null -> return null
                key == currentPair.key -> return currentPair
                key < currentPair.key -> currentPair.leftChild
                else -> currentPair.rightChild
            }
        }
    }
    private fun getBalanceFactor(): Int =
        (rightChild?.height ?: 0) - (leftChild?.height ?: 0)

    private fun updateHeight() {
        height = max((rightChild?.height ?: 0), (leftChild?.height ?: 0)) + 1
    }

    private fun rotateRight(tree: AVLTree<K, V>): Node<K, V> {
        val newNode = leftChild ?: throw IllegalArgumentException("Nothing to rotate, null leftChild")
        newNode.parent = parent
        leftChild = newNode.rightChild
        if (leftChild != null) {
            leftChild?.parent = this
        }
        when (this) {
            tree.root -> tree.root = newNode
            parent?.leftChild -> parent?.leftChild = newNode
            else -> parent?.rightChild = newNode
        }
        newNode.rightChild = this
        parent = newNode
        this.updateHeight()
        newNode.updateHeight()
        return newNode
    }

    private fun rotateLeft(tree: AVLTree<K, V>): Node<K, V> {
        val newNode = rightChild ?: throw IllegalArgumentException("Nothing to rotate, null rightChild")
        newNode.parent = parent
        rightChild = newNode.leftChild
        if (rightChild != null) {
            rightChild?.parent = this
        }
        when (this) {
            tree.root -> tree.root = newNode
            parent?.leftChild -> parent?.leftChild = newNode
            else -> parent?.rightChild = newNode
        }
        newNode.leftChild = this
        parent = newNode
        this.updateHeight()
        newNode.updateHeight()
        return newNode
    }

    fun balance(tree: AVLTree<K, V>): Node<K, V> {
        return when (this.getBalanceFactor()) {
            BALANCE_FACTOR_TWO -> {
                if (rightChild?.getBalanceFactor() ?: 0 < 0) {
                    rightChild = rightChild?.rotateRight(tree)
                }
                this.rotateLeft(tree)
            }
            BALANCE_FACTOR_MINUS_TWO -> {
                if (leftChild?.getBalanceFactor() ?: 0 > 0) {
                    leftChild = leftChild?.rotateLeft(tree)
                }
                this.rotateRight(tree)
            }
            else -> {
                this.updateHeight()
                this
            }
        }
    }

    fun balanceParents(tree: AVLTree<K, V>) {
        var currentPair: Node<K, V>? = this
        while (currentPair != tree.root) {
            currentPair?.parent?.balance(tree)
            if (currentPair != tree.root) {
                currentPair = currentPair?.parent
            }
        }
    }
}
