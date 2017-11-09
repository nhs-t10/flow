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
import org.firstinspires.ftc.teamcode.messages.*
import java.lang.Math.abs

class GliftNode : Node {
    val bottomOpenPosition = 0.4
    val bottomClosedPosition = 0.0
    val topOpenPosition = 1.0
    val topClosedPosition = 0.0
    var bottomIsOpen = true
    var topIsOpen = true

    constructor(){
    }
    override fun init() {
        this.subscribe("/gamepad1/dpad_up", {recieveUpMessage(upPower = (it as gamepadButtonMsg).value)})
        this.subscribe("/gamepad1/dpad_down", {recieveDownMessage(downPower = (it as gamepadButtonMsg).value)})
    }

    fun recieveUpMessage(upPower : Boolean) {
        if(upPower){
            this.publish("/servos/bottomServo", ServoMsg(bottomClosedPosition, priority = 1))
            this.publish("/motors/g1", MotorMsg((if (upPower)0.5 else 0.1), priority = 1))
            this.publish("/servos/topServo", ServoMsg(topOpenPosition, priority = 1))
        } else {
            this.publish("/servos/topServo", ServoMsg(topClosedPosition, priority = 1))

        }
    }
    fun recieveDownMessage(downPower : Boolean) {
        if(downPower){
            this.publish("/servos/topServo", ServoMsg(topOpenPosition, priority = 1))
            this.publish("/servos/bottomServo", ServoMsg(bottomOpenPosition, priority = 1))
            this.publish("/motors/g1", MotorMsg((if (downPower)-0.5 else 0.1), priority = 1))
        } else {
            this.publish("/servos/bottomServo", ServoMsg(bottomClosedPosition, priority = 1))
        }
    }
}
