package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Channels
import org.firstinspires.ftc.teamcode.HeartbeatMsg
import org.firstinspires.ftc.teamcode.Message
import org.firstinspires.ftc.teamcode.Node

class HeartbeatNode(val channels: Channels) : Node() {
    override fun subscriptions() {
        // none
    }

    suspend fun sendBeat() {
        channels.heartbeatChannel.send(HeartbeatMsg)
    }
}