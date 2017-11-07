package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "KotlinOp")
class T10Teleop : OpMode(){
    var heartbeat : HeartbeatNode? = null
    override fun init() {
        heartbeat = HeartbeatNode()
        val gamepadNode = GamepadNode(gamepad1, gamepad2)
        val omniDtNode = OmniDtNode()
        val omniJoyNode = OmniJoyNode()
        val imuNode = ImuNode(hardwareMap)
        val gliftNode = GliftNode()
        val glyphHolderNode = GlyphHolderNode()
        val effectorNode = EffectorNode(hardwareMap)
        val debugNode = DebugNode(telemetry)
        val vuforiaNode = VuforiaNode(hardwareMap)
    }
    override fun loop() {
        if(heartbeat != null){
            heartbeat?.beat((runtime*10).toLong())
        }
    }
}
