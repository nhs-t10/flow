package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by shaash on 12/3/2017.
 */
class KnockerRoutine(val team : TeamColor = TeamColor.RED) : RoutineNode (name = "Knocker Routine"){
    var turned = false
    val downPosition = 1.0
    val upPosition = 0.3

    override fun start() {
        this.publish("/servos/knocker", ServoMsg(downPosition, 1))
    }

    override fun subscriptions() {
        this.subscribe("/colors/colorOne") { doOnce(it) }
    }

    fun doOnce(m : Message){
        if(!turned){
            knockBall(m)
        }
    }

    fun knockBall(m : Message){
        val (red, blue, green) = m as ColorMsg
        if (red > (blue+50)){
            turned = true
            if(team == TeamColor.RED){
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, {retractKnocker()}, 1))
            } else {
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, {retractKnocker()}, 1))
            }
        } else if (blue > (red+50)) {
            turned = true
            if (team == TeamColor.BLUE) {
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, { retractKnocker() }, 1))
            } else {
                this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, { retractKnocker() }, 1))
            }
        } else {
            this.publish("/warn", TextMsg("Saw nuthin"))
        }
    }

    fun retractKnocker(){
        this.publish("/servos/knocker", ServoMsg(upPosition, 1))
        end()
    }
}