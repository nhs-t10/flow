package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.messages.ServoMsg

/**
 * Created by shaash on 10/26/17.
 */

class GlyphHolderNode : Node("Glyph Holder") {
    // Top Holder Servo Position Constants

    fun getBottomPosition (state : GripperState) = when (state) {
        GripperState.OPEN -> 0.64
        GripperState.CLOSED -> 1.0
        GripperState.MIDDLE -> 0.8
    }
    fun getTopPosition (state : GripperState) = when (state) {
        GripperState.OPEN -> 0.3
        GripperState.CLOSED -> 0.7
        GripperState.MIDDLE -> 0.5
    }

    val right_outward = 0.1
    val right_inward = 0.875

    val left_inward = 0.125
    val left_outward = 1.0



    // Holder States
    override fun subscriptions() {
        this.subscribe("/glyph/upper", {upper(it)})
        this.subscribe("/glyph/lower", {lower(it)})
        this.subscribe("/start", {start()})
        subscribe("/hugger", {updateHugger(it)})
    }

    fun start() {
        this.publish("/servos/bottomServo", ServoMsg(getBottomPosition(GripperState.OPEN), 1))
        this.publish("/servos/topServo", ServoMsg(getTopPosition(GripperState.OPEN), 1))
    }

    fun lower(m : Message) {
        val (state) = m as GripperMsg
        this.publish("/servos/bottomServo", ServoMsg(getBottomPosition(state), priority = 1))
    }
    fun upper(m: Message){
        val (state) = m as GripperMsg
        this.publish("/servos/topServo", ServoMsg(getTopPosition(state), priority = 1))
    }

    fun updateHugger(m : Message){
        val (closeIt, onClosed) = m as HuggerMsg
        if (!closeIt) { // NEED TO OPEN IT DOOD
            this.publish("/servos/hugger_l", ServoMsg(left_outward, 2))
            this.publish("/servos/hugger_r", ServoMsg(right_outward, 2))
        }
        else { // NEED TO CLOSE IT DOOD
            this.publish("/servos/hugger_l", ServoMsg(left_inward, 2))
            this.publish("/servos/hugger_r", ServoMsg(right_inward, 2))
        }
    }
}