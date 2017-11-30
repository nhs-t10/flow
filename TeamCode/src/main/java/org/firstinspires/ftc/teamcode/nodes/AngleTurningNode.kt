package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.lib.PID
import org.firstinspires.ftc.teamcode.messages.*
import java.lang.Math.abs

/**
 * Created by shaash on 11/12/17.
 */

class AngleTurningNode : Node("Angle Turning Test") {

    var kP = 0.1
    var kD = 0.2
    var kI = 0.0

    var destAngle = 0.0
    var turn = PID(kP, kI, kD) // temp instance

    var cb : (() -> Unit)? = null
    var turning = false
    override fun subscriptions() {
        this.subscribe("/imu", { this.update((it as ImuMsg).heading)})
        this.subscribe("/AngleTurning/turnTo", {this.setTurnTo(it as AngleTurnMsg)})

        this.subscribe("/AngleTurning/kP", {this.setkP(it)})
        this.subscribe("/AngleTurning/kI", {this.setkI(it)})
        this.subscribe("/AngleTurning/kD", {this.setkD(it)})
    }

    fun increment(value : Double, m: IncrementMsg) = when (m.state){
        IncrementState.INCREMENT -> value + m.increment
        IncrementState.ZERO -> 0.0
        IncrementState.HOLD -> value
    }

    fun setkP(m: Message) { kP = increment(kP, m as IncrementMsg) }
    fun setkI(m: Message) { kI = increment(kI, m as IncrementMsg) }
    fun setkD(m: Message) { kD = increment(kD, m as IncrementMsg) }

    fun setTurnTo(m : Message){
        val (angle, callback) = m as AngleTurnMsg
        turn = PID(kP, kI, kD)
        destAngle = angle + 180
        this.cb = callback
        turning = true
    }

    fun update(heading : Double) {
        if(turning){
            val rotation = (getRotation(heading)).toFloat()
            if(rotation == 0.0f){
                if (cb != null){
                    turning = false
                    this.publish("/drive", OmniDrive(rotation = 0f, leftRight = 0f, upDown = 0f, priority = 1))
                    this.cb?.invoke()
                }
            }
        this.publish("/drive", OmniDrive(rotation = rotation, leftRight = 0f, upDown = 0f, priority = 1))
        }
        /*
        this.publish("/motors/lf", MotorMsg(motorvals[0], priority = 1))
        this.publish("/motors/rf", MotorMsg(motorvals[1], priority = 1))
        this.publish("/motors/lr", MotorMsg(motorvals[2], priority = 1))
        this.publish("/motors/rr", MotorMsg(motorvals[3], priority = 1))
        */
    }


    fun getRotation(heading : Double):Double{
        val error = getError(destAngle, heading + 180)
        // determine if error done
        if (error < 5.0) return 0.0

        var rotation = turn.computePID(error)/180.0 //converts angle to motor vals
        // just in case, but angle to turn should never be > 1.
        // Don't want to break the motors while testing (can take out later):
        if(Math.abs(error) > 1){
            rotation = 1 * Math.signum(rotation)
        }
        return rotation
    }


    fun getError(dest : Double, curr: Double): Double {
        val a = dest - curr
        val b = 360 - Math.abs(a)
        if (a > 0) {
            if (Math.abs(a) > Math.abs(b)) {
                return -b
            }
            return a
        } else {
            if (Math.abs(a) > Math.abs(b)) {
                return b
            }
            return a
        }
    }

}
