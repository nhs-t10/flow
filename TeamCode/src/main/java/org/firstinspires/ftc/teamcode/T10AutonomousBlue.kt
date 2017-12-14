package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.messages.ServoMsg
import org.firstinspires.ftc.teamcode.nodes.*
import org.firstinspires.ftc.teamcode.nodes.routines.KnockerRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.OpenHuggerRoutine
import org.firstinspires.ftc.teamcode.util.TeamColor
import org.firstinspires.ftc.teamcode.nodes.routines.SpinRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.TimedCallbackRoutine

/**
 * Created by shaash on 10/26/17.
 */
@Autonomous(name = "BLUE")
class T10AutonomousBlue : CoreOp(){
    var routine : RoutineGroup? = null

    val heartbeat = HeartbeatNode()

    override fun registration() {
        routine = RoutineGroup(listOf(
//                OpenHuggerRoutine(),
                TimedCallbackRoutine({
                    Dispatcher.publish("/servos/knocker", ServoMsg(0.8, 1))
                }, 1000, {cb ->
                    cb()
                }),
                KnockerRoutine(TeamColor.BLUE)
        ))
    }

    override fun begin() {
        routine?.begin {  }
    }
}