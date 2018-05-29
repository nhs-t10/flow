package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineGroup
import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.*
import org.firstinspires.ftc.teamcode.old.util.TeamColor
import org.firstinspires.ftc.teamcode.old.util.TeamPosition

/**
 * Created by shaash on 12/3/2017.
 */

class KnockerRoutine(val team : TeamColor, val position: TeamPosition) : RoutineNode("Knocker Routine"){
    var turned = false

    val upPosition = 0.15
    val centerYawPosition = 0.575
    override fun begin() {}
    override fun subscriptions() {
        this.subscribe("/color/knocker") { doOnce(it) }
    }

    fun doOnce(m : Message){
        if(!turned){
            knockBall(m)
        }
    }

    fun createMoveRoutine(sign: Int) : RoutineGroup = RoutineGroup(listOf(
        TimedCallbackRoutine({
            publish("/servos/knocker_yaw", ServoMsg(centerYawPosition + (0.2 * sign), 1))
        }, 800, {cb ->
            publish("/servos/knocker_yaw", ServoMsg(centerYawPosition, 1))
            this.publish("/servos/knocker_pitch", ServoMsg(upPosition, 1))
            cb()
        })
    ))

    fun knockBall(m : Message) {
        val (red, blue) = m as ColorMsg
        if (red - 15 > blue){ // If red is in front
            turned = true
            publish("/status", TextMsg("Saw RED"))
            if(team == TeamColor.RED){
                createMoveRoutine(1).beginRoutine { retractKnockerAndEnd() } // Go forward, stop.
            } else {
                createMoveRoutine(-1).beginRoutine { retractKnockerAndEnd() } // Go backward, stop.
            }
        } else if (red-15 <  blue) { // If blue is in front
            turned = true
            publish("/status", TextMsg("Saw BLUE"))
            if (team == TeamColor.BLUE) {
                createMoveRoutine(1).beginRoutine { retractKnockerAndEnd() } // Go forward, stop.
            } else {
                createMoveRoutine(-1).beginRoutine { retractKnockerAndEnd() } // Go backward, stop.
            }
        } else {
            this.publish("/warn", TextMsg("Saw nuthin"))
        }
    }

    fun retractKnockerAndEnd(){
        end()
    }
}