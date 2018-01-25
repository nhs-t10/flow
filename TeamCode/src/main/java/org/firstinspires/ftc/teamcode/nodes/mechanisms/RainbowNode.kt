package org.firstinspires.ftc.teamcode.nodes.mechanisms

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by dvw06 on 1/24/18.
 */

class RainbowNode : Node("Rainbow Lift") {

    override fun subscriptions() {
        this.subscribe("/rainbow/gripper", { receiveGripMessage(it)})
        this.subscribe("/rainbow/extender/extend", {recieveExtendMessage(it)})
        this.subscribe("/rainbow/extender/retract", {recieveRetractMessage(it)})
        this.subscribe("/rainbow/tilter/increment_up", {recieveTiltUpMessage()})
        this.subscribe("/rainbow/tilter/increment_down", {recieveTiltDownMessage()})
    }

    fun receiveGripMessage(m: Message) {
        val (liftstatus) = m as GripperMsg
        when (liftstatus) {
            GripperState.OPEN -> receiveOpenMessage()
            GripperState.CLOSED -> receiveClosedMessage()
            GripperState.MIDDLE -> Unit
        }
    }

    fun recieveExtendMessage(m: Message){
        val (value) = m as GamepadJoyOrTrigMsg

        publish("/motors/rainbow", MotorMsg(power = value.toDouble(), priority = 1))
    }

    fun recieveRetractMessage(m:Message){
        val (value) = m as GamepadJoyOrTrigMsg

        publish("/motors/rainbow", MotorMsg(power = (value.toDouble()*-1), priority = 1))
    }

    fun recieveTiltUpMessage(){
        publish("/servos/tilter", IncrementMsg(state = IncrementState.INCREMENT, increment = .1, priority = 1))
    }

    fun recieveTiltDownMessage(){
        publish("/servos/tilter", IncrementMsg(state = IncrementState.INCREMENT, increment = -.1, priority = 1))
    }

    fun receiveOpenMessage() {
        this.publish("/servos/raingripper", ServoMsg(0.2, priority = 1))
//        safetyClose()
    }

    fun receiveClosedMessage() {
        this.publish("/servos/raingripper", ServoMsg(0.88, priority = 1))
//        safetyClose()
    }
}