package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.routines.GetVumarkRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.KnockerRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.TimedCallbackRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.TimeoutRoutine
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by dvw08 on 12/15/17.
 */
abstract class T10Autonomous(val teamColor : TeamColor) : CoreOp() {
    var routine : RoutineGroup? = null

    var visionSide : RelicRecoveryVuMark = RelicRecoveryVuMark.UNKNOWN

    override fun registration() {
        routine = RoutineGroup(listOf(
                GetVumarkRoutine({vuMark ->
                    visionSide = vuMark
                }),
                TimeoutRoutine({
                    Dispatcher.publish("/glift", LiftMsg(LiftState.MIDDLE, 1))
                    Dispatcher.publish("/servos/knocker", ServoMsg(0.97, 1))
                }, 1000),
                KnockerRoutine(teamColor),
                TimedCallbackRoutine({
                    Dispatcher.publish("/drive", OmniDrive(-0.6f, 0.0f, 0.0f, 1))
                }, 1300, {cb ->
                    Dispatcher.publish("/drive", OmniDrive(0.0f, 0.0f, 0.0f, 1))
                    cb()
                })
        ))
    }

    override fun begin() {
        routine?.begin {
            Dispatcher.publish("/status", TextMsg("Autonomous Complete."))
        }
    }
}