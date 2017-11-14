package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.PID
import org.firstinspires.ftc.teamcode.messages.ImuMsg
import org.firstinspires.ftc.teamcode.messages.OmniDrive

/**
 * Created by shaash on 11/12/17.
 */

class testPIDNode : Node() {
    val pid = PID()
    init {

        pid.setConstants(.2, 0.0, 0.0)
        pid.setDest(20.0)
    }
    override fun subscriptions() {
        this.subscribe("/imu", {update(it as ImuMsg)})
    }
    fun update (imuData : ImuMsg){
        pid.setCurr(imuData.heading)
        val power = pid.computeOutput().toFloat()
        this.publish("/drive", OmniDrive(leftRight = 0f, upDown = 0f, rotation = power, priority = 2))
    }
}
