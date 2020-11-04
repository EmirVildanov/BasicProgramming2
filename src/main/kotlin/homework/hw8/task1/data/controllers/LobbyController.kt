package homework.hw8.task1.data.controllers

import homework.hw8.task1.data.models.RemoteGameModel
import tornadofx.Controller
import tornadofx.stringBinding
import tornadofx.getValue

class LobbyController : Controller() {
    val model = RemoteGameModel()

    val canJoinGameProperty = model.gameStartedProperty
    val canJoinGame by canJoinGameProperty

    val statusText = stringBinding(model.connectedProperty, model.gameStartedProperty, model.errorMessageProperty) {
        when {
            model.errorMessage != null -> model.errorMessage
            model.gameStarted -> "Game Started"
            model.connected -> "Waiting for other players"
            else -> "Connecting"
        }
    }

    fun connect() {
        model.connect()
    }

    fun disconnect() {
        model.disconnect()
    }
}
