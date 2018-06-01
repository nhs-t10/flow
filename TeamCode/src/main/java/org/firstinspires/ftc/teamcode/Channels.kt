package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel

class Channels {
    val gamepad1Channel = BroadcastChannel<Message>(0)
    val gamepad2Channel = BroadcastChannel<Message>(0)

    val heartbeatChannel = BroadcastChannel<Message>(0)
    val debugChannel = BroadcastChannel<Message>(0)
}