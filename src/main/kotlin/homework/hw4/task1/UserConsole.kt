package homework.hw4.task1

import java.io.File
import java.lang.IllegalArgumentException

fun putCommand(hashtable: Hashtable<String, String>) {
    var key: String? = ""
    var value: String? = ""
    print("Enter the key of the element: ")
    key = readLine()
    if (key == null || key == "") {
        print("Empty key is forbidden")
        return
    }
    print("Enter the value of the element: ")
    value = readLine()
    if (value == null || value == "") {
        println("Empty value is forbidden")
        return
    }
    hashtable.put(key, value)
}

fun removeCommand(hashtable: Hashtable<String, String>) {
    var key: String? = ""
    print("Enter the key you want to remove: ")
    key = readLine()
    if (key == null || key == "") {
        println("Empty key is forbidden")
        return
    }
    hashtable.remove(key)
}

fun findCommand(hashtable: Hashtable<String, String>) {
    var key: String? = ""
    print("Enter the key you want to find: ")
    key = readLine()
    if (key == null || key == "") {
        println("Empty key is forbidden")
        return
    }
    println("Value is ${hashtable.get(key)}")
}

fun changeCommand(hashtable: Hashtable<String, String>) {
    var number: Int? = -1
    print("Enter the number of function you want to change to: ")
    number = readLine()?.toInt()
    if (number == null || number < 0) {
        println("Wrong number input")
        return
    }
    when (number) {
        1 -> hashtable.changeHashFunction(1)
        2 -> hashtable.changeHashFunction(2)
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
    var filePath: String? = ""
    print("Enter your file path: ")
    filePath = readLine()
    if (filePath == null) {
        println("Empty file path is forbidden")
        return
    }
    val file = File(filePath)
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
    val hashtable = Hashtable<String, String>()
    var command = ""
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
