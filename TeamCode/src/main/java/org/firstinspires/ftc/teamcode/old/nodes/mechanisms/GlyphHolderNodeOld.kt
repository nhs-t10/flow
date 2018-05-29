package org.firstinspires.ftc.teamcode.old.nodes.mechanisms

import org.firstinspires.ftc.teamcode.old.Dispatcher
import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.*
import org.firstinspires.ftc.teamcode.old.messages.ServoMsg

/**
 * Created by shaash on 10/26/17.
 */

class GlyphHolderNodeOld : NodeOld("Glyph Holder") {
    // Top Holder Servo Position Constants

    fun getBottomPosition (state : GripperState) = when (state) {
        GripperState.OPEN -> 0.0
        GripperState.CLOSED -> 0.6
        GripperState.MIDDLE -> 0.3
    }
    fun getTopPosition (state : GripperState) = when (state) {
        GripperState.OPEN -> 0.0
        GripperState.CLOSED -> 0.65
        GripperState.MIDDLE -> 0.5
    }

    // Holder States
    override fun subscriptions() {
        this.subscribe("/glyph/upper", {upper(it)})
        this.subscribe("/glyph/lower", {lower(it)})
        this.subscribe("/start", {onStart()})
    }

    fun onStart() {
        this.publish("/servos/bottomServo", ServoMsg(getBottomPosition(GripperState.OPEN), 1))
        this.publish("/servos/topServo", ServoMsg(getTopPosition(GripperState.CLOSED), 1))
    }

    fun lower(m : Message) {
        val (state) = m as GripperMsg
        this.publish("/servos/bottomServo", ServoMsg(getBottomPosition(state), priority = 1))
    }
    fun upper(m: Message){
        val (state) = m as GripperMsg
        this.publish("/servos/topServo", ServoMsg(getTopPosition(state), priority = 1))
    }
}