package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import java.util.*

/**
 * Created by shaash on 10/17/17.
 */

class EffectorNode(val hardwareMap: HardwareMap) : Node("Effectors"){
    val motors = HashMap<String, DcMotor>()
    val servos = HashMap<String, Servo>()
    val crServos = HashMap<String, CRServo>()
    override fun subscriptions() {
        addMotors()
        addServos()
        addCrServos()
    }
    fun addMotors(){
        motors.put("lf", hardwareMap.dcMotor.get("m3")!!)
        motors["lf"]?.direction = DcMotorSimple.Direction.REVERSE
        motors.put("lr", hardwareMap.dcMotor.get("m1")!!)
        motors["lr"]?.direction = DcMotorSimple.Direction.REVERSE
        motors.put("rf", hardwareMap.dcMotor.get("m4")!!)
        motors.put("rr", hardwareMap.dcMotor.get("m2")!!)
        motors.put("g1", hardwareMap.dcMotor.get("m5")!!)
        for(key in motors.keys){
            this.subscribe("/motors/$key", {callMotor(key, it)})
        }
    }
    fun addServos() {
        servos.put("bottomServo", hardwareMap.servo.get("s0")!!)
        servos.put("topServo", hardwareMap.servo.get("s1")!!)
        // servos.put("holderServoL", hardwareMap.servo.get("s2")!!)
        // servos.put("holderServoR", hardwareMap.servo.get("s3")!!)

        for (key in servos.keys) {
            this.subscribe("/servos/$key", { callServo(key, it) })
        }
    }
    fun addCrServos() {
        crServos.put("liftServo", hardwareMap.crservo.get("cr0")!!)
        for(key in crServos.keys){
            this.subscribe("/crServos/$key", {callCrServo(key, it)})
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
        if (servos[servoName] != null){ servos[servoName]?.setPosition(position)
        }
    }
    fun callCrServo(crServoName : String, motorMsg: Message){
        val(power) = motorMsg as MotorMsg
        if(crServos[crServoName] != null){
            crServos[crServoName]?.setPower(power)
        }
    }
}
