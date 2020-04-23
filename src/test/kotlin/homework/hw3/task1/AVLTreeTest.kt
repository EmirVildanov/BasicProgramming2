package homework.hw3.task1

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AVLTreeTest {

    @Test
    fun shouldDoNothingWithTreeAsTestingRemovingOfOneElement() {
        val testTree = AVLTree<Int, String>()
        val anotherTestTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.remove(1)
        assertTrue(testTree == anotherTestTree)
    }
    @Test
    fun shouldAddAllElementsFromTheList() {
        val testTree = AVLTree<Int, String>()
        val testList = mapOf(Pair(1, "ba"), Pair(2, "ma"))
        testTree.putAll(testList)
        testList.forEach() {
            assertTrue(testTree.containsKey(it.key))
        }
    }
    @Test
    fun shouldClearTree() {
        val testTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.put(2, "Mama")
        testTree.put(3, "Papa")
        testTree.clear()
        assertTrue(testTree.isEmpty())
    }
}
