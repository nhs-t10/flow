package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel

val capacity = 1
fun makeChannel() = BroadcastChannel<Message>(capacity)

class Channels {
    var allChannels = mutableMapOf<String, BroadcastChannel<Message>>()

    var gamepad1Channel by allChannels
    var gamepad2Channel by allChannels

    var heartbeatChannel by allChannels
    var debugChannel by allChannels

    var moveMotorChannel by allChannels

    init {
        gamepad1Channel = makeChannel()
        gamepad2Channel = makeChannel()

        heartbeatChannel = makeChannel()
        debugChannel = makeChannel()

        moveMotorChannel = makeChannel()
    }
}