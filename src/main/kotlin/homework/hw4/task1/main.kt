package homework.hw4.task1

import homework.hw4.task1.hashFunction.IntCharValuesSumHashFunctionForString
import homework.hw4.task1.hashFunction.PolynomialHashFunctionForString
import java.io.File
import java.lang.IllegalArgumentException

fun putCommand(hashtable: Hashtable<String, String>) {
    print("Enter the key of the element: ")
    val key: String? = readLine()
    if (key == null || key == "") {
        print("Empty key is forbidden")
        return
    }
    print("Enter the value of the element: ")
    val value: String? = readLine()
    if (value == null || value == "") {
        println("Empty value is forbidden")
        return
    }
    hashtable.put(key, value)
}

fun removeCommand(hashtable: Hashtable<String, String>) {
    print("Enter the key you want to remove: ")
    val key: String? = readLine()
    if (key == null || key == "") {
        println("Empty key is forbidden")
        return
    }
    hashtable.remove(key)
}

fun findCommand(hashtable: Hashtable<String, String>) {
    print("Enter the key you want to find: ")
    val key: String? = readLine()
    if (key == null || key == "") {
        println("Empty key is forbidden")
        return
    }
    println("Value is ${hashtable.get(key)}")
}

fun changeCommand(hashtable: Hashtable<String, String>) {
    print("Enter the number of function you want to change to: " +
            "\n 1 -> hash function that will sum chars int value" +
            "\n 2 -> polynomial hash function\n")
    val number: Int? = readLine()?.toInt()
    if (number == null || number < 0) {
        println("Wrong number input")
        return
    }
    when (number) {
        1 -> hashtable.changeHashFunction(IntCharValuesSumHashFunctionForString())
        2 -> hashtable.changeHashFunction(PolynomialHashFunctionForString())
        else -> return
    }
}

fun putFile(hashtable: Hashtable<String, String>, file: File) {
    val bufferedReader = file.bufferedReader()
    bufferedReader.useLines { lines ->
        lines.forEach { line ->
            val currentLineElements = line.split(" ")
            currentLineElements.forEach { pair ->
                val regex = Regex("\\[(.+),(.+)]")
                if (!regex.containsMatchIn(pair)) {
                    throw IllegalArgumentException("Wrong data file")
                }
                val key = regex.find(pair)?.groupValues?.get(1)
                val value = regex.find(pair)?.groupValues?.get(2)
                hashtable.put(key, value)
            }
        }
    }
}

fun fileCommand(hashtable: Hashtable<String, String>) {
    val resourcesPath = "./src/main/resources/homework/hw4/task1/"
    print("Enter your file name: ")
    val filePath: String? = readLine()
    if (filePath == null) {
        println("Empty file name is forbidden")
        return
    }
    val file = File(resourcesPath + filePath)
    if (!file.exists()) {
        println("File not found\n")
        return
    }
    putFile(hashtable, file)
}

fun runCommand(hashtable: Hashtable<String, String>, command: String) {
    when (command) {
        "put" -> putCommand(hashtable)
        "remove" -> removeCommand(hashtable)
        "find" -> findCommand(hashtable)
        "changeFunction" -> changeCommand(hashtable)
        "printStatistics" -> hashtable.printStatistics()
        "putFile" -> fileCommand(hashtable)
        else -> println("Wrong command")
    }
}

fun main() {
    val hashtable = Hashtable<String, String>(IntCharValuesSumHashFunctionForString())
    var command: String
    val entranceLine = "Hello! You have these commands to use:" +
            "\n put -> to put element in the table" +
            "\n remove -> to remove key from the table" +
            "\n find -> to find value in hashtable" +
            "\n changeFunction -> to change hash function used by hashtable" +
            "\n printStatistics -> to see information about hashtable" +
            "\n putFile -> to put file data into the table"
    println(entranceLine)
    while (true) {
        print("Enter the command: ")
        command = readLine() ?: ""
        when (command) {
            "exit" -> return
            else -> runCommand(hashtable, command)
        }
    }
}
