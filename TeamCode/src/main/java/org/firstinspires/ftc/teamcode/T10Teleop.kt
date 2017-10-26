package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by davis on 10/10/17.
 */
@TeleOp(name = "KotlinOp")
class T10Teleop : OpMode(){
    var heartbeat : Heart? = null
    var gamepadNode : GamepadNode? = null
    var omniDtNode : OmniDtNode? = null
    var omniJoyNode : OmniJoyNode?= null
    var servoNode : ServoNode?= null
    var effectorNode : EffectorNode?= null
    var debugNode : DebugNode?=null
    override fun init() {
        heartbeat = Heart()
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
