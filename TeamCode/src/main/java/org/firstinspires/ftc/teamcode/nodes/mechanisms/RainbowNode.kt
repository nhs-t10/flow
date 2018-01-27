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

        this.subscribe("/rainbow/tilter/eject", {goToPos(0.3)})
        this.subscribe("/rainbow/tilter/over_wall", {goToPos(0.5)})
        this.subscribe("/rainbow/tilter/increment_up", {recieveTiltUpMessage(0.025)})
        this.subscribe("/rainbow/tilter/increment_down", {recieveTiltDownMessage(-0.025)})
        this.subscribe("/rainbow/tilter/increment_up/fast", {recieveTiltUpMessage(0.05)})
        this.subscribe("/rainbow/tilter/increment_down/fast", {recieveTiltDownMessage(-0.05)})
    }

    fun receiveGripMessage(m: Message) {
        val (liftstatus) = m as GripperMsg
        when (liftstatus) {
            GripperState.OPEN -> receiveOpenMessage()
            GripperState.CLOSED -> receiveClosedMessage()
            GripperState.MIDDLE -> Unit
        }
    }

    fun goToPos (pos: Double) {
        publish("/servos/tilter", ServoMsg(pos, 1))
    }

    fun recieveExtendMessage(m: Message){
        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/rainbow", MotorMsg(power = value.toDouble()*-1, priority = 1))
    }

    fun recieveRetractMessage(m:Message){
        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/rainbow", MotorMsg(power = (value.toDouble()), priority = 1))
    }

    fun recieveTiltUpMessage(interval: Double){
        publish("/servos/tilter", IncrementMsg(state = IncrementState.INCREMENT, increment = interval, priority = 1))
    }
//.77
    fun recieveTiltDownMessage(interval: Double){
        publish("/servos/tilter", IncrementMsg(state = IncrementState.INCREMENT, increment = interval, priority = 1))
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