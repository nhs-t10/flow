package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.Nodeable
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.Message

abstract class HeartbeatNode(override val nodeName : String, val heartbeatInterval : Long = 50) : Thread(), Nodeable {
    var heartbeatActive = false

    override fun subscribe(channel: String, callback: (Message) -> Unit) {
        Dispatcher.subscribe(channel, callback)
    }
    override fun publish(channel: String, message: Message) {
        Dispatcher.publish(channel, message)
    }

    override fun run() {
        heartbeatActive = true
        var initialTime = System.nanoTime()
        while (System.nanoTime() - initialTime >= heartbeatInterval) {
            initialTime = System.nanoTime()
            onHeartbeat()
        }
    }

    final override fun endNode() {
        heartbeatActive = false
    }

    abstract fun onHeartbeat()
}