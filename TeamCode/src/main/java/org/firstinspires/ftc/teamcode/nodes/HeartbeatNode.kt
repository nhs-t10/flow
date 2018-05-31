package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Message
import org.firstinspires.ftc.teamcode.Node

class HeartbeatNode : Node() {
    override fun subscriptions() {
        // none
    }

    suspend fun sendBeat() {
        channels?.heartbeatChannel?.send(Message.Heartbeat)
    }
}