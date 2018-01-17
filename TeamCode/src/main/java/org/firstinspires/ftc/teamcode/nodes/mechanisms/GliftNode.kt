package org.firstinspires.ftc.teamcode.nodes.mechanisms

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.messages.IncrementState

class GliftNode : Node("Glyph Lift") {
    override fun subscriptions() {
        this.subscribe("/glift/increment_up",  { this.incrementUp() })
        this.subscribe("/glift/increment_down", { this.incrementDown() })
        this.subscribe("/glift", {receiveMessage(it)})
    }

    /**
     * Safety measure so lower gripper doesn't get caught on rails
     */
//    fun safetyClose() {
//        this.publish("/glyph/lower", GripperMsg(GripperState.CLOSED, 2))
//    }

    fun receiveMessage(m: Message) {
        val (state) = m as LiftMsg
        when (state) {
            LiftState.TOP -> receiveTopMessage()
            LiftState.MIDDLE -> receiveMiddleMessage()
            LiftState.UPPER_BOTTOM -> receiveHigherDownMessage()
            LiftState.BOTTOM -> receiveDownMessage()
        }
    }
    fun receiveTopMessage() {
        this.publish("/servos/liftServo", ServoMsg(0.87, priority = 1))
//        safetyClose()
    }
    fun receiveHigherDownMessage() {
        this.publish("/servos/liftServo", ServoMsg(0.575, priority = 1))
//        safetyClose()
    }
    fun receiveDownMessage() {
        this.publish("/servos/liftServo", ServoMsg(0.55, priority = 1))
//        safetyClose()
    }
    fun receiveMiddleMessage() {
        this.publish("/servos/liftServo", ServoMsg(0.71, priority = 1))
//        safetyClose()
    }
    fun incrementUp() {
        this.publish("/servos/liftServo", IncrementMsg(IncrementState.INCREMENT, -0.025))
    }
    fun incrementDown() {
        this.publish("/servos/liftServo", IncrementMsg(IncrementState.INCREMENT, 0.025))
//        safetyClose()
    }
}
