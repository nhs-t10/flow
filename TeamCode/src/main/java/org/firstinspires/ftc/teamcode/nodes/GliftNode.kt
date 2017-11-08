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

    constructor(){
        Dispatcher.subscribe("/gamepad1/dpad_up", {this.recieveUpMessage(upPower = (it as gamepadButtonMsg).value)})
        Dispatcher.subscribe("/gamepad1/dpad_down", {this.recieveDownMessage(downPower = (it as gamepadButtonMsg).value)})
    }

    fun recieveUpMessage(upPower : Boolean) {
           Dispatcher.publish("/motors/g1", MotorMsg((if (upPower)0.5 else 0.0), priority = 1))
    }
    fun recieveDownMessage(downPower : Boolean) {
        if(downPower){
            Dispatcher.publish("/motors/g1", MotorMsg((if (downPower)-0.5 else 0.0), priority = 1))
        }
    }
}
