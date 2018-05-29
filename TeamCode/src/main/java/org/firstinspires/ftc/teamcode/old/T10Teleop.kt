package org.firstinspires.ftc.teamcode.old

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg
import org.firstinspires.ftc.teamcode.old.nodes.control.ControlsNode
import org.firstinspires.ftc.teamcode.old.nodes.control.DriveStraightNode
import org.firstinspires.ftc.teamcode.old.nodes.control.OmniJoyNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.ColorNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.DogeCVNode
import org.firstinspires.ftc.teamcode.old.nodes.human.GamepadNode
import org.firstinspires.ftc.teamcode.old.nodes.human.InspectorNode
import org.firstinspires.ftc.teamcode.old.nodes.human.SelectorViewNode
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.RainbowNode

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
        Dispatcher.tempSetTelemetry(telemetry)
        register(RainbowNode())
        register(ColorNode(hardwareMap))
        register(DriveStraightNode())
    }
}
