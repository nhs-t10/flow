package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.UpMessage
import org.firstinspires.ftc.teamcode.messages.DownMessage
/**
 * Created by shaash on 11/9/17.
 */

class GamepadListenerNode : Node{
    constructor()

    override fun init() {
        this.subscribe("/gamepad1/dpad_up", {upPressed(it)})
        this.subscribe("/gamepad1/dpad_down", {downPressed(it)})
    }
    fun upPressed(input : Message){
        val isPressed = (input as GamepadButtonMsg).value
        this.publish("/pad1/up", UpMessage(isPressed = isPressed, priority = 1))
    }
    fun downPressed(input : Message){
        val isPressed = (input as GamepadButtonMsg).value
        this.publish("/pad1/up", DownMessage(isPressed = isPressed, priority = 1))
    }

}