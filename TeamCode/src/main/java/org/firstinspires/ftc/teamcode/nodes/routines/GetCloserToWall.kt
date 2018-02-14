package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by dvw06 on 1/16/18.
 */

class GetCloserToWall() : RoutineNode("Strafe to Wall"){
    val distancethresh = 0.05
    override fun begin() {
        this.publish("/drive", OmniDrive(upDown = 0.3f, rotation = 0f, leftRight = 0f, priority = 1))
    }

    override fun subscriptions() {
        subscribe("/analog/ultra1", {setTrue(it)})
    }

    fun setTrue(m : Message){
        val (value) = m as AnalogMsg
        if(value < distancethresh){
            this.publish("/drive", OmniDrive(upDown = 0f, rotation = 0f, leftRight = -0.2f, priority = 1))
        } else {
            end()
        }
    }
}
