package org.firstinspires.ftc.teamcode.nodes.control

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.ImuMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.StraightDriveMsg

/**
 * Created by dvw06 on 1/18/18.
 */
class DriveStraightNode : Node("Drive Straight") {
    var active = false
    override fun subscriptions() {
        subscribe("/drive/straight", {receive(m = (it))} )
        subscribe("/imu", {receive(head = (it as ImuMsg).heading)})
    }
    fun receive(m : Message = StraightDriveMsg(0.0, 0.0, 1), head: Double = 0.0){
        val (angle, speed) = m as StraightDriveMsg
        if(!active){
            //this.publish("/drive", org.firstinspires.ftc.teamcode.messages.OmniDrive)
        }
    }

}