package org.firstinspires.ftc.teamcode.nodes.control

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.RoutineGroup
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.nodes.routines.TimedCallbackRoutine
import org.firstinspires.ftc.teamcode.util.whenDown
import org.w3c.dom.Text

/**
 * Created by max on 11/24/17.
 */

class ControlsNode(val telemetry: Telemetry) : Node("Controls") {
    object gripperStates {
        var lower = GripperState.OPEN
        var upper = GripperState.OPEN
    }

    var rainbowGripperState = GripperState.CLOSED
    fun rainbowGripperTransition(prevState: GripperState) = when(prevState){
        GripperState.OPEN -> GripperState.CLOSED
        GripperState.CLOSED -> GripperState.OPEN
        GripperState.MIDDLE -> GripperState.OPEN
    }

    // Finite State Machine for Grippers
    fun gripperTransition(prevState: GripperState) = when(prevState) {
        GripperState.OPEN -> GripperState.CLOSED
        GripperState.CLOSED -> GripperState.OPEN
        GripperState.MIDDLE -> GripperState.OPEN
    }

    var liftState = LiftState.BOTTOM

    // Finite State Machine for Lift
    fun liftTransition(prevState: LiftState, indice : Int) = when(prevState) {
        LiftState.TOP -> if (indice > 0) LiftState.TOP else LiftState.MIDDLE
        LiftState.MIDDLE -> if (indice > 0) LiftState.TOP else LiftState.UPPER_BOTTOM
        LiftState.UPPER_BOTTOM -> if (indice > 0) LiftState.MIDDLE else LiftState.BOTTOM
        LiftState.BOTTOM -> if (indice > 0) LiftState.UPPER_BOTTOM else LiftState.BOTTOM
    }

    fun updateLift(state: LiftState) {
        liftState = state
        publish("/status", TextMsg("Lift moved to $liftState"))
        publish("/glift", LiftMsg(liftState, 1))
    }

    fun updateGrippers(lower : GripperState = gripperStates.lower, upper : GripperState = gripperStates.upper) {
        gripperStates.lower = lower
        gripperStates.upper = upper
        publish("/status", TextMsg("Lower gripper: $lower, Upper gripper: $upper"))
        publish("/glyph/lower", GripperMsg(lower, 1))
        publish("/glyph/upper", GripperMsg(upper, 1))
    }

    fun updateRainbowGrippers(liftStatus : GripperState){
        rainbowGripperState = liftStatus
        publish("/rainbow/gripper", GripperMsg(liftStatus, 1))
    }

    val squeezeReleaseLambda = whenDown { updateGrippers(lower=GripperState.OPEN, upper=GripperState.OPEN) }

    val cancelLambda = whenDown {
        publish("/macros/cancel", UnitMsg())
        publish("/AngleTurning/cancel", UnitMsg())
//        publish("/servos/knocker", ServoMsg(0.08, 0))
        publish("/hugger", HuggerMsg(closeIt = false, priority = 0))
    }



    override fun subscriptions() {

        subscribe("/gamepad1/left_bumper", {msg ->
//          updateLift(liftTransition(liftState, -1))
            if ((msg as GamepadButtonMsg).value) publish("/motors/glift", MotorMsg(0.5, 1))
            else publish("/motors/glift", MotorMsg(0.0, 1))
        })

        subscribe("/gamepad1/right_bumper",  {msg ->
//          updateLift(liftTransition(liftState, 1))
            if ((msg as GamepadButtonMsg).value) publish("/motors/glift", MotorMsg(-0.5, 1))
            else publish("/motors/glift", MotorMsg(0.0, 1))
        })
        subscribe("/gamepad1/dpad_up", whenDown {
            publish("/glift/increment_up", UnitMsg())
        })
        subscribe("/gamepad1/dpad_down", whenDown {
            publish("/glift/increment_down", UnitMsg())
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
        subscribe("/gamepad1/right_trigger", squeezeReleaseLambda)

        /**
         * Press LT to grab both blocks.
         */
        subscribe("/gamepad1/left_trigger", whenDown {
            updateGrippers(lower=GripperState.CLOSED, upper = GripperState.CLOSED)
        })
        /* ------- */

        subscribe("/gamepad2/dpad_up", {publish("/rainbow/macroExtend", UnitMsg())})

        subscribe("/gamepad2/dpad_down", {publish("/rainbow/macroRetract", UnitMsg())})

        subscribe("/gamepad2/dpad_left", whenDown {
            publish("/rainbow/tilter/increment_up/fast", UnitMsg())
        })
        subscribe("/gamepad2/dpad_right", whenDown {
            publish("/rainbow/tilter/increment_down/fast", UnitMsg())
        })
        subscribe("/gamepad2/left_stick_button", whenDown {
            publish("/rainbow/tilter/increment_up/fast", UnitMsg())
        })
        subscribe("/gamepad2/right_stick_button", whenDown {
            publish("/rainbow/tilter/increment_down/fast", UnitMsg())
        })
        subscribe("/gamepad2/a", whenDown {
            publish("/rainbow/tilter/eject", UnitMsg())
        })
        subscribe("/gamepad2/y", whenDown {
            publish("/rainbow/tilter/over_wall", UnitMsg())
        })
        subscribe("/gamepad2/left_bumper", whenDown {
            publish("/rainbow/tilter/increment_up", UnitMsg())
        })
        subscribe("/gamepad2/right_bumper", whenDown {
            publish("/rainbow/tilter/increment_down", UnitMsg())
        })
        subscribe("/gamepad1/left_bumper", whenDown {
            publish("/glift/goDown", MotorMsg(power = 1.0, priority = 1))
        })
        subscribe("/gamepad1/right_bumper", whenDown {
            publish("/glift/goUp", MotorMsg(power = -1.0, priority = 1))
        })

        /**
         * Press B to rainbow grab
         */

        subscribe("/gamepad2/b", whenDown {
            updateRainbowGrippers(rainbowGripperTransition(rainbowGripperState))
        })

        /**
         * Press and hold RT when delivering blocks into the shelf. Release when done.
         */

        subscribe("/gamepad2/right_trigger", {publish("/rainbow/extender/extend", it)})

        /**
         * Press LT to grab both blocks.
         */

        subscribe("/gamepad2/left_trigger", {publish("/rainbow/extender/retract", it)})
        /* ---- */
        /**
         * Middle it out
         */
        subscribe("/gamepad1/right_stick_button", whenDown {
            publish("/servos/jamb", IncrementMsg(IncrementState.INCREMENT, 0.05))
        })

        subscribe("/gamepad2/right_stick_button", whenDown {
            updateLift(LiftState.UPPER_BOTTOM)
        })

        subscribe("/gamepad1/right_stick_button", cancelLambda)
        subscribe("/gamepad2/right_stick_button", cancelLambda)

        subscribe("/gamepad1/left_stick_button", {msg ->
            val m = msg as GamepadButtonMsg
//            this.publish("/drive/fast", SpeedMsg(m.value, 1))
            publish("/servos/jamb", IncrementMsg(IncrementState.INCREMENT, -0.05))
//            this.publish("/AngleTurning/turnTo", AngleTurnMsg(30.0, {}, 1))
        })
    }
}
