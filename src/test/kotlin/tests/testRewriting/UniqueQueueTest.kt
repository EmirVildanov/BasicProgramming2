package tests.testRewriting

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class UniqueQueueTest {
    @Test
    fun shouldAddIntElementToTheQueue() {
        val uniqueQueue = UniqueQueue<Int>(5)
        uniqueQueue.add(3)
        assertTrue(uniqueQueue.contains(3))
    }

    @Test
    fun shouldAddStringElementToTheQueue() {
        val uniqueQueue = UniqueQueue<String>(5)
        uniqueQueue.add("test")
        assertTrue(uniqueQueue.contains("test"))
    }

    @Test
    fun shouldThrowExceptionWhenAddingElementThatAlreadyOnTheQueue() {
        val uniqueQueue = UniqueQueue<Int>(5)
        uniqueQueue.add(3)
        assertThrows(IllegalArgumentException::class.java) {
            uniqueQueue.add(3)
        }
    }

    @Test
    fun shouldThrowExceptionWhenAddingElementInFullQueue() {
        val uniqueQueue = UniqueQueue<Int>(1)
        uniqueQueue.add(3)
        assertThrows(IllegalStateException::class.java) {
            uniqueQueue.add(5)
        }
    }

    @Test
    fun shouldAddAllStringElementsToTheQueue() {
        val uniqueQueue = UniqueQueue<String>(5)
        val testStringArray = arrayListOf("1", "2", "3")
        println(testStringArray.size)
        println(uniqueQueue.size)
        uniqueQueue.addAll(testStringArray)
        assertTrue(uniqueQueue.containsAll(arrayListOf("2", "3", "1")))
    }

    @Test
    fun shouldThrowExceptionWhenAddingElementsInQueueThatSizeTooSmall() {
        val uniqueQueue = UniqueQueue<String>(5)
        val testStringArray = arrayListOf("1", "2", "3", "4", "5", "6")
        assertThrows(IllegalStateException::class.java) {
            uniqueQueue.addAll(testStringArray)
        }
    }

    @Test
    fun shouldThrowExceptionWhenAddingElementsThatArePartiallyAlreadyInTheQueue() {
        val uniqueQueue = UniqueQueue<String>(10)
        uniqueQueue.add("3")
        uniqueQueue.add("6")
        uniqueQueue.add("test")
        val testStringArray = arrayListOf("1", "3", "5", "6")
        assertThrows(IllegalArgumentException::class.java) {
            uniqueQueue.addAll(testStringArray)
        }
    }

    @Test
    fun shouldClearQueue() {
        val uniqueQueue = UniqueQueue<String>(5)
        uniqueQueue.add("1")
        uniqueQueue.add("5")
        uniqueQueue.add("2")
        uniqueQueue.clear()
        assertTrue(uniqueQueue.isEmpty())
    }

    @Test
    fun shouldOfferIntElementToTheFullQueue() {
        val uniqueQueue = UniqueQueue<Int>(1)
        uniqueQueue.add(3)
        assertFalse(uniqueQueue.offer(4))
    }

    @Test
    fun shouldOfferIntElementToTheQueueThatContainsThatElement() {
        val uniqueQueue = UniqueQueue<Int>(5)
        uniqueQueue.add(3)
        assertFalse(uniqueQueue.offer(3))
    }

    @Test
    fun shouldCheckThatQueueStructureWorks() {
        val uniqueQueue = UniqueQueue<Int>(5)
        uniqueQueue.add(3)
        uniqueQueue.add(2)
        uniqueQueue.add(10)
        uniqueQueue.add(120)
        assertEquals(120, uniqueQueue.remove())
        assertEquals(10, uniqueQueue.remove())
        assertEquals(2, uniqueQueue.remove())
        assertEquals(3, uniqueQueue.remove())
        assertEquals(null, uniqueQueue.poll())
    }
}
