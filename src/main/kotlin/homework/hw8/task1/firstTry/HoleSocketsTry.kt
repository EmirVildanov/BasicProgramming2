package homework.hw8.task1.firstTry

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ws
import io.ktor.features.HttpsRedirect
import io.ktor.features.origin
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.*
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.sslConnector
import io.ktor.server.netty.Netty
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlinx.coroutines.runBlocking
import tornadofx.*
import kotlin.time.Duration

var gameIsOver = false

const val FIELD_SIDE = 3

enum class InsideShape(val shape: Int) {
    CROSS(1),
    CIRCLE(2),
    NOTHING(0)
}

val fieldsShapesArray = Array(FIELD_SIDE * FIELD_SIDE) { InsideShape.NOTHING }
val users = mutableListOf<String>()
var firstUserTurn = true
var firstUserCrossSide = true

enum class GameOverCheck(val checkType: Int) {
    CROSS_WON(1),
    CIRCLE_WON(2),
    DRAW(3),
    NOTHING(4)
}

private fun checkDraw(): Boolean {
    fieldsShapesArray.forEach {
        if (it == InsideShape.NOTHING) {
            return false
        }
    }
    return true
}

fun checkGameOver(): GameOverCheck {
    if (
        fieldsShapesArray[0] == fieldsShapesArray[1] && fieldsShapesArray[1] == fieldsShapesArray[2] && fieldsShapesArray[2] == InsideShape.CROSS ||
        fieldsShapesArray[3] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[5] && fieldsShapesArray[5] == InsideShape.CROSS ||
        fieldsShapesArray[6] == fieldsShapesArray[7] && fieldsShapesArray[7] == fieldsShapesArray[8] && fieldsShapesArray[8] == InsideShape.CROSS ||
        fieldsShapesArray[0] == fieldsShapesArray[3] && fieldsShapesArray[3] == fieldsShapesArray[6] && fieldsShapesArray[6] == InsideShape.CROSS ||
        fieldsShapesArray[1] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[7] && fieldsShapesArray[7] == InsideShape.CROSS ||
        fieldsShapesArray[2] == fieldsShapesArray[5] && fieldsShapesArray[5] == fieldsShapesArray[8] && fieldsShapesArray[8] == InsideShape.CROSS ||
        fieldsShapesArray[0] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[8] && fieldsShapesArray[8] == InsideShape.CROSS ||
        fieldsShapesArray[2] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[6] && fieldsShapesArray[6] == InsideShape.CROSS
    ) {
        return GameOverCheck.CROSS_WON
    } else if (
        fieldsShapesArray[0] == fieldsShapesArray[1] && fieldsShapesArray[1] == fieldsShapesArray[2] && fieldsShapesArray[2] == InsideShape.CIRCLE ||
        fieldsShapesArray[3] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[5] && fieldsShapesArray[5] == InsideShape.CIRCLE ||
        fieldsShapesArray[6] == fieldsShapesArray[7] && fieldsShapesArray[7] == fieldsShapesArray[8] && fieldsShapesArray[8] == InsideShape.CIRCLE ||
        fieldsShapesArray[0] == fieldsShapesArray[3] && fieldsShapesArray[3] == fieldsShapesArray[6] && fieldsShapesArray[6] == InsideShape.CIRCLE ||
        fieldsShapesArray[1] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[7] && fieldsShapesArray[7] == InsideShape.CIRCLE ||
        fieldsShapesArray[2] == fieldsShapesArray[5] && fieldsShapesArray[5] == fieldsShapesArray[8] && fieldsShapesArray[8] == InsideShape.CIRCLE ||
        fieldsShapesArray[0] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[8] && fieldsShapesArray[8] == InsideShape.CIRCLE ||
        fieldsShapesArray[2] == fieldsShapesArray[4] && fieldsShapesArray[4] == fieldsShapesArray[6] && fieldsShapesArray[6] == InsideShape.CIRCLE
    ) {
        return GameOverCheck.CIRCLE_WON
    } else if (checkDraw()) {
        return GameOverCheck.DRAW
    }
    return GameOverCheck.NOTHING
}

fun tryToEndGame() {
    gameIsOver = true
    when (checkGameOver()) {
        GameOverCheck.CROSS_WON -> {
//            close()
//            GameOverView("Crosses won!").openWindow()
        }
        GameOverCheck.CIRCLE_WON -> {
//            close()
//            GameOverView("Circles won!").openWindow()
        }
        GameOverCheck.DRAW -> {
//            close()
//            GameOverView("Draw!").openWindow()
        }
        else -> {
            gameIsOver = false
        }
    }
}

fun main() {
    val server = embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(WebSockets) {
        }
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
            webSocket("/ws") { // websocketSession
                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            println("Getting request")
                            val text = frame.readText()
                            println(text)
                            if (text.equals("ba", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said Ba"))
                            } else if (text.startsWith(("User"))) {
                                println("USER")
                                if (users.size == 0) {
                                    outgoing.send(Frame.Text("YES"))
                                    users.add(text.substringAfter(' '))
                                } else if (users.size == 1 && text.substringAfter(' ') != users[0]) {
                                    users.add(text.substringAfter(' '))
                                    outgoing.send(Frame.Text("YES"))
                                } else {
                                    outgoing.send(Frame.Text("NO"))
                                }
                            } else if (text == ("Side")) {
                                if (users.size == 1) {
                                    if (firstUserCrossSide) {
                                        outgoing.send(Frame.Text("CIRCLE"))
                                    } else {
                                        outgoing.send(Frame.Text("CROSS"))
                                    }
                                } else {
                                    outgoing.send(Frame.Text("CHOICE"))
                                }
                            } else if (text.startsWith("Start")) {
                                println("START")
                                if (users.size == 1) {
                                    firstUserCrossSide = text.substringAfter(' ') == "1"
                                }
                            } else if (text.startsWith("Turn")) {
                                var answer = ""
                                answer = if (text.substringAfter(' ') == users[0]) {
                                    if (firstUserTurn) {
                                        "YES"
                                    } else {
                                        "NO"
                                    }
                                } else {
                                    if (!firstUserTurn) {
                                        "YES"
                                    } else {
                                        "NO"
                                    }
                                }
                                println(answer)
                                outgoing.send(Frame.Text(answer))
                            } else if (text.startsWith("Info")) {
                                var answer = ""
                                fieldsShapesArray.forEach {
                                    answer += when (it) {
                                        InsideShape.CROSS -> "1 "
                                        InsideShape.CIRCLE -> "2 "
                                        else -> "0 "
                                    }
                                }
                                outgoing.send(Frame.Text(answer))
                                println(answer)
                            } else if (text == "End") {
                                users.clear()
                                for (i in fieldsShapesArray.indices) {
                                    fieldsShapesArray[i] = InsideShape.NOTHING
                                }
                                for (i in fieldsShapesArray.indices) {
                                    when (fieldsShapesArray[i]) {
                                        InsideShape.CROSS -> print(1)
                                        InsideShape.CIRCLE -> print(2)
                                        else -> print(0)
                                    }
                                    print("\n")
                                }
                            } else if (text.startsWith("Figure")) {
                                fieldsShapesArray[text[0].toString().toInt()] = when (text[1]) {
                                    '1' -> InsideShape.CROSS
                                    '2' -> InsideShape.CIRCLE
                                    else -> InsideShape.NOTHING
                                }
                                firstUserTurn = !firstUserTurn
                                outgoing.send(Frame.Text(text))
                            } else {
                                println("UNKNOWN")
                            }
                        }
                    }
                }
            }
        }
    }
    server.start(wait = true)
}

//fun Application.mymodule() {
//
//    install(Authentication) {
//        basic(name = "myauth1") {
//            realm = "Ktor Server"
//            validate { credentials ->
//                if (credentials.name == credentials.password) {
//                    UserIdPrincipal(credentials.name)
//                } else {
//                    null
//                }
//            }
//        }
//    }
//
//
//    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//    }
//}

