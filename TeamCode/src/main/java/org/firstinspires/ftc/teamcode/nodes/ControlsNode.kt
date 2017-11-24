package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.GripperMsg
import org.firstinspires.ftc.teamcode.messages.GripperState
import org.firstinspires.ftc.teamcode.util.whenDown

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
    }
}