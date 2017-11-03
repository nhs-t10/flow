package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg

class HeartbeatNode : Node {
    constructor()
    fun beat(time : Long){
        val pub = Dispatcher.publish("/heartbeat", HeartBeatMsg(time=time,priority=1))
    }
}