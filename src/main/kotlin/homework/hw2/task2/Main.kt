
fun deleteRepeatingElements(array: MutableList<Int>): MutableList<Int> {
    return array.reversed().toSet().reversed().toMutableList()
}

fun main() {
    print("Enter elements of the array : ")
    val array = readLine()?.split(' ')?.map { it.toInt() }?.toMutableList()
    if (array != null) {
        print("Your array after transformation is : ")
        deleteRepeatingElements(array).forEach { print("$it ") }
    } else {
        println("Input array is null")
    }
}
