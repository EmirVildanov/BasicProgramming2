import java.util.*
import kotlin.collections.ArrayList

fun deleteRepeatingElements(array: ArrayList<Int>) {
    array.reverse()
    var index: Int
    for (i in 0 until array.size) {
        if (i == array.size) {
            break
        }
        index = i + 1
        while (index < array.size) {
            if (array[i] == array[index]) {
                array.removeAt(index)
                index--
            }
            index++
        }
    }
    array.reverse()
}

fun main() {
    val scan = Scanner(System.`in`)
    print("Enter the length of your array : ")
    val length: Int = scan.nextInt()
    val array = ArrayList<Int>()
    print("Enter $length elements of the array : ")
    for (i in 0 until length) {
        array.add(scan.nextInt())
    }
    deleteRepeatingElements(array)
    print("Your array after transformation is : ")
    array.forEach { print("$it ") }
}