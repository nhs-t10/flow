package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.control.AngleTurningNode
import org.firstinspires.ftc.teamcode.nodes.hardware.*
import org.firstinspires.ftc.teamcode.nodes.human.UIColorNode
import org.firstinspires.ftc.teamcode.nodes.routines.*
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by dvw08 on 12/15/17.
 */
abstract class T10Autonomous(val teamColor : TeamColor) : CoreOp() {
    var routine : RoutineGroup? = null

    // This allows us to inject the robot state into routines
    // Routines accept an arg with type () -> RobotState
    // and call that function (getRobotState) to get the latest RobotState
    val robotState = RobotState()
    val getRobotState = {robotState}

    override fun registration() {
        // Do the safety dance
        val colorNode = UIColorNode(hardwareMap)
        if (teamColor == TeamColor.RED) colorNode.changeColor("red")
        else if (teamColor == TeamColor.BLUE) colorNode.changeColor("blue")

        register(VuforiaNode(hardwareMap))
        register(colorNode)
        register(DistanceColorNode(hardwareMap))
        register(DigitalSensorNode(hardwareMap))
        register(AnalogSensorNode(hardwareMap))
        register(UIColorNode(hardwareMap))



        routine = RoutineGroup(listOf(
                GetVumarkRoutine({vuMark ->
                    robotState.vuMark = vuMark
                }),
                TimeoutRoutine({
                    Dispatcher.publish("/glift", LiftMsg(LiftState.MIDDLE, 1))
                    Dispatcher.publish("/servos/knocker", ServoMsg(0.97, 1))
                }, 1000),
                KnockerRoutine(teamColor),
                DriveToCryptoboxRoutine()
        ))
    }

    override fun begin() {
        routine?.begin {
            Dispatcher.publish("/status", TextMsg("Autonomous Complete."))
        }
    }
}
