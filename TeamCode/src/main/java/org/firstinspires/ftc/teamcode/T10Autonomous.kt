package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.control.AngleTurningNode
import org.firstinspires.ftc.teamcode.nodes.control.DriveStraightNode
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

//        register(VuforiaNode(hardwareMap))
//        register(DigitalSensorNode(hardwareMap))
        register(AnalogSensorNode(hardwareMap))
        register(ColorNode(hardwareMap))
        register(DriveStraightNode())
        //register(DogeCVNode(hardwareMap))
        val routineList = listOf(
                TimeoutRoutine({
                    Dispatcher.publish("/servos/knocker_yaw", ServoMsg(0.575, 1))
                }, 1000),
                GetVumarkRoutine({vuMark ->
                    robotState.vuMark = vuMark
                }),
                TimeoutRoutine({
                    Dispatcher.publish("/glift", LiftMsg(LiftState.UPPER_BOTTOM, 1))
                    Dispatcher.publish("/servos/knocker_pitch", ServoMsg(0.65, 1))
                }, 1200),
                TimeoutRoutine({ // this makes it do each thing in parts
                    Dispatcher.publish("/servos/knocker_pitch", ServoMsg(1.0, 1))
                }, 500),
                KnockerRoutine(teamColor, teamPosition),
//                (if (teamPosition == TeamPosition.ONE) DriveToCryptoboxRoutine(teamColor) else TimeoutRoutine({
//                    Dispatcher.publish("/status", TextMsg("Doing nothing"))
//                }, 200)),
                TimeoutRoutine({
                    val upPosition = 0.15
                    Dispatcher.publish("/servos/knocker_pitch", ServoMsg(upPosition, 1))
                }, 1000),
                (if (teamPosition == TeamPosition.ONE) TimedCallbackRoutine({
                    val sign = if (teamColor == TeamColor.BLUE) 1 else -1
                    Dispatcher.publish("/drive", OmniDrive(sign * 0.6f, 0.0f, 0.0f, 1)) // REMEMBER: if ur using this for red, check signs
                }, 1300, {cb ->
                    Dispatcher.publish("/drive", OmniDrive(0.0f, 0.0f, 0.0f, 1))
                    cb()
                }) else TimeoutRoutine({
                    Dispatcher.publish("/status", TextMsg("Doing nothing x2"))
                }, 200))
//                TimedCallbackRoutine({
//                    Dispatcher.publish("/drive", OmniDrive(0f, 0.2f, 0f, 1))
//                }, 1300, {cb ->
//                    Dispatcher.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
//                    cb()
//                }),
//                SmashIntoWall(),
//                SpinRoutine(-90.0),
//                CountFlangesRoutine(vumark = robotState.vuMark),
//                TimedCallbackRoutine({
//                    Dispatcher.publish("/glyph/lower", GripperMsg(GripperState.OPEN, 1))
//                    Dispatcher.publish("/drive", OmniDrive(0.3f, 0f, 0f, 1))
//                }, 400, {cb ->
//                    Dispatcher.publish("/drive", OmniDrive(0f, 0f, 0f, 1))
//                    cb()
//                })
        )
        routine = RoutineGroup(routineList)
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
