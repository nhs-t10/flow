package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.nodes.control.ControlsNode
import org.firstinspires.ftc.teamcode.nodes.control.OmniJoyNode
import org.firstinspires.ftc.teamcode.nodes.hardware.DigitalSensorNode
import org.firstinspires.ftc.teamcode.nodes.hardware.DogeCVNode
import org.firstinspires.ftc.teamcode.nodes.hardware.VuforiaNode
import org.firstinspires.ftc.teamcode.nodes.human.GamepadNode
import org.firstinspires.ftc.teamcode.nodes.human.InspectorNode
import org.firstinspires.ftc.teamcode.nodes.human.SelectorViewNode
import org.firstinspires.ftc.teamcode.nodes.human.TestingNode

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "Sensor Test Op")
class T10TestOp : CoreOp() {
    override fun registration() {
        register(GamepadNode(gamepad1, gamepad2))
        register(VuforiaNode(hardwareMap))
        register(DogeCVNode(hardwareMap))
        register(DigitalSensorNode(hardwareMap))
        register(SelectorViewNode())
        register(InspectorNode())
        register(TestingNode())
        Dispatcher.tempSetTelemetry(telemetry)
    }
}
