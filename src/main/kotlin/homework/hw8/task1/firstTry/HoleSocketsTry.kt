package homework.hw8.task1.firstTry

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ws
import io.ktor.features.HttpsRedirect
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
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlinx.coroutines.runBlocking
import tornadofx.*
import kotlin.time.Duration

var crossTurn = true
var playerTurn = true
var gameIsOver = false
var botDifficulty = 1

var firstPlayerTurnCheck = false
var firstPlayerTurnCellIndex = -1

var secondPlayerTurnCheck = false
var secondPlayerTurnCellIndex = -1

var lastPlayerTurn = -1

const val SIDE = 150.0
const val WORDS_FONT = 30.0
const val FIELD_SIDE = 3
const val BOT_DELAY_TIME = 0.5

/*
  000
  000
  000
 */
val fieldsShapesArray = Array(FIELD_SIDE * FIELD_SIDE) { "0" }

val difficultyToggleGroup = ToggleGroup()

fun main(args: Array<String>) {
//    val env = applicationEngineEnvironment {
//        module {
//            main()
//        }
//        // Private API
//        connector {
//            host = "127.0.0.1"
//            port = 9090
//        }
//        // Public API
//        connector {
//            host = "0.0.0.0"
//            port = 8080
//        }
//    }
    val server = embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
//        install(HttpsRedirect)
        install(WebSockets) {
//            masking = true
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
                            val text = frame.readText()
//                            outgoing.send(Frame.Text("YOU SAID: $text"))
//                            var sendingString = ""
//                            fieldsShapesArray.forEach { sendingString += "$it " }
//                            outgoing.send(Frame.Text(sendingString))
                            if (text.equals("bye", ignoreCase = true)) {
//                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                            }
                            else if (text.equals("ba", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said Ba"))
                            } else {
                                fieldsShapesArray[text[0].toInt()] = text[1].toString()
                                outgoing.send(Frame.Text(text))
                            }
                        }
                    }
                }
            }
        }
    }
    server.start(wait = true)
}
