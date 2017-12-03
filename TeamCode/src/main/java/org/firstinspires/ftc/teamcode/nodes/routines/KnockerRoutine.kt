package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by shaash on 12/3/2017.
 */
class KnockerRoutine(val team : TeamColor = TeamColor.RED) : RoutineNode (name = "Knocker Routine"){
    var turned = false

    override fun start() {
        val downPosition = 0.0
        this.publish("/servos/knocker", ServoMsg(downPosition, 1))
    }

    override fun subscriptions() {
        this.subscribe("/colors/colorOne") { setColors(it) }
    }

    fun setColors(m : Message){
        if(!turned){
            knockBall(m)
            turned = true
        }
    }

    fun knockBall(m : Message){
        val (red, blue, green) = m as ColorMsg
        if (red > blue){
            if(team == TeamColor.RED){
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, {end()}, 1))
            } else {
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, {end()}, 1))
            }
        } else {
            if(team == TeamColor.BLUE){
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, {end()}, 1))
            } else {
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, {end()}, 1))
            }
        }

    }

}