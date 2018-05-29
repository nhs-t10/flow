package org.firstinspires.ftc.teamcode.old.nodes

import org.firstinspires.ftc.teamcode.old.Dispatcher
import org.firstinspires.ftc.teamcode.old.Node
import org.firstinspires.ftc.teamcode.old.Nodeable
import org.firstinspires.ftc.teamcode.old.messages.Message

abstract class HeartbeatNode(override val nodeName : String, val synchronous : Boolean = true, val heartbeatInterval : Long = 10) : Thread(), Nodeable {
    var heartbeatActive = false

    override fun subscribe(channel: String, callback: (Message) -> Unit) : String {
        return Dispatcher.subscribe(channel, nodeName, callback)
    }
    override fun publish(channel: String, message: Message) {
        Dispatcher.publish(channel, message)
    }

    override fun run() {
        // if not thread
        if (synchronous) {
            subscribe("/heartbeat_unthreaded", {
                onHeartbeat()
            })
        }
        else {
            heartbeatActive = true
            var initialTime = System.currentTimeMillis()
            while (heartbeatActive && System.currentTimeMillis() - initialTime >= heartbeatInterval) {
                initialTime = System.currentTimeMillis()
                onHeartbeat()
            }
        }
    }

    final override fun endNode() {
        heartbeatActive = false
        heartbeatEnd()
    }

    // override this if stuff is still alive after opmode stop
    open fun heartbeatEnd() {}

    abstract fun onHeartbeat()
}