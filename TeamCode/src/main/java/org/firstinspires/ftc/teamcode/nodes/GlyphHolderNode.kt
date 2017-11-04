package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.ServoMsg
import org.firstinspires.ftc.teamcode.messages.gamepadButtonMsg

/**
 * Created by shaash on 10/26/17.
 */

class GlyphHolderNode : Node {
    var position = .2

    constructor() {
        Dispatcher.subscribe("/gamepad1/right_bumper", { open(it) })
        Dispatcher.subscribe("/gamepad1/left_bumper", { close(it) })
    }

    fun open(state : Message) {
        val (value) = state as gamepadButtonMsg
        if(value){
            position = 0.2
            Dispatcher.publish("/servos/s0", ServoMsg(position, priority = 1))
        }
    }
    fun close(state : Message) {
        val (value) = state as gamepadButtonMsg
        if(value){
            position = 0.0
            Dispatcher.publish("/servos/s0", ServoMsg(position, priority = 1))
        }
    }
}