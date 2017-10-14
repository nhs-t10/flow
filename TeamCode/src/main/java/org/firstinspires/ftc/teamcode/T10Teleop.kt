package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.nodes.GamepadNode
import org.firstinspires.ftc.teamcode.nodes.Heart

/**
 * Created by davis on 10/10/17.
 */
class T10Teleop : OpMode(){
    var heartbeat : Heart? = null
    var gamepadNode : GamepadNode? = null
    override fun init() {
        heartbeat = Heart()
        gamepadNode = GamepadNode(gamepad1, gamepad2)
    }
    override fun loop() {
        if(heartbeat != null){
            heartbeat?.beat((runtime*10).toLong())
        }



    }
}