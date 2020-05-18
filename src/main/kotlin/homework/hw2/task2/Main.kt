import java.util.Scanner

fun deleteRepeatingElements(array: MutableList<Int>) {
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
    print("Enter $length elements of the array : ")
    val array = readLine()?.split(' ')?.map { it.toInt() }?.toMutableList()
    if (array != null) {
        deleteRepeatingElements(array)
        print("Your array after transformation is : ")
        array.forEach { print("$it ") }
    }
}
