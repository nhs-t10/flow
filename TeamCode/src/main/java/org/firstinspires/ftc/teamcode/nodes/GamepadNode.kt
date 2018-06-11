package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Channels
import org.firstinspires.ftc.teamcode.ContinuousGamepadMsg
import org.firstinspires.ftc.teamcode.Message
import org.firstinspires.ftc.teamcode.Node

class GamepadNode(val channels: Channels, val gamepad1: Gamepad, val gamepad2: Gamepad) : Node() {
    override fun subscriptions() {
        subscribe(channels.heartbeatChannel, {onBeat()})
    }

    // this is an experiment to see if reflecting and listening for changes is a performance hog
    suspend fun onBeat() {
        channels.gamepad1Channel.send(ContinuousGamepadMsg(gamepad1))

        channels.gamepad2Channel.send(ContinuousGamepadMsg(gamepad2))
    }
}