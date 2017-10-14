package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeat
import org.firstinspires.ftc.teamcode.messages.Message
/**
 * Created by dvw06 on 10/12/17.
 */

class GamepadNode : Node{
    var gamepadButtons = HashMap<String, Boolean>()
    var gamepadJoyTrigs = HashMap<String, Float>()
    var gamepad1 : Gamepad? = null
    var gamepad2 : Gamepad? = null

    constructor(gamepad1: Gamepad, gamepad2: Gamepad){
        Dispatcher.subscribe("/heartbeat", {click(it as HeartBeat)})
        this.gamepad1 = gamepad1
        this.gamepad2 = gamepad2
        gamepadButtons.put("dup", gamepad1.dpad_up)
        gamepadButtons.put("ddown", gamepad1.dpad_down)
        gamepadButtons.put("dleft", gamepad1.dpad_left)
        gamepadButtons.put("dright", gamepad1.dpad_right)
        gamepadButtons.put("a", gamepad1.a)
        gamepadButtons.put("b", gamepad1.b)
        gamepadButtons.put("x", gamepad1.x)
        gamepadButtons.put("y", gamepad1.y)
        gamepadButtons.put("lbump", gamepad1.left_bumper)
        gamepadButtons.put("rbump", gamepad1.right_bumper)
        //for (prop in User::class.memberProperties) {

    }

    fun click(hb: HeartBeat){
        val (time) = hb

    }
}