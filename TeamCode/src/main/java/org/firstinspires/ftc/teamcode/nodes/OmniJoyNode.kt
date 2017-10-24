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

    constructor(/*hardwareMap: HardwareMap*/){
        /*
        this.lf = hardwareMap.dcMotor.get("m3")
        this.lr = hardwareMap.dcMotor.get("m1")
        this.rf = hardwareMap.dcMotor.get("m4")
        this.rr = hardwareMap.dcMotor.get("m2")
        lf?.direction = REVERSE
        lr?.direction = REVERSE
        */
        Dispatcher.subscribe("/heartbeat", {this.loop(it as HeartBeat)})
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
    /*

    fun drive(forwardback : List<Float>, leftright : List<Float>, rotation : List<Float>):List<Float>{
        val sumlf = forwardback[0] + leftright[0] + rotation[0]
        val sumrf = forwardback[1] + leftright[1] + rotation[1]
        val sumlr = forwardback[2] + leftright[2] + rotation[2]
        val sumrr = forwardback[3] + leftright[3] + rotation[3]
        val sums = arrayOf(sumlf, sumrf, sumlr, sumrr)
        val findhighestinthis = sums.map{abs(it)}
        val highestsum:Float? = findhighestinthis.max()
        var attenuationfactor = 1f
        if (highestsum == null){
            attenuationfactor = 1f
        }

        else if (abs(highestsum) > 1f) {
            attenuationfactor = highestsum
        } else {
            attenuationfactor = 1f
        }

        val finalvals = sums.map { it / attenuationfactor }
        return finalvals
    }
*/
    fun loop(hb: HeartBeat) {
        val (time) = hb
        //val toPub : List<Float> = drive(this.forwardsComponent, this.eastWestComponent, rotationalComponent)
    }

}
