package homework.hw1.task1


fun rearrangeArrayParts(array: IntArray, firstBoard: Int): IntArray {
    try {
        array.toMutableList().subList(0, firstBoard - 1).reverse()
        array.toMutableList().subList(firstBoard, array.size - 1).reverse()
        array.toMutableList().subList(0, array.size - 1).reverse()
    } catch (exception: ArrayIndexOutOfBoundsException) {
        println("FirstBoard index is out of array bounds")
        throw exception
    }
    return array
}

fun main() {
    try {
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
    } catch (exception: NumberFormatException) {
        print("Wrong input data format")
    }
}
