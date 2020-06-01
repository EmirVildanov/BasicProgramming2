package homework.hw3.task1

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AVLTreeTest {

    @Test
    fun shouldTestFindingNodeByKey() {
        val testTree = AVLTree<Int, String>()
        testTree.put(2, "Me")
        testTree.put(3, "Me")
        testTree.put(4, "Be")
        testTree.put(5, "Fe")
        val testNode = Node(5, "Fe", testTree)
        assertEquals(testNode, testTree.root?.findNodeByKey(5))
    }
    @Test
    fun shouldAssertFalseTreesWithDifferentSizes() {
        val testTree = AVLTree<Int, String>()
        val anotherTestTree = AVLTree<Int, String>()
        anotherTestTree.put(2, "Me")
        anotherTestTree.put(3, "Me")
        testTree.put(1, "Me")
        assertFalse(testTree == anotherTestTree)
    }
    @Test
    fun shouldAssertFalseTreesWithDifferentKeys() {
        val testTree = AVLTree<Int, String>()
        val anotherTestTree = AVLTree<Int, String>()
        anotherTestTree.put(2, "Me")
        testTree.put(1, "Me")
        assertFalse(testTree == anotherTestTree)
    }
    @Test
    fun shouldAssertFalseTreesWithDifferentValues() {
        val testTree = AVLTree<Int, String>()
        val anotherTestTree = AVLTree<Int, String>()
        anotherTestTree.put(1, "Not Me")
        testTree.put(1, "Me")
        assertFalse(testTree == anotherTestTree)
    }
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
        testList.forEach {
            testTree.put(it.key, it.value)
        }
        testList.forEach() {
            assertTrue(testTree.containsKey(it.key))
        }
    }
    @Test
    fun shouldChangeNodeWithNewValue() {
        val testTree = AVLTree<Int, String>()
        val anotherTestTree = AVLTree<Int, String>()
        anotherTestTree.put(1, "Be")
        anotherTestTree.put(1, "Me")
        testTree.put(1, "Me")
        assertTrue(testTree == anotherTestTree)
    }
    @Test
    fun shouldTestRemovingNode() {
        val testTree = AVLTree<Int, String>()
        val anotherTestTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.remove(1)
        assertTrue(testTree == anotherTestTree)
    }
    @Test
    fun shouldClearTree() {
        val testTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.put(2, "Mama")
        testTree.put(3, "Papa")
        for (i in 0 until testTree.size) {
            testTree.remove(i + 1)
        }
        assertTrue(testTree.isEmpty())
    }
}
