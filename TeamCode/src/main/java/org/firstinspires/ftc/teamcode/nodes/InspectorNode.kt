package org.firstinspires.ftc.teamcode.nodes

import kotlinx.coroutines.experimental.channels.BroadcastChannel
import org.firstinspires.ftc.teamcode.Channels
import org.firstinspires.ftc.teamcode.Message
import org.firstinspires.ftc.teamcode.Node
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf

class InspectorNode(val channels: Channels) : Node() {
    override fun subscriptions() {
        // subscribe to Open Inspector Hook

        // very hacky iterator
        for (channel in Channels::class.declaredMemberProperties) {
            if (channel.returnType.isSubtypeOf(
                    List::class.createType(
                        listOf(KTypeProjection.covariant(Message::class.createType()))
                    )
                )) {

                @Suppress("UNCHECKED_CAST")
                (channel.get(channels)!! as BroadcastChannel<Message>).openSubscription()
            }
        }
    }

    fun showChannels() {
        // iterate through channels
        for ((key, channel) in channels.allChannels.entries) {
        }

    }
}