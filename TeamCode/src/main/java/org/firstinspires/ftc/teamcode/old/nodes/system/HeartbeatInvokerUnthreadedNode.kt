package org.firstinspires.ftc.teamcode.old.nodes.system

import org.firstinspires.ftc.teamcode.old.Node
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg

/**
 * Created by max on 2/28/18.
 */
class HeartbeatInvokerUnthreadedNode : Node("Heartbeat Invoker") {
    override fun subscriptions() {

    }
    fun publishBeat() {
        publish("/heartbeat_unthreaded", UnitMsg())
    }
}