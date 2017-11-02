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
    var gamepadNode : GamepadNode? = null

    var omniDtNode : OmniDtNode? = null
    var omniJoyNode : OmniJoyNode?= null
    var servoNode : ServoNode?= null
    var effectorNode : EffectorNode?= null
    var debugNode : DebugNode?=null
    override fun init() {
        heartbeat = HeartbeatNode()
        gamepadNode = GamepadNode(gamepad1, gamepad2)
        omniDtNode = OmniDtNode()
        omniJoyNode = OmniJoyNode()
        servoNode = ServoNode()
        effectorNode = EffectorNode(hardwareMap)
        debugNode = DebugNode(telemetry)
    }
    override fun loop() {
        if(heartbeat != null){
            heartbeat?.beat((runtime*10).toLong())
        }
    }
}
