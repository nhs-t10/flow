package org.firstinspires.ftc.teamcode.nodes.control

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.messages.GamepadJoyOrTrigMsg
import org.firstinspires.ftc.teamcode.messages.TextMsg

class OmniJoyNode : Node("Omni Joystick") {
    var tempRotation: Float = 0f
    var tempUpDown: Float = 0f
    var tempLeftRight: Float = 0f

    var gamepad1Locked = false

    override fun subscriptions() {
        this.subscribe("/gamepad1/left_stick_y", {this.recieveMessage(gamepadNumber = 1, upDown = (it as GamepadJoyOrTrigMsg).value)})
        this.subscribe("/gamepad1/right_stick_x", {this.recieveMessage(gamepadNumber = 1, rotation = (it as GamepadJoyOrTrigMsg).value)})
        this.subscribe("/gamepad1/left_stick_x", {this.recieveMessage(gamepadNumber = 1, leftRight = (it as GamepadJoyOrTrigMsg).value)})


        this.subscribe("/gamepad2/left_stick_y", {this.recieveMessage(gamepadNumber = 2, upDown = (it as GamepadJoyOrTrigMsg).value/2)})
        this.subscribe("/gamepad2/right_stick_x", {this.recieveMessage(gamepadNumber = 2, rotation = (it as GamepadJoyOrTrigMsg).value/2)})
        this.subscribe("/gamepad2/left_stick_x", {this.recieveMessage(gamepadNumber = 2, leftRight = (it as GamepadJoyOrTrigMsg).value/2)})
    }

    // Possesses drive channel if joysticks being used
    fun deadLock(gamepadNumber : Int) {
        // lock driver 1 if in use, and unconditionally lock drive channel
        if (tempUpDown != 0f || tempLeftRight != 0f || tempRotation != 0f) {
            if(gamepadNumber == 1) gamepad1Locked = true
            Dispatcher.lock("/drive", 1)
        }
        else { // unlock driver 1 if in use, and unconditionally unlock drive channel
            if (gamepadNumber == 1) gamepad1Locked = false
            Dispatcher.unlock("/drive")
        }
    }

    fun recieveMessage(gamepadNumber: Int, rotation : Float = this.tempRotation, upDown : Float = this.tempUpDown, leftRight: Float = this.tempLeftRight) {
        // Reject gamepad2 when gamepad1 locks
        if (!(gamepad1Locked && gamepadNumber > 1)) {
            if (rotation != this.tempRotation) {
                this.tempRotation = rotation
                deadLock(gamepadNumber)
            }
            if (upDown != this.tempUpDown) {
                this.tempUpDown = upDown
                deadLock(gamepadNumber)
            }
            if (leftRight != this.tempLeftRight) {
                this.tempLeftRight = leftRight
                deadLock(gamepadNumber)
            }
            this.publish("/drive", OmniDrive(this.tempUpDown, this.tempLeftRight, this.tempRotation, priority = 1))
        }
        else {
            publish("/warn", TextMsg("Driver 2 attempting to override Driver 1's joystick movement."))
        }
    }

}
