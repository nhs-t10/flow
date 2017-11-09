package org.firstinspires.ftc.teamcode.nodes

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.messages.GamepadJoyOrTrigMsg

class OmniJoyNode : Node {
    var tempRotation: Float = 0f
    var tempUpDown: Float = 0f
    var tempLeftRight: Float = 0f

    constructor(){

    }
    override fun init() {
        this.subscribe("/gamepad1/left_stick_y", {this.recieveMessage(rotation = (it as GamepadJoyOrTrigMsg).value)})
        this.subscribe("/gamepad1/right_stick_x", {this.recieveMessage(upDown = (it as GamepadJoyOrTrigMsg).value)})
        this.subscribe("/gamepad1/left_stick_x", {this.recieveMessage(leftRight = (it as GamepadJoyOrTrigMsg).value)})
    }

    fun recieveMessage(rotation : Float = this.tempRotation, upDown : Float = this.tempUpDown, leftRight: Float = this.tempLeftRight) {
        if (rotation != this.tempRotation) {
            this.tempRotation = rotation
        }
        if (upDown != this.tempUpDown) {
            this.tempUpDown = upDown
        }
        if (leftRight != this.tempLeftRight) {
            this.tempLeftRight = leftRight
        }
        this.publish("/drive", OmniDrive(this.tempUpDown, this.tempLeftRight, this.tempRotation, priority = 4))
    }

}
