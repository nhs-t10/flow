package org.firstinspires.ftc.teamcode.old

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.old.nodes.control.ControlsNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.control.DriveStraightNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.control.OmniJoyNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.ColorNode
import org.firstinspires.ftc.teamcode.old.nodes.human.GamepadNode
import org.firstinspires.ftc.teamcode.old.nodes.human.InspectorNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.human.SelectorViewNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.RainbowNodeOld

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "KotlinOp")
class T10Teleop : CoreOp() {
    override fun registration() {
        register(GamepadNode(gamepad1, gamepad2))
        register(SelectorViewNodeOld())
        register(InspectorNodeOld())
        register(ControlsNodeOld(telemetry))
        register(OmniJoyNode())
        Dispatcher.tempSetTelemetry(telemetry)
        register(RainbowNodeOld())
        register(ColorNode(hardwareMap))
        register(DriveStraightNodeOld())
    }
}
