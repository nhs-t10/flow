package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Channels
import org.firstinspires.ftc.teamcode.Node

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