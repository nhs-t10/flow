package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch

abstract class Node {

    abstract fun subscriptions()

//    fun subscribe(channel: BroadcastChannel<Message>, callback: suspend (Message) -> Unit) = launch {
//        subscribeInternal(channel, callback)
//    }
    fun subscribe(channel: BroadcastChannel<Message>, callback: suspend (Message) -> Unit) = actor<Message> {
        val subscription = channel.openSubscription()
        for (msg in subscription) {
            launch {
                callback.invoke(msg)
            }
        }
    }
}

