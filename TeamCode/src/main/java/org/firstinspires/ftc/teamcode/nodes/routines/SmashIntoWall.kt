package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by dvw06 on 1/16/18.
 */

class SmashIntoWall() : RoutineNode("Strafe to Wall"){

    override fun begin() {
        this.publish("/drive", OmniDrive(upDown = 0.25f, rotation = 0f, leftRight = 0f, priority = 1))
    }

    override fun subscriptions() {
        subscribe("/digital/touch2", {setTrue(it)})
    }

    fun setTrue(m : Message){
        val (value) = m as DigitalMsg
        if(value){
            this.publish("/drive", OmniDrive(upDown = 0f, rotation = 0f, leftRight = 0f, priority = 1))
            end()
        }
    }
}
