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
    override fun init() {
        val nodes = arrayOf(
                GamepadNode(gamepad1, gamepad2),
                OmniDtNode(),
                OmniJoyNode(),
                ImuNode(hardwareMap),
                GliftNode(),
                GlyphHolderNode(),
                EffectorNode(hardwareMap),
                DebugNode(),
                TelemetryNode(telemetry),
                heartbeat,
                SelectorView(),
                InspectorNode()
//                VuforiaNode(hardwareMap)
        )
        for (node in nodes) node.subscriptions()
    }
    override fun loop() {
        if(heartbeat != null){
            heartbeat?.beat((runtime*10).toLong())
        }
    }
    override fun stop() {
        Dispatcher.reset()
    }
}
