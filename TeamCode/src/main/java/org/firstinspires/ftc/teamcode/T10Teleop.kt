package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "KotlinOp")
class T10Teleop : OpMode(){
    val heartbeatNode = HeartbeatNode()
    val systemNode = SystemNode()
    var nodes : List<Node>? = null
    override fun init() {
        nodes = listOf(
                systemNode,
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
                heartbeatNode,
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
        systemNode.publishStart()
    }
    override fun loop() {
        heartbeatNode.beat((runtime*10).toLong())
    }
    override fun stop() {
        Dispatcher.reset()
    }
}
