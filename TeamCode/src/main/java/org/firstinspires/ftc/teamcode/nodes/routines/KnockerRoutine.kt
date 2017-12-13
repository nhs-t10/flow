package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineGroup
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by shaash on 12/3/2017.
 */
class KnockerRoutine(val team : TeamColor) : RoutineNode (name = "Knocker Routine"){
    var turned = false
    val downPosition = 1.0
    val upPosition = 0.35
    var initialReadingR = 80
    var initialReadingB = 60
    var isDown = false
    override fun start() {
    }

    override fun subscriptions() {
        this.subscribe("/color/knocker") { doOnce(it) }
    }

    fun doOnce(m : Message){
        /*
        if(!isDown){
            val (red, blue) = m as ColorMsg
            initialReadingR = red
            initialReadingB = blue
            isDown = true
        }
        */

        if(!turned){
            knockBall(m)
        }
    }

    fun createMoveRoutine(sign: Int) : RoutineGroup = RoutineGroup(listOf(
            TimedCallbackRoutine({
                this.publish("/drive", OmniDrive(sign * 0.5f, 0f, 0f, 1))
            }, 400, {
                this.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
                retractKnocker()
            })
    ))

    fun knockBall(m : Message){
        val (red, blue) = m as ColorMsg
        if (Math.abs(initialReadingR - red) > Math.abs(initialReadingB - blue)){ // If red is in front
            turned = true
            if(team == TeamColor.RED){
                createMoveRoutine(-1).begin {  } // Go forward, stop.
//                this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, {retractKnocker()}, 1))
            } else {
                createMoveRoutine(1).begin {  } // Go backward, stop.
//                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, {retractKnocker()}, 1))
            }
        } else if (Math.abs(initialReadingR - red) < Math.abs(initialReadingB - blue)) { // If blue is in front
            turned = true
            if (team == TeamColor.BLUE) {
                createMoveRoutine(-1).begin {  } // Go forward, stop.
//                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, { retractKnocker() }, 1))
            } else {
                createMoveRoutine(1).begin {  } // Go backward, stop.
//              this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, { retractKnocker() }, 1))
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