package com.jvg.gpsapp.ui.messages

import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Messages {
    val messages: Flow<Message>

    suspend fun sendMessage(message: Int, description: String? = null)
}

class DefaultMessages : Messages {
    private val _messages: Channel<Message> = Channel<Message>()
    override val messages: Flow<Message> = _messages.receiveAsFlow()

    override suspend fun sendMessage(message: Int, description: String?) {
        _messages.send(Message(message, description))
    }
}

data class Message(
    @StringRes
    val message: Int,
    val description: String? = null
)
