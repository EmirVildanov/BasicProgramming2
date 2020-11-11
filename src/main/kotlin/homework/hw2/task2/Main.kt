
fun <T> deleteRepeatingElements(list: List<T>): List<T> {
    return list.reversed().distinct().reversed()
}

fun main() {
    print("Enter elements of the array : ")
    val list = readLine()?.split(' ')?.map { it.toInt() }
    if (list != null) {
        print("Your array after transformation is : ")
        println(deleteRepeatingElements(list).joinToString(" "))
    } else {
        println("Input array is null")
    }
}
