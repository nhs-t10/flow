package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.lib.AngleTurning
import org.firstinspires.ftc.teamcode.lib.PID
import org.firstinspires.ftc.teamcode.messages.ImuMsg
import org.firstinspires.ftc.teamcode.messages.MotorMsg

/**
 * Created by shaash on 11/12/17.
 */

class AngleTurningNode : Node("Angle Turning Test") {
    val turn1 = AngleTurning(kI = 0.1, kD = 0.0, kP = 0.1, destination = 30.0)
    override fun subscriptions() {
        this.subscribe("/imu", {update(it as ImuMsg)})
    }
    fun update (imuData : ImuMsg){
        val heading = (imuData.heading)
        val motorvals = turn1.computeTurnMotorVals(heading)
        this.publish("/motors/lf", MotorMsg(motorvals[0], priority = 1))
        this.publish("/motors/rf", MotorMsg(motorvals[1], priority = 1))
        this.publish("/motors/lr", MotorMsg(motorvals[2], priority = 1))
        this.publish("/motors/rr", MotorMsg(motorvals[3], priority = 1))
    }
}
