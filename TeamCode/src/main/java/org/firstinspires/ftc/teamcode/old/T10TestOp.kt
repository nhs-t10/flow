package org.firstinspires.ftc.teamcode.old

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.old.nodes.control.ControlsNode
import org.firstinspires.ftc.teamcode.old.nodes.control.OmniJoyNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.AnalogSensorNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.DigitalSensorNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.DogeCVNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.VuforiaNode
import org.firstinspires.ftc.teamcode.old.nodes.human.GamepadNode
import org.firstinspires.ftc.teamcode.old.nodes.human.InspectorNode
import org.firstinspires.ftc.teamcode.old.nodes.human.SelectorViewNode
import org.firstinspires.ftc.teamcode.old.nodes.human.TestingNode

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
        register(AnalogSensorNode(hardwareMap))
        register(SelectorViewNode())
        register(InspectorNode())
        register(TestingNode())
        Dispatcher.tempSetTelemetry(telemetry)
    }
}
