package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.routines.KnockerRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.TimedCallbackRoutine
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by dvw08 on 12/15/17.
 */
abstract class T10Autonomous(val teamColor : TeamColor) : CoreOp() {
    var routine : RoutineGroup? = null
    override fun registration() {
        routine = RoutineGroup(listOf(
                //                OpenHuggerRoutine(),
                TimedCallbackRoutine({
                    Dispatcher.publish("/glift", LiftMsg(LiftState.MIDDLE, 1))
                    Dispatcher.publish("/servos/knocker", ServoMsg(0.97, 1))
                }, 1000, {cb ->
                    cb()
                }),
                KnockerRoutine(teamColor),
                TimedCallbackRoutine({
                    Dispatcher.publish("/drive", OmniDrive(-0.6f, 0.0f, 0.0f, 1))
                }, 1300, {
                    Dispatcher.publish("/drive", OmniDrive(0.0f, 0.0f, 0.0f, 1))
                })
        ))
    }

    override fun begin() {
        routine?.begin {
            Dispatcher.publish("/status", TextMsg("Autonomous Complete."))
        }
    }
}