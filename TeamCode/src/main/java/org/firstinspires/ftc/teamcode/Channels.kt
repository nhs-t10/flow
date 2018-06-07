package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel

class Channels {
    val gamepad1Channel = BroadcastChannel<Message>(1)
    val gamepad2Channel = BroadcastChannel<Message>(1)

    val heartbeatChannel = BroadcastChannel<Message>(1)
    val debugChannel = BroadcastChannel<Message>(1)
}