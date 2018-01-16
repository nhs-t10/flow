package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.lib.PID
import org.firstinspires.ftc.teamcode.messages.*
import java.lang.Math.abs

/**
 * Created by shaash on 11/12/17.
 */

class AngleTurningNode : Node("Angle Turning Test") {

    var kP : Double = 2.0
    var kD : Double = 0.0
    var kI : Double = 0.0
    val stopThreshold = 5.0
    var destAngle = 0.0
    var turn = PID(kP, kI, kD) // temp instance

    var cb : (() -> Unit)? = null
    var turning = false
    override fun subscriptions() {
        this.subscribe("/imu", { this.update((it as ImuMsg).heading)})
        this.subscribe("/AngleTurning/turnTo", {this.setTurnTo(it as AngleTurnMsg)})
        this.subscribe("/AngleTurning/cancel", {this.stop()})

        this.subscribe("/AngleTurning/kP", {this.setkP(it)})
        this.subscribe("/AngleTurning/kI", {this.setkI(it)})
        this.subscribe("/AngleTurning/kD", {this.setkD(it)})
    }

    fun increment(value : Double, m: IncrementMsg) = when (m.state){
        IncrementState.INCREMENT -> value + m.increment
        IncrementState.ZERO -> 0.0
        IncrementState.HOLD -> value
    }

    fun setkP(m: Message) {
        kP = increment(kP, m as IncrementMsg)
        this.publish("/debug", TextMsg("Incremented kP to $kP"))
    }
    fun setkI(m: Message) {
        kI = increment(kI, m as IncrementMsg)
        this.publish("/debug", TextMsg("Incremented kI to $kI"))
    }
    fun setkD(m: Message) {
        kD = increment(kD, m as IncrementMsg)
        this.publish("/debug", TextMsg("Incremented kD to $kD"))
    }

    fun stop() {
        turning = false
        this.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
        this.cb?.invoke()
    }

    fun setTurnTo(m : Message){
        val (angle, callback) = m as AngleTurnMsg
        turn = PID(kP, kI, kD)
        destAngle = angle
        this.cb = callback
        turning = true
    }

    fun update(heading : Double) {
        if(turning){
            val rotation = (getRotation(heading)).toFloat()
            if(rotation == 0.0f){
                this.stop()
            }
        this.publish("/drive", OmniDrive(rotation = -1f * rotation, leftRight = 0f, upDown = 0f, priority = 1))
        }

        /*
        this.publish("/motors/lf", MotorMsg(motorvals[0], priority = 1))
        this.publish("/motors/rf", MotorMsg(motorvals[1], priority = 1))
        this.publish("/motors/lr", MotorMsg(motorvals[2], priority = 1))
        this.publish("/motors/rr", MotorMsg(motorvals[3], priority = 1))
        */
    }


    fun getRotation(heading : Double):Double{
        val error = getError(destAngle+180, heading+180)
        this.publish("/debug", TextMsg("Error: $error"))
        // determine if error done
//        if (abs(error) < stopThreshold) return 0.0

        val rotation = turn.computePID(error)/180.0 //converts angle to motor vals
        // just in case, but angle to turn should never be > 1.
        // Don't want to break the motors while testing (can take out later):

        this.publish("/debug", TextMsg("Rotation: $rotation"))
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
