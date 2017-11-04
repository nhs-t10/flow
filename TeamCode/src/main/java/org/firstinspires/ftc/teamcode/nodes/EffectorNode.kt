package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import java.util.*

/**
 * Created by shaash on 10/17/17.
 */

class EffectorNode : Node{
    var hardwareMap: HardwareMap? = null
    val motors = HashMap<String, DcMotor>()
    val servos = HashMap<String, Servo>()
    constructor(hardwareMap: HardwareMap){
        this.hardwareMap = hardwareMap
        addMotors()
        addServos()
    }
    fun addMotors(){
        motors.put("lf", hardwareMap?.dcMotor?.get("m3")!!)
        motors.put("lr", hardwareMap?.dcMotor?.get("m1")!!)
        motors.put("rf", hardwareMap?.dcMotor?.get("m4")!!)
        motors.put("rr", hardwareMap?.dcMotor?.get("m2")!!)
        for(key in motors.keys){
            Dispatcher.subscribe("/motors/$key", {callMotor(key, it)})
        }
    }
    fun addServos() {
        servos.put("topServo", hardwareMap?.servo?.get("s0")!!)
        for (key in servos.keys) {
            Dispatcher.subscribe("/servos/$key", { callServo(key, it) })
        }
    }
    fun callMotor(motorName : String, motorMsg: Message){
        val (power) = motorMsg as MotorMsg
        if (motors[motorName] != null){
            motors[motorName]?.setPower(power)
        }
    }
    fun callServo(servoName : String, msg: Message){
        val (position) = msg as ServoMsg
        if (servos[servoName] != null){
            Dispatcher.publish("/debug", DebugMsg("$position"))
            servos[servoName]?.setPosition(position)
        }
    }
}
