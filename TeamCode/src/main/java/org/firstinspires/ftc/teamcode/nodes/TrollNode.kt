package org.firstinspires.ftc.teamcode.nodes

import kotlinx.coroutines.experimental.delay
import org.firstinspires.ftc.teamcode.Channels
import org.firstinspires.ftc.teamcode.ContinuousGamepadMsg
import org.firstinspires.ftc.teamcode.DebugMsg
import org.firstinspires.ftc.teamcode.Node

class TrollNode(val channels: Channels) : Node() {
    var count = 0
    override fun subscriptions() {
        subscribe(channels.heartbeatChannel) {
            count++
            if (count % 10 == 0) {
                val tmp = count
                channels.debugChannel.send(DebugMsg("Hello I am #${tmp}"))
                delay(2000)
                channels.debugChannel.send(DebugMsg("Ok #${tmp} is done"))
            }
        }

        subscribe(channels.gamepad1Channel) {
            when (it) {
                is ContinuousGamepadMsg -> {
                    channels.debugChannel.send(DebugMsg("${it}"))
                    if (it.value.x) {
                        count+=50
                        channels.debugChannel.send(DebugMsg("X IS BEING HELD DOWN"))
                    }
                }
            }
        }
    }
}