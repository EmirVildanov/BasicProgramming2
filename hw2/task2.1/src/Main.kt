//Определите минимальное количество символов,
// которые нужно удалить из строки так, чтобы она не содержала «xxx» в качестве подстроки.
// Выведите 0, если строка изначально не содержит запрещенной подстроки «xxx».
// Удалять символы можно в произвольных позициях (не обязательно подряд).

import java.util.*

fun main() {
    val scan = Scanner(System.`in`)
    print("Enter the forbidden line : ")
    val forbiddenLine: String = scan.next()
    print("Enter the hole line : ")
    val holeLine: String = scan.next()
    println("You have to delete ${cutOutLine(holeLine, forbiddenLine)} to get rid of the forbidden line")
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
