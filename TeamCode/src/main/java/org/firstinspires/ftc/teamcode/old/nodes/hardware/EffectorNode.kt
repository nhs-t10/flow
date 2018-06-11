package org.firstinspires.ftc.teamcode.old.nodes.hardware

import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.old.messages.*
import org.firstinspires.ftc.teamcode.old.nodes.HeartbeatNode
import java.util.*

/**
 * Created by shaash on 10/17/17.
 */

class EffectorNode(val hardwareMap: HardwareMap) : HeartbeatNode("Effectors"){
    val motors = HashMap<String, DcMotor>()
    val servos = HashMap<String, Servo>()
    val crServos = HashMap<String, CRServo>()

    val crServoStates = HashMap<String, Double>()
    val servoStates = HashMap<String, Double>()

    init {
    }
    override fun subscriptions() {
        subscribe("/macros/cancel", {stopMovement()})
        addMotors()
        addServos()
        addCrServos()
    }

    fun stopMovement() {
        motors.keys.forEach {
            callMotor(it, MotorMsg(0.0, 0))
        }
    }

    override fun heartbeatEnd() {
        for (m in motors.keys) {
            motors[m]?.setPower(0.0)
        }
        for (s in servos.keys) {
            servos[s]?.setPosition(0.0)
        }
        for (c in crServos.keys) {
            crServos[c]?.setPower(0.0)
        }
    }

    override fun onHeartbeat() {
        this.thumpCrServos()
        this.thumpServos()
    }
    fun addMotors(){
        motors.put("lf", hardwareMap.dcMotor.get("m3"))
        motors["lf"]?.direction = DcMotorSimple.Direction.REVERSE
        motors.put("lr", hardwareMap.dcMotor.get("m1"))
        motors["lr"]?.direction = DcMotorSimple.Direction.REVERSE
        motors.put("rf", hardwareMap.dcMotor.get("m4"))
        motors.put("rr", hardwareMap.dcMotor.get("m2"))
        motors.put("rainbow", hardwareMap.dcMotor.get("m5"))
        motors.put("glift", hardwareMap.dcMotor.get("m6"))
        //motors.put("g1", hardwareMap.dcMotor.get("m5")!!)


        // DO NOT ADD MOTORS BELOW THIS LINE

        for(key in motors.keys){
            this.subscribe("/motors/$key", {callMotor(key, it)})
        }
    }
    fun addServos() {
        servos.put("bottomServo", hardwareMap.servo.get("s0"))
        servos.put("topServo", hardwareMap.servo.get("s1"))
        servos.put("knocker_yaw", hardwareMap.servo.get("s2"))
        servos.put("knocker_pitch", hardwareMap.servo.get("s3"))
        servos.put("tilter", hardwareMap.servo.get("s4"))
        servos.put("raingripper", hardwareMap.servo.get("s5"))
        servos.put("wood", hardwareMap.servo.get("s6"))

        // DO NOT ADD SERVOS BELOW THIS LINE

        for (key in servos.keys) {
            this.subscribe("/servos/$key", { callServo(key, it) })
        }
        addServoStates()

        // specific default servo values
        servoStates.put("liftServo", 0.55)
        servoStates.put("bottomServo", 0.0)
        servoStates.put("topServo", 0.3)
        servoStates.put("raingripper", 0.88)
        servoStates.put("tilter", 0.2)
        servoStates.put("wood", 0.0)
        servoStates.put("knocker_yaw", 0.675)
        servoStates.put("knocker_pitch", 0.15)
    }

    fun addCrServos() {
        for(key in crServos.keys){
            this.subscribe("/crServos/$key", {callCrServo(key, it)})
        }
        addCrServoStates()
    }

    fun addCrServoStates() {
        for(key in crServos.keys) {
            crServoStates.put(key, crServos[key]?.getPower()!!)
        }
    }

    fun addServoStates() {
        for (key in servos.keys) {
            servoStates.put(key, 0.5) // moves everything to 0.5 by default
        }
    }

    fun thumpCrServos() {
        for (key in crServos.keys) {
            crServos[key]?.setPower(crServoStates[key]!!)
        }
    }


    fun thumpServos() {
        for (key in servos.keys) {
            setServoPosition(key, servoStates[key] ?: 0.0)
        }
    }

    fun callMotor(motorName : String, motorMsg: MessageOld){
        val (power) = motorMsg as MotorMsg
        if (motors[motorName] != null){
            motors[motorName]?.setPower(power)
        }
        else {
            this.publish("/warn", TextMsg("$motorName does not exist. Check effectors list."))
        }
    }

    fun clipValue(value: Double) = Range.clip(value, -1.0, 1.0)

    fun callServo(servoName : String, msg: MessageOld){
        if (msg is ServoMsg) {
            val (position) = msg
            setServoPosition(servoName, position)
        }
        else if (msg is IncrementMsg) {
            val (state, increment) = msg
            val pos = when (state) {
                IncrementState.INCREMENT -> ((servoStates[servoName] ?: 0.0) + increment)
                IncrementState.ZERO -> 0.0
                IncrementState.HOLD -> servoStates[servoName] ?: 0.0
            }
            val clippedPos = clipValue(pos)
            setServoPosition(servoName, clippedPos)
            this.publish("/debug", TextMsg("Incremented $servoName to $clippedPos"))
        }
    }
    fun setServoPosition(servoName: String, position : Double) {
        val s = servos[servoName]
        if (s != null) {
            val clippedPos = clipValue(position)
            s.setPosition(clippedPos)
            servoStates.put(servoName, clippedPos)
        }
        else {
            this.publish("/warn", TextMsg("$servoName does not exist. Check effectors list."))
        }
    }

    fun callCrServo(crServoName : String, msg: MessageOld){
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
                    IncrementState.ZERO -> {
                        s?.setPower(0.0)
                        crServoStates.put(crServoName, 0.0)
                    }
                    IncrementState.HOLD -> s?.setPower(s.getPower())
                }
                this.publish("/debug", TextMsg("Incremented $crServoName to ${s?.getPower()}"))
            }
        }
    }
}
