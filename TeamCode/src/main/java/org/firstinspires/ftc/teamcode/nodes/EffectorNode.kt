package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.motor

/**
 * Created by shaash on 10/17/17.
 */
class EffectorNode : Node{
    var hardwareMap: HardwareMap? = null
    val motors = HashMap<String, DcMotor>()
    constructor(hardwareMap: HardwareMap){
        this.hardwareMap = hardwareMap
        addMotors()

    }
    fun addMotors(){
        motors.put("lf", hardwareMap?.dcMotor?.get("m3")!!)
        motors.put("lr", hardwareMap?.dcMotor?.get("m1")!!)
        motors.put("rf", hardwareMap?.dcMotor?.get("m4")!!)
        motors.put("rr", hardwareMap?.dcMotor?.get("m2")!!)
        for(key in motors.keys){
            Dispatcher.subscribe("/motors/${key}", {callMotor(key, it)})
        }
    }
    fun callMotor(motorName : String, motorMsg: Message){
        val (power) = motorMsg as motor
        if (motors.get(motorName) != null){
            motors.get(motorName)?.setPower(power)
        }
    }
}