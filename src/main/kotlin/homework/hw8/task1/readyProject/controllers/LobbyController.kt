package homework.hw8.task1.readyProject.controllers

import homework.hw8.task1.readyProject.models.RemoteGameModel
import io.ktor.util.KtorExperimentalAPI
import tornadofx.Controller
import tornadofx.stringBinding
import tornadofx.getValue

class LobbyController : Controller() {
    @KtorExperimentalAPI
    val model = RemoteGameModel()

    @KtorExperimentalAPI
    val canJoinGameProperty = model.gameStartedProperty
    @KtorExperimentalAPI
    val canJoinGame by canJoinGameProperty

    @KtorExperimentalAPI
    val statusText = stringBinding(model.connectedProperty, model.gameStartedProperty, model.errorMessageProperty) {
        when {
            model.errorMessage != null -> model.errorMessage
            model.gameStarted -> "Game Started"
            model.connected -> "Waiting for other players"
            else -> "Connecting"
        }
    }

    @KtorExperimentalAPI
    fun connect() {
        model.connect()
    }

    @KtorExperimentalAPI
    fun disconnect() {
        model.disconnect()
    }
}
