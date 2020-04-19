package homework.hw1.task1

fun rearrangeArrayParts(array: IntArray, firstBoard: Int): IntArray {
    val listArray = array.toMutableList()
    listArray.subList(0, firstBoard).reverse()
    listArray.subList(firstBoard, array.size).reverse()
    listArray.subList(0, array.size).reverse()
    return listArray.toIntArray()
}

fun main() {
    print("Enter the first board : ")
    val firstBoard = readLine()?.toInt()
    print("Enter the second board : ")
    val secondBoard = readLine()?.toInt()
    val length = firstBoard!! + secondBoard!!
    val array = IntArray(length) { 0 }
    print("Enter $length elements of the list(every from the new line) : ")
    for (i in 0 until length) {
        array[i] = readLine()?.toInt()!!
    }
    rearrangeArrayParts(array, firstBoard)
    print("The answer is : ${array.joinToString(" ")}")
}
