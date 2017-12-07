package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.whenDown

/**
 * Created by max on 11/24/17.
 */

class ControlsNode : Node("Controls") {
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

    // Hugger:
    fun updateHugger(){

    }

    override fun subscriptions() {
        /**
         * Press X to do the macro thing hahahahah.
         */
        subscribe("/gamepad1/x", whenDown {
            publish("/glift/middle", UnitMsg())
            publish("/hugger", HuggerMsg(closeIt = true, onClosed = {
                publish("/glyph/lower", GripperMsg(state = GripperState.MIDDLE, priority = 2))
                publish("/glift/bottom", UnitMsg())
                publish("/glyph/upper", GripperMsg(GripperState.CLOSED, 2))
            }, priority = 2))
        })

        /**
         * Press right stick button
         */
        subscribe("/gamepad1/right_stick_button", {
            publish("/hugger/cancel", UnitMsg())
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
         * Press and hold LT when delivering blocks into the shelf. Release when done.
         */
        subscribe("/gamepad1/left_trigger", {msg ->
            val (value) = (msg as GamepadJoyOrTrigMsg)
            // If intent detected
            if (value > 0.5) updateGrippers(lower=GripperState.MIDDLE, upper=GripperState.MIDDLE)
            // If user is done and not the initial push-in
            else if(gripperStates.lower == GripperState.MIDDLE && gripperStates.upper == GripperState.MIDDLE) {
                updateGrippers(lower=GripperState.OPEN, upper=GripperState.OPEN)
            }
        })

        /**
         * TEST BUTTON 1: Turn 30º with PID
         */
        subscribe("/gamepad1/left_stick_button", whenDown {
            publish("/AngleTurning/turnTo", AngleTurnMsg(angle = 30.0, callback = {}, priority = 1))
        })

        /**
         * TEST BUTTON 2: Cancel PID Turn
         */
        subscribe("/gamepad1/right_stick_button", whenDown {
            publish("/AngleTurning/cancel", UnitMsg())
        })
    }
}
