package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "KotlinOp")
class T10Teleop : OpMode(){
    val heartbeat = HeartbeatNode()
    var nodes : List<Node>? = null
    override fun init() {
        nodes = listOf(
                GamepadNode(gamepad1, gamepad2),
                OmniDtNode(),
                OmniJoyNode(),
                ImuNode(hardwareMap),
                DigitalSensorNode(hardwareMap),
                GliftNode(),
                GlyphHolderNode(),
                EffectorNode(hardwareMap),
                DebugNode(),
                TelemetryNode(telemetry),
                heartbeat,
                SelectorViewNode(),
                InspectorNode(),
                ControlsNode(),
                AngleTurningNode()
//                VuforiaNode(hardwareMap)
        )
    }
    override fun start() {
        nodes?.forEach{
            it.subscriptions()
        }
    }
    override fun loop() {
        heartbeat.beat((runtime*10).toLong())
    }
    override fun stop() {
        Dispatcher.reset()
    }
}
