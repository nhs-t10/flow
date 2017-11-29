package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.lib.AngleTurning
import org.firstinspires.ftc.teamcode.messages.ImuMsg
import org.firstinspires.ftc.teamcode.messages.AngleTurnMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.OmniDrive
/**
 * Created by shaash on 11/12/17.
 */

class AngleTurningNode : Node("Angle Turning Test") {
    var tempDest = 0.0
    var cb : (() -> Unit)? = null

    override fun subscriptions() {
        this.subscribe("/imu", { this.update((it as ImuMsg).heading)})
        this.subscribe("/AngleTurning/turnTo", {this.setTurnTo(it as AngleTurnMsg)})
    }

    fun setTurnTo(m : Message){
        val (angle, callback) = m as AngleTurnMsg
        this.tempDest = angle
        this.cb = callback
    }

    fun update(heading : Double) {
        val turn1 = AngleTurning(destination = tempDest)
        val rotation = (turn1.computeHeading(heading)).toFloat()
        if(rotation < 0.1){
            if (cb != null){
                this.cb?.invoke()
            }
        }
        this.publish("/drive", OmniDrive(rotation = rotation, leftRight = 0f, upDown = 0f, priority = 1))

        /*
        this.publish("/motors/lf", MotorMsg(motorvals[0], priority = 1))
        this.publish("/motors/rf", MotorMsg(motorvals[1], priority = 1))
        this.publish("/motors/lr", MotorMsg(motorvals[2], priority = 1))
        this.publish("/motors/rr", MotorMsg(motorvals[3], priority = 1))
        */

    }
}
