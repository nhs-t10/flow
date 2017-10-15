package org.firstinspires.ftc.teamcode.nodes
import android.text.AutoText
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeat
import org.firstinspires.ftc.teamcode.messages.Message
/**
 * Created by davis on 10/10/17.
 */

class Heart : Node{
    constructor()
    fun beat(time : Long){
        val pub = Dispatcher.publish("/heartbeat", HeartBeat(time=time,priority=1))
    }
}
