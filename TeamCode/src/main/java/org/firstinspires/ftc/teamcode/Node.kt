package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.actor

abstract class Node {
    val channels: Channels? = null

    abstract fun subscriptions()

    fun subscribe(channel: BroadcastChannel<Message>, callback: suspend (Message) -> Unit) = actor<Message> {
        val subscription = channel.openSubscription()
        for (msg in subscription) {
            callback.invoke(msg)
        }
    }
}

