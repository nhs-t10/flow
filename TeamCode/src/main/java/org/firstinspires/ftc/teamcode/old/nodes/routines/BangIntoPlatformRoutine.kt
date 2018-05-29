package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.*

/**
 * Created by dvw06 on 1/16/18.
 */

class BangIntoPlatformRoutine() : RoutineNode("Strafe to Wall"){

    override fun begin() {
        this.publish("/drive", OmniDrive(upDown = 0f, rotation = 0f, leftRight = 0.3f, priority = 1))
    }

    override fun subscriptions() {
        subscribe("/digital/touch1", {recieveTouch(it)})
    }

    fun recieveTouch(m : Message){
        val (value) = m as DigitalMsg
        if(value){
            this.publish("/drive", OmniDrive(upDown = 0f, rotation = 0f, leftRight = 0f, priority = 1))
            end()
        }
    }
}
