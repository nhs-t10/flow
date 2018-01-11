package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "KotlinOp")
class T10Teleop : CoreOp() {
    override fun registration() {
        register(GamepadNode(gamepad1, gamepad2))
        register(SelectorViewNode())
        register(InspectorNode())
        register(ControlsNode(telemetry))
        register(OmniJoyNode())
//        register(VuforiaNode(hardwareMap))
        Dispatcher.tempSetTelemetry(telemetry)
    }
}
