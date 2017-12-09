package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.nodes.*
import org.firstinspires.ftc.teamcode.nodes.routines.KnockerRoutine
import org.firstinspires.ftc.teamcode.nodes.routines.OpenHuggerRoutine
import org.firstinspires.ftc.teamcode.util.TeamColor
import org.firstinspires.ftc.teamcode.nodes.routines.SpinRoutine

/**
 * Created by shaash on 10/26/17.
 */
@Autonomous(name = "RED")
class T10AutonomousRed : CoreOp(){
    var routine : RoutineGroup? = null

    val heartbeat = HeartbeatNode()

    override fun registration() {
        routine = RoutineGroup(listOf(
                KnockerRoutine(TeamColor.RED)
        ))
    }

    override fun begin() {
        routine?.begin {  }
    }
}