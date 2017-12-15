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
class T10AutonomousBlue : T10Autonomous(TeamColor.BLUE)