package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by dvw06 on 1/16/18.
 */

class SmashIntoWall() : RoutineNode("Strafe to Wall"){

    override fun begin() {
        this.publish("/drive/straight", DriveStraightMsg(-90.0, 0.2, false,1))
    }

    override fun subscriptions() {
        subscribe("/digital/front_contact", {receivedTouch(it)})
    }

    fun receivedTouch(m : Message){
        val (value) = m as DigitalMsg
        if(value){
            this.publish("/drive/straight", DriveStraightMsg(-90.0, 0.0, false, 1))
            end()
        }
    }
}