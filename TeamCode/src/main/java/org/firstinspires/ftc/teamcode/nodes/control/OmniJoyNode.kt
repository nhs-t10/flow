package org.firstinspires.ftc.teamcode.nodes.control

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.messages.GamepadJoyOrTrigMsg

class OmniJoyNode : Node("Omni Joystick") {
    var tempRotation: Float = 0f
    var tempUpDown: Float = 0f
    var tempLeftRight: Float = 0f

    override fun subscriptions() {
        this.subscribe("/gamepad1/left_stick_y", {this.recieveMessage(upDown = (it as GamepadJoyOrTrigMsg).value)})
        this.subscribe("/gamepad1/right_stick_x", {this.recieveMessage(rotation = (it as GamepadJoyOrTrigMsg).value)})
        this.subscribe("/gamepad1/left_stick_x", {this.recieveMessage(leftRight = (it as GamepadJoyOrTrigMsg).value)})
    }

    // Possesses drive channel if joysticks being used
    fun zeroLock() {
        if (tempUpDown != 0f || tempLeftRight != 0f || tempRotation != 0f) Dispatcher.lock("/drive", 1)
        else Dispatcher.unlock("/drive")
    }

    fun recieveMessage(rotation : Float = this.tempRotation, upDown : Float = this.tempUpDown, leftRight: Float = this.tempLeftRight) {
        if (rotation != this.tempRotation) {
            this.tempRotation = rotation
            zeroLock()
        }
        if (upDown != this.tempUpDown) {
            this.tempUpDown = upDown
            zeroLock()
        }
        if (leftRight != this.tempLeftRight) {
            this.tempLeftRight = leftRight
            zeroLock()
        }


        this.publish("/drive", OmniDrive(this.tempUpDown, this.tempLeftRight, this.tempRotation, priority = 1))
    }

}
