package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.GripperMsg
import org.firstinspires.ftc.teamcode.messages.GripperState
import org.firstinspires.ftc.teamcode.util.whenDown
import org.firstinspires.ftc.teamcode.messages.AngleTurnMsg
import org.firstinspires.ftc.teamcode.messages.UnitMsg

/**
 * Created by max on 11/24/17.
 */

class ControlsNode : Node("Controls") {
    override fun subscriptions() {
        this.subscribe("/gamepad1/a", whenDown {
            publish("/glyph/lower", GripperMsg(GripperState.TOGGLE, 1))
        })
        this.subscribe("/gamepad1/b", whenDown {
            publish("/glyph/upper", GripperMsg(GripperState.TOGGLE, 1))
        })
        this.subscribe("/gamepad1/left_stick_button", whenDown {
            publish("/AngleTurning/turnTo", AngleTurnMsg(angle = 30.0, callback = {}, priority = 1))
        })
        this.subscribe("/gamepad1/right_stick_button", whenDown {
            publish("/AngleTurning/cancel", UnitMsg())
        })


    }
}