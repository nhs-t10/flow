package org.firstinspires.ftc.teamcode.nodes

/**
 * Created by shaash on 10/15/17.
 */

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeat
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.messages.gamepadJoyOrTrigMsg
import java.lang.Math.abs

class OmniJoyNode : Node{
    var lf : DcMotor? = null
    var lr : DcMotor? = null
    var rf : DcMotor? = null
    var rr : DcMotor? = null
    var slowMode = false
    var tempRotation: Float = 0f
    var tempUpDown: Float = 0f
    var tempLeftRight: Float = 0f
    var forwardsComponent = listOf<Float>(0f, 0f, 0f, 0f)
    var eastWestComponent = listOf<Float>(0f, 0f, 0f, 0f)
    var rotationalComponent = listOf<Float>(0f, 0f, 0f, 0f)

    constructor(){
        Dispatcher.subscribe("/gamepad1/right_stick_x", {this.recieveMessage(rotation = (it as gamepadJoyOrTrigMsg).value)})
        Dispatcher.subscribe("/gamepad1/left_stick_y", {this.recieveMessage(upDown = (it as gamepadJoyOrTrigMsg).value)})
        Dispatcher.subscribe("/gamepad1/left_stick_x", {this.recieveMessage(leftRight = (it as gamepadJoyOrTrigMsg).value)})

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
        Dispatcher.publish("/drive", OmniDrive(this.tempUpDown, this.tempLeftRight, this.tempRotation, priority = 4))
    }

}
