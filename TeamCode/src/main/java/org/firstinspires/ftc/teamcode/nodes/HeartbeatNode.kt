package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg

abstract class HeartbeatNode(val heartbeatNodeName : String, val heartbeatInterval : Long = 50) : Node("[Heartbeat] $heartbeatNodeName") {
    var heartbeatActive = false

    override fun run() {
        heartbeatActive = true
        var initialTime = System.nanoTime()
        while (System.nanoTime() - initialTime >= heartbeatInterval) {
            initialTime = System.nanoTime()
            onHeartbeat()
        }
    }

    override fun end() {
        heartbeatActive = false
    }

    abstract fun onHeartbeat()
}