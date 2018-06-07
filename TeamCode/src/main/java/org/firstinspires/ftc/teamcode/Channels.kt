package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel

class Channels {
    val capacity = 1
    val gamepad1Channel = BroadcastChannel<Message>(capacity)
    val gamepad2Channel = BroadcastChannel<Message>(capacity)

    val heartbeatChannel = BroadcastChannel<Message>(capacity)
    val debugChannel = BroadcastChannel<Message>(capacity)

    val moveMotorChannel = BroadcastChannel<Message>(capacity)
}