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


    }

    fun showChannels() {
        // iterate through channels
        for ((key, channel) in channels.allChannels.entries) {
        }

    }
}