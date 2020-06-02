package homework.hw4.task1

fun main() {
    val regex = "[\\[]\\s+ \\s+[\\]]]".toRegex()
    val match = regex.findAll("[test file]")
//        ?.groupValues?.get(1)
//    val secondPart = treePattern.find(treeString)?.groupValues?.get(2)
    println(match.elementAt(0).value)
//    println(match.elementAt(2).value)
//    println(match.elementAt(3).value)
//    val test = "arrayOf(1, 2, 3)" as Array<Int>
//    println(test.joinToString(" "))
}
