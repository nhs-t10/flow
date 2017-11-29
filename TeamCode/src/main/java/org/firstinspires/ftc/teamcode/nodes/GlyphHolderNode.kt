package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.whenDown

/**
 * Created by shaash on 10/26/17.
 */

class GlyphHolderNode : Node("Glyph Holder") {

    val bottomFullOpenPosition = 0.5
    val bottomClosedPosition = 1.0
    val bottomOpenPosition = 0.65

    val topClosedPosition = 0.7
    val topOpenPosition = 0.05

    val holderLOpenPosition = 1.0
    val holderLClosedPosition = 0.0
    val holderROpenPosition = 1.0
    val holderRClosedPosition = 0.0
    var bottomIsOpen = true
    var topIsOpen = true
    var holderIsOpen= true

    override fun subscriptions() {
        this.subscribe("/glyph/upper", {upper(it)})
        this.subscribe("/glyph/lower", {lower(it)})
        //this.subscribe("/gamepad1/x", whenDown { holder() })
    }

    fun findState (current : Boolean, state : GripperState) = when (state) {
        GripperState.TOGGLE -> !current
        GripperState.OPEN -> true
        GripperState.CLOSED -> false
    }

    fun lower(m : Message) {
        val (state) = m as GripperMsg
        bottomIsOpen = findState(bottomIsOpen, state)
        this.publish("/servos/bottomServo", ServoMsg(if(bottomIsOpen) bottomOpenPosition else bottomClosedPosition, priority = 1))
    }
    fun upper(m: Message){
        val (state) = m as GripperMsg
        topIsOpen = findState(topIsOpen, state)
        this.publish("/servos/topServo", ServoMsg(if (topIsOpen) topOpenPosition else topClosedPosition, priority = 1))
    }
    /*
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
    */
}