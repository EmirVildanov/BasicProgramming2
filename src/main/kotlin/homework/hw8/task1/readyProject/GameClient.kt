package homework.hw8.task1.readyProject

import homework.hw7.task1.messages.GameStartedMessage
import homework.hw7.task1.messages.GameEndedMessage
import homework.hw7.task1.messages.TextMessage
import homework.hw7.task1.messages.TurnServerMessage
import homework.hw7.task1.messages.TurnClientMessage
import homework.hw8.task1.readyProject.server.GameServer.Companion.HOST
import homework.hw8.task1.readyProject.server.GameServer.Companion.PORT
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tornadofx.runLater
import java.net.ConnectException

class GameClient {
    @KtorExperimentalAPI
    private val client: HttpClient = HttpClient {
        install(WebSockets)
    }

    private var connected = false

    private var session: WebSocketSession? = null
    var playerId: Int? = null

    var onConnect: (() -> Unit)? = null
    var onConnectionError: ((ConnectException) -> Unit)? = null
    var onDisconnect: (() -> Unit)? = null
    var onGameStart: ((Int) -> Unit)? = null
    var onTurnMade: ((Int, Int) -> Unit)? = null
    var onTie: (() -> Unit)? = null
    var onVictory: ((Int) -> Unit)? = null

    fun start() {
        GlobalScope.launch {
            try {
                connect()
            } catch (e: ConnectException) {
                runLater {
                    onConnectionError?.let { it(e) }
                }
            }
        }
    }

    @KtorExperimentalAPI
    private suspend fun connect() {
        client.ws(
            method = HttpMethod.Get,
            host = HOST,
            port = PORT,
            path = "/"
        ) {
            session = this
            runLater {
                connected = true
                onConnect?.let { it() }
            }
            try {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        handleMessage(frame.readText())
                    }
                }
            } finally {
                runLater {
                    connected = false
                    onDisconnect?.let { it() }
                }
            }
        }
    }

    fun close() {
        runBlocking {
            session?.close()
        }
    }

    private fun handleMessage(message: String) {
        try {
            when {
                message.startsWith(GameStartedMessage.name) -> handleGameStart(message)
                message.startsWith(TurnServerMessage.name) -> handleTurn(message)
                message.startsWith(GameEndedMessage.name) -> handleGameEnd(message)
            }
        } catch (e: TextMessage.IllegalMessageTypeException) {
            println("Received an unsupported message")
        } catch (e: TextMessage.IllegalNumberOfMessageArgumentsException) {
            println("Received message with an illegal number of arguments")
        } catch (e: TextMessage.IllegalMessageArgumentSyntax) {
            println("Received message with illegal argument syntax: ${e.message}")
        }
    }

    private fun handleGameStart(message: String) {
        val gameStartedMessage = GameStartedMessage(message)
        runLater {
            onGameStart?.let { it(gameStartedMessage.playerId) }
        }
    }

    private fun handleGameEnd(message: String) {
        val gameEndedMessage = GameEndedMessage(message)
        runLater {
            if (gameEndedMessage.winner == -1) {
                onTie?.let { it() }
            } else {
                onVictory?.let { it(gameEndedMessage.winner) }
            }
        }
    }

    private fun handleTurn(message: String) {
        val turnMessage = TurnServerMessage(message)

        runLater {
            onTurnMade?.let { it(turnMessage.playerId, turnMessage.position) }
        }
    }

    fun makeTurn(position: Int) = GlobalScope.launch {
        assert(session != null)
        session?.send(Frame.Text(TurnClientMessage.compose(position)))
    }
}
