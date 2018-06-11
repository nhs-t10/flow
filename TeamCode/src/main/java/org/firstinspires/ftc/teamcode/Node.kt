package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch

abstract class Node {

    abstract fun subscriptions()

    // Spawns an actor for each subscription
    fun subscribe(channel: BroadcastChannel<Message>, callback: suspend (Message) -> Unit) = actor<Message> {
        // We aren't using the "mailbox" channel provided in the actor's context.
        // This provides the iterable from another channel.
        val subscription = channel.openSubscription()
        for (msg in subscription) {
            // Right now a new coroutine is spawned for each callback invocation...
            // ...so the channel isn't blocked by a single callback
            launch {
                callback.invoke(msg)
            }
        }
    }
}

