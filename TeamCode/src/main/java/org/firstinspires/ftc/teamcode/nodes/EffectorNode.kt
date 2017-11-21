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

    val crServoStates = HashMap<String, Double>()
    override fun subscriptions() {
        addMotors()
        addServos()
        addCrServos()
        this.subscribe("/heartbeat", {this.thumpCrServos(it)})
        this.subscribe("/heartbeat", {this.thumpServos(it)})
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

    fun addCrServoStates() {
        for(key in crServos.keys) {
            crServoStates.put(key, crServos[key]?.getPower()!!)
        }
    }

    fun thumpCrServos(m: Message) {
        val (time) = m as HeartBeatMsg
        val timeDivided = time / 100
        for (key in crServos.keys) {
            crServos[key]?.setPower(crServoStates[key]!!)
        }
    }


    fun thumpServos(m: Message) {
        val (time) = m as HeartBeatMsg
        val timeDivided = time / 100
        for (key in servos.keys) {
            servos[key]?.setPosition(servos[key]?.getPosition()!!)
        }
    }

    fun addCrServos() {
        crServos.put("liftServo", hardwareMap.crservo.get("cr0")!!)
        for(key in crServos.keys){
            this.subscribe("/crServos/$key", {callCrServo(key, it)})
        }
        addCrServoStates()
    }

    fun callMotor(motorName : String, motorMsg: Message){
        val (power) = motorMsg as MotorMsg
        if (motors[motorName] != null){
            motors[motorName]?.setPower(power)
        }
    }
    fun callServo(servoName : String, msg: Message){
        if (msg is ServoMsg) {
            val (position) = msg
            if (servos[servoName] != null){
                servos[servoName]?.setPosition(position)
            }
        }
        else if (msg is IncrementMsg) {
            val (state, increment) = msg
            if (servos[servoName] != null) {
                val s = servos[servoName]
                when (state) {
                    IncrementState.INCREMENT -> s?.setPosition(s.getPosition() + increment)
                    IncrementState.ZERO -> s?.setPosition(0.0)
                    IncrementState.HOLD -> s?.setPosition(s.getPosition())
                }
                this.publish("/debug", TextMsg("Incremented $servoName to ${s?.getPosition()}"))
            }
        }
    }
    fun callCrServo(crServoName : String, msg: Message){
        if(crServos[crServoName] != null){
            if (msg is MotorMsg) {
                val (power) = msg
                crServos[crServoName]?.setPower(power)
                crServoStates.put(crServoName, power)
            }
            else if (msg is IncrementMsg) {
                val (state, increment) = msg
                val s = crServos[crServoName]
                when (state) {
                    IncrementState.INCREMENT -> {
                        val p = crServoStates[crServoName]!! + increment
                        s?.setPower(p)
                        crServoStates.put(crServoName, p)
                    }
                    IncrementState.ZERO -> s?.setPower(0.0)
                    IncrementState.HOLD -> s?.setPower(s.getPower())
                }
                this.publish("/debug", TextMsg("Incremented $crServoName to ${s?.getPower()}"))
            }
        }
    }
}
