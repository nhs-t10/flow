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
//    val downPosition = 0.95
    val upPosition = 0.075
    override fun start() {}

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
                this.publish("/drive", OmniDrive(sign * 0.4f, 0f, 0f, 1))
            }, 800, {cb ->
                publish("/status", TextMsg("Drove fwd"))
                this.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
                retractKnockerAndEnd()
                cb()
            })
    ))

    fun knockBall(m : Message) {
        val (red, blue) = m as ColorMsg
        if (red - 15 > blue){ // If red is in front
            turned = true
            publish("/status", TextMsg("Saw RED"))
            if(team == TeamColor.RED){
                createMoveRoutine(-1).begin {  } // Go forward, stop.
//                this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, {retractKnocker()}, 1))
            } else {
                createMoveRoutine(1).begin {  } // Go backward, stop.
//                this.publish("/AngleTurning/turnTo", AngleTurnMsg(-30.0, {retractKnocker()}, 1))
            }
        } else if (red-15 <  blue) { // If blue is in front
            turned = true
            publish("/status", TextMsg("Saw BLUE"))
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

    fun retractKnockerAndEnd(){
        this.publish("/servos/knocker", ServoMsg(upPosition, 1))
        end()
    }
}