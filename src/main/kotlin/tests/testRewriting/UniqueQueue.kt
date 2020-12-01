package tests.testRewriting

import java.lang.IllegalArgumentException
import java.util.Queue

class UniqueQueue<T>(override val size: Int) : Queue<T> {
    private val elements = mutableListOf<T>()

    override fun add(element: T): Boolean {
        if (elements.size == size) {
            throw IllegalStateException("The queue is full")
        }
        if (elements.contains(element)) {
            throw IllegalArgumentException("Element is already on the queue")
        }
        elements.add(element)
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        if (this.elements.size + elements.size > size) {
            throw IllegalStateException("There is no space for all adding elements")
        }
        elements.forEach {
            if (this.elements.contains(it)) {
                throw IllegalArgumentException("Element already is on the queue")
            }
            this.elements.add(it)
        }
        return true
    }

    override fun clear() {
        elements.clear()
    }

    override fun iterator(): MutableIterator<T> {
        return elements.iterator()
    }

    override fun remove(): T {
        if (isEmpty()) {
            throw IllegalStateException("The queue is empty")
        }
        val removingElement = elements.first()
        elements.remove(removingElement)
        return removingElement
    }

    override fun contains(element: T): Boolean {
        return elements.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return this.elements.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return elements.size == 0
    }

    override fun remove(element: T): Boolean {
        return elements.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return this.elements.removeAll(elements)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return this.elements.retainAll(elements)
    }

    override fun offer(e: T): Boolean {
        if (elements.size == size || elements.contains(e)) {
            return false
        }
        elements.add(e)
        return true
    }

    override fun poll(): T? {
        if (isEmpty()) {
            return null
        }
        val removingElement = elements.first()
        elements.remove(removingElement)
        return removingElement
    }

    override fun element(): T {
        if (isEmpty()) {
            throw IllegalStateException("The queue is empty")
        }
        return elements.first()
    }

    override fun peek(): T? {
        if (isEmpty()) {
            return null
        }
        return elements.first()
    }
}
