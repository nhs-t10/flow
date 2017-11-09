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
    }
    override fun init() {

        this.subscribe("/gamepad1/a"){ lower(it) }
        this.subscribe("/gamepad1/b"){ upper(it) }
    }

    fun lower(state : Message) {
        val (value) = state as gamepadButtonMsg
        if(value){
            if(bottomIsOpen){
                this.publish("/servos/bottomServo", ServoMsg(bottomClosedPosition, priority = 1))
                bottomIsOpen = false
            } else {
                this.publish("/servos/bottomServo", ServoMsg(bottomOpenPosition, priority = 1))
                bottomIsOpen = true
            }
        }
    }
    fun upper(state : Message){
        val (value) = state as gamepadButtonMsg
        if(value){
            if(topIsOpen){
                this.publish("/servos/topServo", ServoMsg(topClosedPosition, priority = 1))
                topIsOpen = false
            } else {
                this.publish("/servos/topServo", ServoMsg(topOpenPosition, priority = 1))
                topIsOpen = true
            }
        }
    }
}