package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withTimeout

abstract class Node {

    private var fallbackChannel : Chan? = null

    fun doSubscriptions(fallback: Chan) {
        fallbackChannel = fallback
    }
    protected abstract fun subscriptions()

    private val timeout : Long = 5000L

    // Spawns an actor for each subscription
    fun subscribe(channel: Chan, callback: suspend (Message) -> Unit) = actor<Message> {
        // We aren't using the "mailbox" channel provided in the actor's context.
        // This provides the iterable from another channel.
        val subscription = channel.openSubscription()

        for (msg in subscription) {
            try {
                withTimeout(timeout) {
                    if (channel.blocking) {
                        callback.invoke(msg)
                    } else {
                        launch {
                            callback.invoke(msg)
                        }
                    }
                }
            } catch (e: Exception) {
                fallbackChannel?.send(ErrorMsg("Caught exception, might have timed out: $e"))
            }
        }
        callback.invoke(CloseMsg)
    }
    suspend fun send(channel: Chan, msg: Message) {
        val success = channel.send(msg)
        if (!success && channel.getName() != fallbackChannel?.getName()) {
           fallbackChannel?.send(ErrorMsg("Queue is full for ${channel.getName()}! Change the capacity or make it non-blocking, it's currently at ${channel.capacity}."))
        }
    }
}

