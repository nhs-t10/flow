package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.RoutineGroup
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.routines.TimedCallbackRoutine
import org.firstinspires.ftc.teamcode.util.whenDown

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

    fun updateGrippers(lower : GripperState = gripperStates.lower, upper : GripperState = gripperStates.upper) {
        gripperStates.lower = lower
        gripperStates.upper = upper
        publish("/glyph/lower", GripperMsg(lower, 1))
        publish("/glyph/upper", GripperMsg(upper, 1))
    }

    override fun subscriptions() {
        subscribe("/gamepad2/dpad_up", whenDown {
            publish("/glift/top", UnitMsg())
        })

        subscribe("/gamepad1/dpad_down", whenDown {
            publish("/glift/bottom", UnitMsg())
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
        subscribe("/gamepad1/x", whenDown {
            //            publish("/hugger", HuggerMsg(closeIt = true, onClosed = {
//                publish("/debug", TextMsg("done"))
//            }, priority = 1))
            val routine = listOf(
                    TimedCallbackRoutine({
                        publish("/glift/middle", UnitMsg()) // Move glift up...
                    }, 3000, {cb -> cb()}),
                    TimedCallbackRoutine({
                        publish("/hugger", HuggerMsg(closeIt = true, priority = 1)) //... close the hugger
                    }, 2000, {cb ->
                        updateGrippers(upper = GripperState.OPEN, lower=GripperState.OPEN) // loosen grip on block
                        cb()}),
                    TimedCallbackRoutine({
                    }, 500, {cb -> cb()}),
                    TimedCallbackRoutine({
                        publish("/glift/bottom", UnitMsg()) // hugger now has block. move lift down
                    }, 1500, {cb ->
                        updateGrippers(upper = GripperState.CLOSED) // grab block with upper grabber
                        cb()
                    }),
                    TimedCallbackRoutine({}, 1000, {cb ->
                        publish("/hugger", HuggerMsg(closeIt = false, priority = 1)) // open hugger
                        publish("/glift/higher_bottom", UnitMsg())
                        cb()
                    })// donezo!
            )
            val routineGroup = RoutineGroup(routine)
            publish("/status", TextMsg("Hugger routine STARTED"))
            routineGroup.begin {
                publish("/status", TextMsg("Hugger routine FINISHED"))
            }
        })

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
        subscribe("/gamepad1/right_trigger", {msg ->
            val (value) = (msg as GamepadJoyOrTrigMsg)
            // If intent detected
            if (value > 0.5) updateGrippers(lower=GripperState.MIDDLE, upper=GripperState.MIDDLE)
            // If user is done and not the initial push-in. Ready to collect.
            else if(gripperStates.lower == GripperState.MIDDLE && gripperStates.upper == GripperState.MIDDLE) {
                updateGrippers(lower=GripperState.OPEN, upper=GripperState.OPEN)
            }
        })

        /**
         * Press LT to grab both blocks.
         */
        subscribe("/gamepad1/left_trigger", whenDown {
            updateGrippers(lower=GripperState.CLOSED, upper = GripperState.CLOSED)
        })
        /* ------- */

        subscribe("/gamepad2/dpad_up", whenDown {
            publish("/glift/top", UnitMsg())
        })

        subscribe("/gamepad2/dpad_down", whenDown {
            publish("/glift/bottom", UnitMsg())
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
        subscribe("/gamepad2/x", whenDown {
//            publish("/hugger", HuggerMsg(closeIt = true, onClosed = {
//                publish("/debug", TextMsg("done"))
//            }, priority = 1))
            val routine = listOf(
                    TimedCallbackRoutine({
                        publish("/glift/middle", UnitMsg()) // Move glift up...
                    }, 3000, {cb -> cb()}),
                    TimedCallbackRoutine({
                        publish("/hugger", HuggerMsg(closeIt = true, priority = 1)) //... close the hugger
                    }, 2000, {cb ->
                        updateGrippers(upper = GripperState.OPEN, lower=GripperState.OPEN) // loosen grip on block
                        cb()}),
                    TimedCallbackRoutine({
                    }, 500, {cb -> cb()}),
                    TimedCallbackRoutine({
                        publish("/glift/bottom", UnitMsg()) // hugger now has block. move lift down
                    }, 1500, {cb ->
                        updateGrippers(upper = GripperState.CLOSED) // grab block with upper grabber
                        cb()
                    }),
                    TimedCallbackRoutine({}, 1000, {cb ->
                        publish("/hugger", HuggerMsg(closeIt = false, priority = 1)) // open hugger
                        publish("/glift/higher_bottom", UnitMsg())
                        cb()
                    })// donezo!
            )
            val routineGroup = RoutineGroup(routine)
            publish("/status", TextMsg("Hugger routine STARTED"))
            routineGroup.begin {
                publish("/status", TextMsg("Hugger routine FINISHED"))
            }
        })

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
        subscribe("/gamepad2/right_trigger", {msg ->
            val (value) = (msg as GamepadJoyOrTrigMsg)
            // If intent detected
            if (value > 0.5) updateGrippers(lower=GripperState.MIDDLE, upper=GripperState.MIDDLE)
            // If user is done and not the initial push-in. Ready to collect.
            else if(gripperStates.lower == GripperState.MIDDLE && gripperStates.upper == GripperState.MIDDLE) {
                updateGrippers(lower=GripperState.OPEN, upper=GripperState.OPEN)
            }
        })

        /**
         * Press LT to grab both blocks.
         */
        subscribe("/gamepad2/left_trigger", whenDown {
            updateGrippers(lower=GripperState.CLOSED, upper = GripperState.CLOSED)
        })
        /* ---- */
        /**
         * TEST BUTTON 1: Turn 30º with PID
         */
        subscribe("/gamepad1/left_stick_button", whenDown {
            publish("/glift/middle", UnitMsg())
        })

        /**
         * TEST BUTTON 2: Cancel PID Turn and hugger.
         */
        subscribe("/gamepad1/right_stick_button", whenDown {
            publish("/AngleTurning/cancel", UnitMsg())
            publish("/hugger", HuggerMsg(closeIt = false, priority = 1))
        })
    }
}
