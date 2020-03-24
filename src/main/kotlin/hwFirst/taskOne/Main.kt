package hwFirst.taskOne

fun reverse(array: IntArray, start: Int, end: Int) {
    val length: Int = (end - start + 1) / 2
    for (i in 0 until length) {
        val temporary: Int = array[start + i]
        array[start + i] = array[end - i]
        array[end - i] = temporary
    }
}

fun rearrangeArrayParts(array: IntArray, firstBoard: Int): IntArray {
    try {
        reverse(array, 0, firstBoard - 1)
        reverse(array, firstBoard, array.size - 1)
        reverse(array, 0, array.size - 1)
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
    } catch (exception: NumberFormatException){
        print("Wrong input data format")
    }
}