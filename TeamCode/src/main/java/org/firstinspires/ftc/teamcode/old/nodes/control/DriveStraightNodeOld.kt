package org.firstinspires.ftc.teamcode.old.nodes.control

import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.lib.PID
import org.firstinspires.ftc.teamcode.old.messages.*

/**
 * Created by dvw06 on 1/18/18.
 */
class DriveStraightNodeOld : NodeOld("Drive Straight") {
    var kP : Double = 17.5
    var kD : Double = 17.0
    var kI : Double = 0.0

    var destAngle = 0.0
    var turn = PID(kP, kI, kD) // temp instance
    var power = 0.0
    var sideways = false

    var turning = false
    override fun subscriptions() {
        this.subscribe("/stop", {this.stopTurn()})
        this.subscribe("/imu", { this.update((it as ImuMsg).heading)})
        this.subscribe("/drive/straight", {this.setTurnTo(it)})
        this.subscribe("/AngleTurning/cancel", {this.stopTurn()})
        this.subscribe("/AngleTurning/kP", {this.setkP(it)})
        this.subscribe("/AngleTurning/kI", {this.setkI(it)})
        this.subscribe("/AngleTurning/kD", {this.setkD(it)})
    }

    fun increment(value : Double, m: IncrementMsg) = when (m.state){
        IncrementState.INCREMENT -> value + m.increment
        IncrementState.ZERO -> 0.0
        IncrementState.HOLD -> value
    }

    fun setkP(m: MessageOld) {
        kP = increment(kP, m as IncrementMsg)
        this.publish("/debug", TextMsg("DriveStraight: Incremented kP to $kP"))
    }
    fun setkI(m: MessageOld) {
        kI = increment(kI, m as IncrementMsg)
        this.publish("/debug", TextMsg("DriveStraight: Incremented kI to $kI"))
    }
    fun setkD(m: MessageOld) {
        kD = increment(kD, m as IncrementMsg)
        this.publish("/debug", TextMsg("DriveStraight: Incremented kD to $kD"))
    }

    fun stopTurn() {
        turning = false
        this.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
    }

    fun setTurnTo(m : MessageOld){
        val (angle, power, sideways) = m as DriveStraightMsg
        if (power == 0.0) {
            stopTurn()
        }
        else {
            turn = PID(kP, kI, kD)
            destAngle = angle
            this.power = power
            this.sideways = sideways
            turning = true
        }
    }

    fun update(heading : Double) {
        if(turning){
            val rotation = (getRotation(heading)).toFloat()
            val floatyPower = power.toFloat()
            this.publish("/drive", OmniDrive(rotation = -1f * rotation,
                    leftRight = (if (sideways) floatyPower else 0.0f),
                    upDown = (if (!sideways) floatyPower else 0.0f), priority = 2)
            )
        }
    }


    fun getRotation(heading : Double):Double{
        val error = getError(destAngle+180, heading+180)
        // determine if error done
//        if (abs(error) < stopThreshold) return 0.0

        val rotation = turn.computePID(error)/180.0 //converts angle to motor vals
        // just in case, but angle to turn should never be > 1.
        // Don't want to break the motors while testing (can take out later):
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