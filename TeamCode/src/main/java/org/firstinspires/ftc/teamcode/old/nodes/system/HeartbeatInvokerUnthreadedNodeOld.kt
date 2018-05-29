package org.firstinspires.ftc.teamcode.old.nodes.system

import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg

/**
 * Created by max on 2/28/18.
 */
class HeartbeatInvokerUnthreadedNodeOld : NodeOld("Heartbeat Invoker") {
    override fun subscriptions() {

    }
    fun publishBeat() {
        publish("/heartbeat_unthreaded", UnitMsg())
    }
}