package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.messages.UnitMsg
import org.firstinspires.ftc.teamcode.nodes.control.ControlsNode
import org.firstinspires.ftc.teamcode.nodes.control.OmniJoyNode
import org.firstinspires.ftc.teamcode.nodes.hardware.DogeCVNode
import org.firstinspires.ftc.teamcode.nodes.human.GamepadNode
import org.firstinspires.ftc.teamcode.nodes.human.InspectorNode
import org.firstinspires.ftc.teamcode.nodes.human.SelectorViewNode

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
        register(DogeCVNode(hardwareMap))
        Dispatcher.tempSetTelemetry(telemetry)
        Dispatcher.publish("/cv/transition", UnitMsg())
    }
}
