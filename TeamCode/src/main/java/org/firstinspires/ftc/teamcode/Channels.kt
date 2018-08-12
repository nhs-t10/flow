package org.firstinspires.ftc.teamcode

import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.SubscriptionReceiveChannel

val capacity = 1
class Chan(val blocking : Boolean, val capacity : Int = 1) {
    private val channel = BroadcastChannel<Message>(capacity)
    fun openSubscription() : SubscriptionReceiveChannel<Message> {
        return channel.openSubscription()
    }
    fun getName() : String {
        return "$channel"
    }
    fun close() {
        channel.close(Throwable("Channel was closed - opmode should be stopped."))
    }
    suspend fun send(msg: Message) : Boolean {
        if (channel.isFull) {
            return false
        }
        channel.send(msg)
        return true
    }
}

class Channels {
    var allChannels = mutableMapOf<String, Chan>()

    var gamepad1Channel by allChannels
    var gamepad2Channel by allChannels

    var heartbeatChannel by allChannels
    var debugChannel by allChannels

    var moveMotorChannel by allChannels

    init {
        gamepad1Channel = Chan(false)
        gamepad2Channel = Chan(false)

        heartbeatChannel = Chan(false)
        debugChannel = Chan(false)

        moveMotorChannel = Chan(true, 5)
    }
}