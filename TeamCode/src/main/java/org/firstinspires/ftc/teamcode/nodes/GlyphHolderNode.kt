package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by shaash on 10/26/17.
 */

class GlyphHolderNode : Node("Glyph Holder") {
    // Bottom Holder Servo Position Constants
    val bottomClosedPosition = 1.0
    val bottomOpenPosition = 0.65
    val bottomDropPosition = 0.8
    // Top Holder Servo Position Constants
    val topClosedPosition = 0.7
    val topOpenPosition = 0.05
    val topDropPosition = 0.5
    // Holder States
    var bottomIsOpen = true
    var topIsOpen = true

    override fun subscriptions() {
        this.subscribe("/glyph/upper", {upper(it)})
        this.subscribe("/glyph/lower", {lower(it)})
        this.subscribe("/gamepad1/x", {dropPosition(it)}) // Opens holders slightly so the glyphs can drop.
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
    fun dropPosition(m : Message) {
        bottomIsOpen = false
        topIsOpen = false
        this.publish("/servos/bottomServo", ServoMsg(position = bottomDropPosition, priority = 1))
        this.publish("/servos/topServo", ServoMsg(position = topDropPosition, priority = 1))
    }
}