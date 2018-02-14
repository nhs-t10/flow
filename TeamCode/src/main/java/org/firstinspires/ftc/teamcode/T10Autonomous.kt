package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.control.AngleTurningNode
import org.firstinspires.ftc.teamcode.nodes.hardware.*
import org.firstinspires.ftc.teamcode.nodes.human.UIColorNode
import org.firstinspires.ftc.teamcode.nodes.routines.*
import org.firstinspires.ftc.teamcode.util.TeamColor
import org.firstinspires.ftc.teamcode.util.TeamPosition

/**
 * Created by dvw08 on 12/15/17.
 */
abstract class T10Autonomous(val teamColor : TeamColor, val teamPosition: TeamPosition) : CoreOp() {
    var routine : RoutineGroup? = null

    // This allows us to inject the robot state into routines
    // Routines accept an arg with type () -> RobotState
    // and call that function (getRobotState) to get the latest RobotState
    val robotState = RobotState()
    val getRobotState = {robotState}

    override fun registration() {
        // Do the safety dance
        val uiColorNode = UIColorNode(hardwareMap)
        if (teamColor == TeamColor.RED) uiColorNode.changeColor("red")
        else if (teamColor == TeamColor.BLUE) uiColorNode.changeColor("blue")
        register(uiColorNode)

        register(VuforiaNode(hardwareMap))
        register(DigitalSensorNode(hardwareMap))
        register(AnalogSensorNode(hardwareMap))
        register(ColorNode(hardwareMap))
        //register(DogeCVNode(hardwareMap))
        routine = RoutineGroup(listOf(
            TimeoutRoutine({
                Dispatcher.publish("/glyph/upper", GripperMsg(GripperState.CLOSED, 1))
            }, 1000),
            GetVumarkRoutine({vuMark ->
                robotState.vuMark = vuMark
            })
            ,
            TimeoutRoutine({
                Dispatcher.publish("/glift", LiftMsg(LiftState.UPPER_BOTTOM, 1))
                Dispatcher.publish("/servos/knocker", ServoMsg(0.875, 1))
            }, 2000),
            KnockerRoutine(teamColor, teamPosition),
            TimeoutRoutine({}, 1000), // wait for knocker retraction
            GetCloserToWall(),
            StopAtCryptoboxRoutine(vumark = robotState.vuMark),
            SpinRoutine(-90.0),
            TimedCallbackRoutine({
                Dispatcher.publish("/glyph/lower", GripperMsg(GripperState.OPEN, 1))
                Dispatcher.publish("/drive", OmniDrive(0.3f, 0f, 0f, 1))
            }, 400, {cb ->
                Dispatcher.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
                cb()
            })
        ))
    }

    override fun initialize() {
        telemetry.addLine("$teamColor $teamPosition $teamColor $teamPosition $teamColor $teamPosition")
        telemetry.addLine("YOU SELECTED $teamColor POSITION $teamPosition")
        telemetry.addLine("$teamColor $teamPosition $teamColor $teamPosition $teamColor $teamPosition")
    }

    override fun begin() {
        routine?.beginRoutine {
            Dispatcher.publish("/status", TextMsg("Autonomous Complete."))
        }
    }
}
