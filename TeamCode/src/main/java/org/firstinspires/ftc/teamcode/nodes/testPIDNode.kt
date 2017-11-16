package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.PID
import org.firstinspires.ftc.teamcode.messages.ImuMsg
import org.firstinspires.ftc.teamcode.messages.OmniDrive

/**
 * Created by shaash on 11/12/17.
 */

class testPIDNode : Node() {
    val turn1pid = PID(kP = 0.1, kD = 0.1, kI = 0.1, destination = 30.0)
    override fun subscriptions() {
        this.subscribe("/imu", {update(it as ImuMsg)})
    }
    fun update (imuData : ImuMsg){
        val heading = (imuData.heading)
        turn1pid.computePID(heading)
    }
}
