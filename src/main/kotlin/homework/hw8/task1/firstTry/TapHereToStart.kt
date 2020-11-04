//package homework.hw8.task1.firstTry
//
//import io.ktor.application.call
//import io.ktor.application.install
//import io.ktor.http.ContentType
//import io.ktor.http.cio.websocket.*
//import io.ktor.response.respondText
//import io.ktor.routing.get
//import io.ktor.routing.routing
//import io.ktor.server.engine.embeddedServer
//import io.ktor.server.netty.Netty
//import io.ktor.websocket.WebSockets
//import io.ktor.websocket.webSocket
//
//var gameIsOver = false
//
//const val FIELD_SIDE = 3
//
//enum class InsideShape(val shape: Int) {
//    CROSS(1),
//    CIRCLE(2),
//    NOTHING(0)
//}
//
//val fieldsShapesArray = Array(FIELD_SIDE * FIELD_SIDE) { InsideShape.NOTHING }
//val users = mutableListOf<String>()
//var firstUserTurn = true
//var secondUserReady = false
//var firstUserCrossSide = true
//
//enum class GameOverCheck {
//    CROSS_WON,
//    CIRCLE_WON,
//    DRAW,
//    NOTHING
//}
//
//fun main() {
//    val server = embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
//        install(WebSockets)
//        routing {
//            webSocket("/ws") {
//                for (frame in incoming) {
//                    when (frame) {
//                        is Frame.Text -> {
//                            val text = frame.readText()
//                            println(text)
//                            if (text.equals("ba", ignoreCase = true)) {
//                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said Ba"))
//                            } else if (text.startsWith(("User"))) {
//                                outgoing.send(Frame.Text(handleUser(text)))
//                            } else if (text.startsWith("GameOver")) {
//                                outgoing.send(Frame.Text(handleGameOver()))
//                            } else if (text.startsWith("Wait")) {
//                                outgoing.send(Frame.Text(handleWait()))
//                            } else if (text.startsWith("Side")) {
//                                outgoing.send(Frame.Text(handleSide()))
//                            } else if (text.startsWith("Start")) {
//                                handleStart(text)
//                            } else if (text.startsWith("Info")) {
//                                outgoing.send(Frame.Text(handleInfo(text)))
//                            } else if (text == "Close") {
//                                handleClose()
//                            } else if (text.startsWith("Figure")) {
//                                handleFigure(text.substringAfter(' '))
//                                outgoing.send(Frame.Text(text))
//                            } else {
//                                println("UNKNOWN")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//    server.start(wait = true)
//}
//
//fun handleUser(text: String): String {
//    var answer = ""
//    if (users.size == 0) {
//        answer = "YES"
//        users.add(text.substringAfter(' '))
//    } else if (text.substringAfter(' ') != users[0]) {
//        if (users.size == 1) {
//            users.add(text.substringAfter(' '))
//        } else {
//            users[1] = text.substringAfter(' ')
//        }
//        answer = "YES"
//    } else {
//        answer = "NO"
//    }
//    return answer
//}
//
//fun handleWait(): String {
//    var answer = ""
//    answer = if (secondUserReady) {
//        "YES"
//    } else {
//        "NO"
//    }
//    return answer
//}
//
//fun handleSide(): String {
//    var answer = ""
//    if (users.size == 2) {
//        if (firstUserCrossSide) {
//            answer = "CIRCLE"
//        } else {
//            answer = "CROSS"
//        }
//    } else {
//        answer = "CHOICE"
//    }
//    return answer
//}
//
//fun handleStart(text: String) {
//    if (users.size == 1) {
//        firstUserCrossSide = text.substringAfter(' ') == "CROSS"
//        firstUserTurn = firstUserCrossSide
//    } else {
//        secondUserReady = true
//    }
//}
//
//fun handleInfo(text: String): String {
//    var answer = ""
//    fieldsShapesArray.forEach {
//        answer += when (it) {
//            InsideShape.CROSS -> "1 "
//            InsideShape.CIRCLE -> "2 "
//            else -> "0 "
//        }
//    }
//    if (text.substringAfter(' ') == users[0]) {
//        if (firstUserTurn) {
//            answer += "true"
//        } else {
//            answer += "false"
//        }
//    } else {
//        if (!firstUserTurn) {
//            answer += "true"
//        } else {
//            answer += "false"
//        }
//    }
//    println(answer)
//    return answer
//}
//
//fun handleClose() {
//    users.clear()
//    firstUserTurn = true
//    secondUserReady =false
//    firstUserCrossSide = true
//    for (i in fieldsShapesArray.indices) {
//        fieldsShapesArray[i] = InsideShape.NOTHING
//    }
//}
//
//fun handleGameOver(): String {
//    var answer = ""
//    answer = when (checkGameOver()) {
//        GameOverCheck.CROSS_WON -> "CROSS"
//        GameOverCheck.CIRCLE_WON -> "CIRCLE"
//        GameOverCheck.DRAW -> "DRAW"
//        else -> ""
//    }
//    return answer
//}
//
//fun handleFigure(text: String) {
//    fieldsShapesArray[text[0].toString().toInt()] = when (text[1]) {
//        '1' -> InsideShape.CROSS
//        '2' -> InsideShape.CIRCLE
//        else -> InsideShape.NOTHING
//    }
//    firstUserTurn = !firstUserTurn
//}
