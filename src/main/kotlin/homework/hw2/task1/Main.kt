package homework.hw2.task1

import java.lang.Integer.min
import java.util.Scanner

fun findMinimal(first: Int, second: Int, third: Int): Int {
    return min(min(first, second), third)
}

fun distance(first: String, second: String): Int {
    var first = first
    var second = second
    var n = first.length
    var m = second.length
    if (n > m) {
        val temporary = first
        first = second
        second = temporary
        val temporaryLength = n
        n = m
        m = temporaryLength
    }
    var currentRow = mutableListOf<Int>()
    for (i in 0 until n + 1) {
        currentRow.add(i)
    }
    var previousRow = mutableListOf<Int>()
    previousRow.addAll(currentRow)
    var add: Int
    var delete: Int
    var change: Int
    for (i in 1 until m + 1) {
        previousRow = currentRow
        val temporaryList = mutableListOf<Int>()
        temporaryList.add(i)
        for (k in 0 until n) {
            temporaryList.add(0)
        }
        currentRow = temporaryList
        for (j in 1 until n + 1) {
            add = previousRow[j] + 1
            delete = currentRow[j - 1] + 1
            change = previousRow[j - 1]
            if (first[j - 1] != second[i - 1]) {
                change += 1
            }
            currentRow[j] = findMinimal(add, delete, change)
        }
    }
    return currentRow[n]
}

fun main() {
//    val scan = Scanner(System.`in`)
//    print("Enter the forbidden line : ")
//    val forbiddenLine: String = scan.next()
//    print("Enter the hole line : ")
//    val holeLine: String = scan.next()
//    println("You have to delete ${cutOutLine(holeLine, forbiddenLine)} to get rid of the forbidden line")
    for (i in 0 until 3 + 1) {
        print(i)
    }
    println("\n")
    println("The distance is ${distance("aaa", "a")}")
}

fun cutOutLine(holeLine: String, forbiddenLine: String): Int {
    var answer = 0
    var currentString = holeLine
    while (currentString.contains(forbiddenLine)) {
        val index = currentString.indexOf(forbiddenLine)
        for (i in 0..forbiddenLine.length) {
            val tryString = currentString.removeRange(index + i, index + 1 + i)
            if (!tryString.contains(forbiddenLine)) {
                return answer + 1
            }
        }
        currentString = currentString.removeRange(index, index + 1)
        answer++
    }
    return answer
}
