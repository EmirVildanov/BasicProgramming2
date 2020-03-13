class Man(var age: Int, var name: String) : Comparable<Man> {
    fun sayName() {
        println("My name is : $name")
    }

    override fun compareTo(other: Man): Int {
        return if (this.age < other.age) -1 else if (this.age == other.age) 0 else 1
    }
}

fun main()  {
    val testTree = AVLTree<Man, String>()
    val man1 = Man(16, "Ban")
    val man2 = Man(17, "Angela")
    val man3 = Man(18, "Kate")
    val man4 = Man(19, "John")
    testTree.put(man1, "Va")
    testTree.put(man2, "Ka")
    testTree.put(man3, "Bu")
    testTree.put(man4, "Ba")
}