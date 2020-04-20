package homework.hw3.task1

import AVLTree
import org.junit.jupiter.api.Test

class TestComparableClass(private var age: Int, var name: String) : Comparable<TestComparableClass> {
    override fun compareTo(other: TestComparableClass): Int {
        return if (this.age < other.age) -1 else if (this.age == other.age) 0 else 1
    }
}

internal class AVLTreeTest {

    @Test
    fun shouldDoNothingWithTreeAsTestingRemovingOfOneELement() {
        val testTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.remove(1)
    }
    @Test
    fun shouldAddAllElementsFromTheList() {
        val testTree = AVLTree<Int, String>()
        val testList = mapOf(Pair(1, "ba"), Pair(2, "ma"))
        testTree.putAll(testList)
    }
    @Test
    fun shouldAddOneElementInTree() {
        val testTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.put(2, "Mama")
        testTree.put(3, "Papa")
        testTree.put(4, "Brother")
        testTree.put(5, "Ala")
        testTree.print()
        testTree.put(6, "Bob")
        testTree.print()
    }
    @Test
    fun shouldWorkWithHandWrittenClass() {
        val testTree = AVLTree<TestComparableClass, String>()
        val man1 = TestComparableClass(16, "Ban")
        val man2 = TestComparableClass(17, "Angela")
        val man3 = TestComparableClass(18, "Kate")
        val man4 = TestComparableClass(19, "John")
        testTree.put(man1, "Va")
        testTree.put(man2, "Ka")
        testTree.put(man3, "Bu")
        testTree.put(man4, "Ba")
    }
    @Test
    fun shouldClearTree() {
        val testTree = AVLTree<Int, String>()
        testTree.put(1, "Me")
        testTree.put(2, "Mama")
        testTree.put(3, "Papa")
        testTree.clear()
    }
}
