package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg

class HeartbeatNode : Node {
    constructor()
    override fun subscriptions() {}
    fun beat(time : Long){
        this.publish("/heartbeat", HeartBeatMsg(time=time,priority=1))
    }
}