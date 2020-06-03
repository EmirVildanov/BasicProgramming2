package homework.hw4.task1

fun main() {
//    val regex = "\\[(.+), (.+)]".toRegex()
    val regex = Regex("\\[(.+), (.+)]")
    val resilt = regex.containsMatchIn("[12/./3, name]")
    print(resilt)
    val mathces = regex.findAll("[12/./3, name]").toList().map { it.value }
    val key = regex.find("123, name")?.groupValues?.get(0)
    val value1 = regex.find("[12/./3, name]")?.groupValues?.get(1)
    val value2 = regex.find("[123, name]")?.groupValues?.get(2)
    println(key)
    println(value1)
    println(value2)
    println(mathces)
    println(mathces.size)
}
