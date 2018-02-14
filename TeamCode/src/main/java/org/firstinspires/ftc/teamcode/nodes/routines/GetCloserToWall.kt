package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by dvw06 on 1/16/18.
 */

class GetCloserToWall() : RoutineNode("Strafe to Wall"){

    override fun begin() {
        this.publish("/drive", OmniDrive(upDown = 0.3f, rotation = 0f, leftRight = 0f, priority = 1))
    }

    override fun subscriptions() {
        subscribe("/digital/touch1", {setTrue(it)})
    }

    fun setTrue(m : Message){
        val (value) = m as DigitalMsg
        if(!value){
            this.publish("/drive", OmniDrive(upDown = 0f, rotation = 0f, leftRight = -0.2f, priority = 1))
        } else {
            this.publish("/drive", OmniDrive(upDown = 0f, rotation = 0f, leftRight = 0f, priority = 1))
            end()
        }
    }

}
