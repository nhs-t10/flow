package org.firstinspires.ftc.teamcode.old

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.old.nodes.hardware.AnalogSensorNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.DigitalSensorNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.VuforiaNode
import org.firstinspires.ftc.teamcode.old.nodes.human.GamepadNode
import org.firstinspires.ftc.teamcode.old.nodes.human.InspectorNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.human.SelectorViewNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.human.TestingNodeOld

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "Sensor Test Op")
class T10TestOp : CoreOp() {
    override fun registration() {
        register(GamepadNode(gamepad1, gamepad2))
        register(VuforiaNode(hardwareMap))
        register(DigitalSensorNode(hardwareMap))
        register(AnalogSensorNode(hardwareMap))
        register(SelectorViewNodeOld())
        register(InspectorNodeOld())
        register(TestingNodeOld())
        Dispatcher.tempSetTelemetry(telemetry)
    }
}
