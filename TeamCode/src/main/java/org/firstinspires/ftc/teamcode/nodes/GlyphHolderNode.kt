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
    val bottomOpenPosition = 0.4
    val bottomClosedPosition = 0.0
    val topOpenPosition = 1.0
    val topClosedPosition = 0.0
    var bottomIsOpen = true
    var topIsOpen = true

    constructor() {
        Dispatcher.subscribe("/gamepad1/a", { lower(it) })
        Dispatcher.subscribe("/gamepad1/b", {upper(it)})
        Dispatcher.publish("/servos/bottomServo", ServoMsg(0.0, priority = 1))
        Dispatcher.publish("/servos/topServo", ServoMsg(0.0, priority = 1))
    }

    fun lower(state : Message) {
        val (value) = state as gamepadButtonMsg
        if(value){
            if(bottomIsOpen){
                Dispatcher.publish("/servos/bottomServo", ServoMsg(bottomClosedPosition, priority = 1))
                bottomIsOpen = false
            } else {
                Dispatcher.publish("/servos/bottomServo", ServoMsg(bottomOpenPosition, priority = 1))
                bottomIsOpen = true
            }
        }
    }
    fun upper(state : Message){
        val (value) = state as gamepadButtonMsg
        if(value){
            if(topIsOpen){
                Dispatcher.publish("/servos/topServo", ServoMsg(topClosedPosition, priority = 1))
                topIsOpen = false
            } else {
                Dispatcher.publish("/servos/topServo", ServoMsg(topOpenPosition, priority = 1))
                topIsOpen = true
            }
        }
    }
}