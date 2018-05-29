package org.firstinspires.ftc.teamcode.old.nodes.mechanisms

import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.*

/**
 * Created by dvw06 on 1/24/18.
 */

class RainbowNodeOld : NodeOld("Rainbow Lift") {
    enum class STATE {
        MACRO_EXTENDING,
        EXTENDED,
        MACRO_RETRACTING,
        RETRACTED,
        NORMAL
    }
    var state = STATE.NORMAL

    override fun subscriptions() {
        this.subscribe("/rainbow/gripper", { receiveGripMessage(it)})
        this.subscribe("/rainbow/extender/extend", {recieveExtendMessage(it)})
        this.subscribe("/rainbow/extender/retract", {recieveRetractMessage(it)})
        subscribe("/rainbow/macroExtend", {macroExtend()})
        subscribe("/rainbow/macroRetract", {macroRetract()})

        subscribe("/digital/rainbowUp", {touchedUp(it)})
        subscribe("/digital/rainbowDown", {touchedDown(it)})
        subscribe("/macros/cancel", {cancel()})

        this.subscribe("/rainbow/tilter/eject", {goToPos(0.36)})
        this.subscribe("/rainbow/tilter/over_wall", {goToPos(0.625)})
        this.subscribe("/rainbow/tilter/increment_up", {recieveTiltUpMessage(0.025)})
        this.subscribe("/rainbow/tilter/increment_down", {recieveTiltDownMessage(-0.025)})
        this.subscribe("/rainbow/tilter/increment_up/fast", {recieveTiltUpMessage(0.05)})
        this.subscribe("/rainbow/tilter/increment_down/fast", {recieveTiltDownMessage(-0.05)})

    }

    fun cancel () {
        publish("/motors/rainbow", MotorMsg(power = 0.0, priority = 1))
    }

    fun touchedUp (m: Message) {
        val (value) = m as DigitalMsg
        // Make sure touch is down and we r moving
        if (value && state == STATE.MACRO_EXTENDING) {
            state = STATE.EXTENDED
            publish("/motors/rainbow", MotorMsg(power = 0.0, priority = 1))
        }

    }

    fun macroExtend() {
        if(state!=STATE.EXTENDED){
            state = STATE.MACRO_EXTENDING
            publish("/motors/rainbow", MotorMsg(power = -1.0, priority = 1))
        } else {
            publish("/warn", TextMsg("Already extendo"))
        }
    }

    fun touchedDown (m: Message) {
        val (value) = m as DigitalMsg
        // Make sure touch is down and we r moving
        if (value && state == STATE.MACRO_RETRACTING) {
            state = STATE.RETRACTED
            publish("/motors/rainbow", MotorMsg(power = 0.0, priority = 1))
        }

    }

    fun macroRetract() {
        if(state!=STATE.RETRACTED){
            state = STATE.MACRO_RETRACTING

//            publish("/motors/rainbow", MotorMsg(power = 1.0, priority = 1))
        } else {
            publish("/warn", TextMsg("Already retracted"))
        }
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