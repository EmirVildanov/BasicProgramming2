class AVLTree<K, V>(
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
    ) : Map.Entry<K, V> {

    }

    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun containsValue(value: V): Boolean {
        return values.contains(value)
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun get(key: K): V? {
        if (root == null) {
            return null
        }
        var currentPair = root;
        while (true) {
            if (currentPair == null) {
                return null
            }
            if (currentPair.key == key) {
                return currentPair.value
            } else if (key < currentPair.key) {
                currentPair = currentPair.leftChild;
            } else {
                currentPair = currentPair.rightChild;
            }
        }
    }

    fun put(key: K, value: V): V? {
        val newPair = Pair(key, value)
        if (isEmpty()) {
            root = newPair
            entries.add(newPair)
            size++
            return null
        }
        var currentPair = root
        while (true) {
            if (currentPair == null) {
                return null
            }
            if (key.compareTo(currentPair.key) == -1)//<
            {
                if (currentPair.leftChild == null) {
                    currentPair.leftChild = newPair
                    newPair.parent = currentPair
                    balanceParents(newPair)
                    ++size
                    entries.add(newPair)
                    return null
                }
                currentPair = currentPair.leftChild
            } else if (key.compareTo(currentPair.key) == 1)//>
            {
                if (currentPair.rightChild == null) {
                    currentPair.rightChild = newPair
                    newPair.parent = currentPair
                    balanceParents(newPair)
                    size++
                    entries.add(newPair)
                    return null
                }
                currentPair = currentPair.rightChild
            } else//=
            {
                val rememberValue = currentPair.value
                currentPair.value = value
                return rememberValue
            }
        }
    }

    fun putAll(from: Map<out K, V>) {
        from.forEach { this.put(it.key, it.value) }
    }

    private fun findNewRoot(pair: Pair<K, V>?): Pair<K, V>? {
        if (pair == null) {
            return null;
        }
        var minRightElement = pair.rightChild;
        while (minRightElement?.leftChild != null) {
            minRightElement = minRightElement.leftChild;
        }
        return minRightElement;
    }

    private fun findHeight(pair: Pair<K, V>?): Int {
        if (pair == null) {
            return 0;
        }
        return pair.height;
    }

    private fun findBalanceFactor(pair: Pair<K, V>?): Int {
        if (pair == null) {
            return -3
        }
        return findHeight(pair.rightChild) - findHeight(pair.leftChild)
    }

    private fun updateHeight(pair: Pair<K, V>?) {
        if (pair == null) {
            return;
        }
        val rightChildHeight = findHeight(pair.rightChild);
        val leftChildHeight = findHeight(pair.leftChild);
        if (rightChildHeight > leftChildHeight) {
            pair.height = rightChildHeight + 1;
        } else {
            pair.height = leftChildHeight + 1;
        }
    }

    private fun rotateRight(pair: Pair<K, V>?): Pair<K, V>? {
        if (pair == null) {
            return null;
        }
        val newNode = pair.leftChild;
        newNode?.parent = pair.parent;
        pair.leftChild = newNode?.rightChild;
        if (pair.leftChild != null) {
            pair.leftChild?.parent = pair;
        }
        if (pair == root) {
            root = newNode;
        } else if (pair == pair.parent?.leftChild) {
            pair.parent?.leftChild = newNode;
        } else {
            pair.parent?.rightChild = newNode;
        }
        newNode?.rightChild = pair;
        pair.parent = newNode;
        updateHeight(pair);
        updateHeight(newNode);
        return newNode;
    }

    private fun rotateLeft(pair: Pair<K, V>?): Pair<K, V>? {
        if (pair == null) {
            return null;
        }
        val newNode = pair.rightChild;
        newNode?.parent = pair.parent;
        pair.rightChild = newNode?.leftChild;
        if (pair.rightChild != null) {
            pair.rightChild?.parent = pair;
        }
        if (pair == root) {
            root = newNode;
        } else if (pair == pair.parent?.leftChild) {
            pair.parent?.leftChild = newNode;
        } else {
            pair.parent?.rightChild = newNode;
        }
        newNode?.leftChild = pair;
        pair.parent = newNode;
        updateHeight(pair);
        updateHeight(newNode);
        return newNode;
    }

    private fun balance(pair: Pair<K, V>?): Pair<K, V>? {
        if (pair == null) {
            return null;
        }
        updateHeight(pair);
        val balanceFactor = findBalanceFactor(pair);
        if (balanceFactor == 2) {
            if (findBalanceFactor(pair.rightChild) < 0) {
                pair.rightChild = rotateRight(pair.rightChild);
            }
            return rotateLeft(pair)
        }
        if (balanceFactor == -2) {
            if (findBalanceFactor(pair.leftChild) > 0) {
                pair.leftChild = rotateLeft(pair.leftChild);
            }
            return rotateRight(pair)
        }
        return pair
    }

    private fun balanceParents(pair: Pair<K, V>?) {
        var currentPair = pair
        while (currentPair?.parent?.key != null) {
            balance(currentPair.parent)
            currentPair = currentPair.parent
        }
    }

    private fun findElementToDelete(value: K): Pair<K, V>? {
        var currentPair = root
        while (true) {
            if (currentPair == null) {
                return null;
            }
            if (value == currentPair.key) {
                break;
            } else if (value < currentPair.key) {
                currentPair = currentPair.leftChild;
            } else {
                currentPair = currentPair.rightChild;
            }
        }
        return currentPair;
    }

    private fun deleteRoot(currentPair: Pair<K, V>?) {
        if (currentPair == null) {
            return
        }
        if (currentPair.leftChild == null && currentPair.rightChild == null) {
            root = null;
            --size;
        } else if (currentPair.leftChild == null) {
            root = currentPair.rightChild;
            size;
        } else if (currentPair.rightChild == null) {
            root = currentPair.leftChild;
            size
        } else {
            val newRoot = findNewRoot(currentPair) ?: return;
            val rememberKey = newRoot.key;
            remove(rememberKey);
            currentPair.key = rememberKey;
            currentPair.value = newRoot.value
        }
    }

    private fun deleteLeftChild(currentPair: Pair<K, V>?) {
        if (currentPair == null) {
            return
        }
        if (currentPair.leftChild == null && currentPair.rightChild == null) {
            currentPair.parent?.leftChild = null;
            --size;
        } else if (currentPair.leftChild == null) {
            currentPair.parent?.leftChild = currentPair.rightChild;
            currentPair.rightChild?.parent = currentPair.parent;
            --size;
        } else if (currentPair.rightChild == null) {
            currentPair.parent?.leftChild = currentPair.leftChild;
            currentPair.leftChild?.parent = currentPair.parent;
            --size;
        } else {
            val newRoot = findNewRoot(currentPair) ?: return;
            val rememberKey = newRoot.key;
            remove(rememberKey);
            currentPair.key = rememberKey;
        }
    }

    private fun deleteRightChild(currentPair: Pair<K, V>?) {
        if (currentPair == null) {
            return
        }
        if (currentPair.leftChild == null && currentPair.rightChild == null) {
            currentPair.parent?.rightChild = null;
            --size;
        } else if (currentPair.leftChild == null) {
            currentPair.parent?.rightChild = currentPair.rightChild;
            currentPair.rightChild?.parent = currentPair.parent;
            --size;
        } else if (currentPair.rightChild == null) {
            currentPair.parent?.rightChild = currentPair.leftChild;
            currentPair.leftChild?.parent = currentPair.parent;
            --size;
        } else {
            val newRoot = findNewRoot(currentPair) ?: return
            val rememberKey = newRoot.key;
            remove(rememberKey);
            currentPair.key = rememberKey;
            currentPair.value = newRoot.value
        }
    }

    fun remove(key: K): K? {
        val currentPair = findElementToDelete(key); //Finding element
        val currentKey = currentPair?.key
        var rememberCurrentElementParent =
            currentPair?.parent;//we need it to balance parents after deletion current element
        if (currentPair == root)//If the element is a root
        {
            deleteRoot(currentPair);
        } else if (currentPair == currentPair?.parent?.leftChild)//If the element is parent's left child
        {
            deleteLeftChild(currentPair);
        } else//if element is parent's right child
        {
            deleteRightChild(currentPair);
        }
        rememberCurrentElementParent = balance(rememberCurrentElementParent);
        if (rememberCurrentElementParent == null) {
            return currentPair?.key;
        }
        while (rememberCurrentElementParent?.parent != null) {
            rememberCurrentElementParent = balance(rememberCurrentElementParent.parent);
        }
        return currentKey
    }

    fun clear() {
        entries.forEach { remove(it.key) }
    }

    private fun processPrint(pair: Pair<K, V>?) {
        if (pair == null)
        {
            print("null ")
        }
        else
        {
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
