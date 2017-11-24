package org.firstinspires.ftc.teamcode.nodes

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.messages.IncrementState

class GliftNode : Node("Glyph Lift") {
    override fun subscriptions() {
        this.subscribe("/gamepad1/dpad_up", org.firstinspires.ftc.teamcode.util.whenDown { this.receiveUpMessage() })
        this.subscribe("/gamepad1/dpad_down", org.firstinspires.ftc.teamcode.util.whenDown { this.receiveDownMessage() })
        this.subscribe("/gamepad1/left_bumper", org.firstinspires.ftc.teamcode.util.whenDown { this.incrementUp() })
        this.subscribe("/gamepad1/right_bumper", org.firstinspires.ftc.teamcode.util.whenDown { this.incrementDown() })
    }

    /**
     * Safety measure so lower gripper doesn't get caught on rails
     */
    fun safetyClose() {
        this.publish("/glyph/lower", GripperMsg(GripperState.CLOSED, 2))
    }

    fun receiveUpMessage() {
        this.publish("/crServos/liftServo", MotorMsg((-0.8), priority = 1))
    }
    fun receiveDownMessage() {
        this.publish("/crServos/liftServo", MotorMsg((0.1), priority = 1))
        safetyClose()
    }
    fun incrementUp() {
        this.publish("/crServos/liftServo", IncrementMsg(IncrementState.INCREMENT, -0.1))
    }
    fun incrementDown() {
        this.publish("/crServos/liftServo", IncrementMsg(IncrementState.INCREMENT, 0.1))
        safetyClose()
    }
}
