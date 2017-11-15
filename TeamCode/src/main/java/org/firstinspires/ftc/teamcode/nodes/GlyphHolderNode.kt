package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.ServoMsg
import org.firstinspires.ftc.teamcode.messages.TextMsg
import org.firstinspires.ftc.teamcode.util.whenDown

/**
 * Created by shaash on 10/26/17.
 */

class GlyphHolderNode : Node() {
    val bottomClosedPosition = 0.5
    val bottomOpenPosition = 0.95

    val topClosedPosition = 0.05
    val topOpenPosition = 0.65

    val holderLOpenPosition = 1.0
    val holderLClosedPosition = 0.0
    val holderROpenPosition = 1.0
    val holderRClosedPosition = 0.0
    var bottomIsOpen = false
    var topIsOpen = false
    var holderIsOpen= true

    override fun subscriptions() {

        this.subscribe("/gamepad1/a", whenDown { lower() })
        this.subscribe("/gamepad1/b", whenDown { upper() })
        this.subscribe("/gamepad1/x", whenDown { holder() })
    }

    fun lower() {
        this.publish("/servos/bottomServo", ServoMsg(if(bottomIsOpen) bottomClosedPosition else bottomOpenPosition, priority = 1))
        bottomIsOpen = !bottomIsOpen
    }
    fun upper(){
        this.publish("/servos/topServo", ServoMsg(if(topIsOpen) topClosedPosition else topOpenPosition, priority = 1))
        topIsOpen = !topIsOpen
    }
    fun holder(){
        if(holderIsOpen){
            this.publish("/servos/holderServoL", ServoMsg(holderLClosedPosition, priority = 1))
            this.publish("/servos/holderServoR", ServoMsg(holderRClosedPosition, priority = 1))
            holderIsOpen = false
        } else {
            this.publish("/servos/holderServoL", ServoMsg(holderLOpenPosition, priority = 1))
            this.publish("/servos/holderServoR", ServoMsg(holderROpenPosition, priority = 1))
            holderIsOpen = true
        }
    }
}