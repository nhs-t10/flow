package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.RoutineGroup
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.routines.TimedCallbackRoutine
import org.firstinspires.ftc.teamcode.util.whenDown
import javax.tools.ToolProvider

/**
 * Created by max on 11/24/17.
 */

class ControlsNode(val telemetry: Telemetry) : Node("Controls") {
    object gripperStates {
        var lower = GripperState.OPEN
        var upper = GripperState.OPEN
    }


    // Finite State Machine for Grippers
    fun gripperTransition(prevState: GripperState) = when(prevState) {
        GripperState.OPEN -> GripperState.CLOSED
        GripperState.CLOSED -> GripperState.OPEN
        GripperState.MIDDLE -> GripperState.OPEN
    }

    var liftState = LiftState.BOTTOM

    fun liftTransition(prevState: LiftState, indice : Int) = when(prevState) {
        LiftState.TOP -> if (indice > 0) LiftState.TOP else LiftState.MIDDLE
        LiftState.MIDDLE -> if (indice > 0) LiftState.TOP else LiftState.BOTTOM
        LiftState.UPPER_BOTTOM -> if (indice > 0) LiftState.MIDDLE else LiftState.BOTTOM
        LiftState.BOTTOM -> if (indice > 0) LiftState.MIDDLE else LiftState.BOTTOM
    }

    fun updateLift(state: LiftState) {
        liftState = state
        publish("/glift", LiftMsg(liftState, 1))
    }

    fun updateGrippers(lower : GripperState = gripperStates.lower, upper : GripperState = gripperStates.upper) {
        gripperStates.lower = lower
        gripperStates.upper = upper
        publish("/glyph/lower", GripperMsg(lower, 1))
        publish("/glyph/upper", GripperMsg(upper, 1))
    }

    val macroLambda = whenDown {
        val routine = listOf(
                TimedCallbackRoutine({
                    if (gripperStates.lower != GripperState.CLOSED) {
                        updateGrippers(lower = GripperState.CLOSED)
                    }
                }, if (gripperStates.lower != GripperState.CLOSED) 500 else 0, {cb -> cb()}),
                TimedCallbackRoutine({
                    updateLift(LiftState.MIDDLE) // move glift up..
                }, 3300, {cb -> cb()}),
                TimedCallbackRoutine({
                    publish("/hugger", HuggerMsg(closeIt = true, priority = 1)) //... close the hugger
                }, 2000, {cb ->
                    updateGrippers(upper = GripperState.OPEN, lower=GripperState.OPEN) // loosen grip on block
                    cb()}),
                TimedCallbackRoutine({
                }, 500, {cb -> cb()}),
                TimedCallbackRoutine({
                    updateLift(LiftState.BOTTOM) // hugger now has block. move lift down
                }, 1500, {cb ->
                    updateGrippers(upper = GripperState.CLOSED) // grab block with upper grabber
                    cb()
                }),
                TimedCallbackRoutine({}, 1000, {cb ->
                    publish("/hugger", HuggerMsg(closeIt = false, priority = 1)) // open hugger
                    updateLift(LiftState.UPPER_BOTTOM)
                    cb()
                })// donezo!
        )
        val routineGroup = RoutineGroup(routine)
        publish("/status", TextMsg("Hugger routine STARTED"))
        routineGroup.begin {
            publish("/status", TextMsg("Hugger routine FINISHED"))
        }
    }

    val squeezeReleaseLambda = {msg:Message ->
        val (value) = (msg as GamepadJoyOrTrigMsg)
        // If intent detected
        if (value > 0.5) updateGrippers(lower=GripperState.MIDDLE, upper=GripperState.MIDDLE)
        // If user is done and not the initial push-in. Ready to collect.
        else if(gripperStates.lower == GripperState.MIDDLE && gripperStates.upper == GripperState.MIDDLE) {
            updateGrippers(lower=GripperState.OPEN, upper=GripperState.OPEN)
        }
    }

    val cancelLambda = whenDown {
        publish("/macros/cancel", UnitMsg())
        publish("/AngleTurning/cancel", UnitMsg())
        publish("/servos/knocker", ServoMsg(0.08, 0))
        publish("/hugger", HuggerMsg(closeIt = false, priority = 0))
    }

    override fun subscriptions() {

        subscribe("/gamepad1/dpad_up", whenDown {
            updateLift(liftTransition(liftState, 1))
        })

        subscribe("/gamepad1/dpad_down", whenDown {
            updateLift(liftTransition(liftState, -1))
        })
        subscribe("/gamepad1/left_bumper", whenDown {
            publish("/glift/increment_up", UnitMsg())
        })
        subscribe("/gamepad1/right_bumper", whenDown {
            publish("/glift/increment_down", UnitMsg())
        })
        /**
         * Press X to do the hugger macro.
         */
        subscribe("/gamepad1/x", macroLambda)

        /**
         * Press A to toggle grabbing or ejecting a lower block.
         */
        subscribe("/gamepad1/a", whenDown {
            updateGrippers(lower = gripperTransition(gripperStates.lower))
        })

        /**
         * Press B to toggle grabbing or ejecting an upper block.
         */
        subscribe("/gamepad1/b", whenDown {
            updateGrippers(upper = gripperTransition(gripperStates.upper))
        })

        /**
         * Press and hold RT when delivering blocks into the shelf. Release when done.
         */
        subscribe("/gamepad1/right_trigger", squeezeReleaseLambda)

        /**
         * Press LT to grab both blocks.
         */
        subscribe("/gamepad1/left_trigger", whenDown {
            updateGrippers(lower=GripperState.CLOSED, upper = GripperState.CLOSED)
        })
        /* ------- */

        subscribe("/gamepad2/dpad_up", whenDown {
            updateLift(liftTransition(liftState, 1))
        })

        subscribe("/gamepad2/dpad_down", whenDown {
            updateLift(liftTransition(liftState, -1))
        })
        subscribe("/gamepad2/left_bumper", whenDown {
            publish("/glift/increment_up", UnitMsg())
        })
        subscribe("/gamepad2/right_bumper", whenDown {
            publish("/glift/increment_down", UnitMsg())
        })
        /**
         * Press X to do the hugger macro.
         */
        subscribe("/gamepad2/x", macroLambda)

        /**
         * Press A to toggle grabbing or ejecting a lower block.
         */
        subscribe("/gamepad2/a", whenDown {
            updateGrippers(lower = gripperTransition(gripperStates.lower))
        })

        /**
         * Press B to toggle grabbing or ejecting an upper block.
         */
        subscribe("/gamepad2/b", whenDown {
            updateGrippers(upper = gripperTransition(gripperStates.upper))
        })

        /**
         * Press and hold RT when delivering blocks into the shelf. Release when done.
         */
        subscribe("/gamepad2/right_trigger", squeezeReleaseLambda)

        /**
         * Press LT to grab both blocks.
         */
        subscribe("/gamepad2/left_trigger", whenDown {
            updateGrippers(lower=GripperState.CLOSED, upper = GripperState.CLOSED)
        })
        /* ---- */
        /**
         * Middle it out
         */
        subscribe("/gamepad1/left_stick_button", whenDown {
            updateLift(LiftState.UPPER_BOTTOM)
        })

        subscribe("/gamepad2/left_stick_button", whenDown {
            updateLift(LiftState.UPPER_BOTTOM)
        })

        /**
         * TEST BUTTON 2: Cancel PID Turn and hugger.
         */
        subscribe("/gamepad1/right_stick_button", cancelLambda)
    }
}
