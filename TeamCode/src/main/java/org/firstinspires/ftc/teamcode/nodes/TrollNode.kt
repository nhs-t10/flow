package org.firstinspires.ftc.teamcode.nodes

import kotlinx.coroutines.experimental.delay
import org.firstinspires.ftc.teamcode.*
class TrollNode(val channels: Channels) : Node() {
    var count = 0
    override fun subscriptions() {
        subscribe(channels.heartbeatChannel) {
            count++
            if (count % 100 == 0) {
                val tmp = count
                channels.debugChannel.send(DebugMsg("Hello I am #${tmp}"))
                delay(2000)
                channels.debugChannel.send(DebugMsg("Ok #${tmp} is done"))
            }
        }

        subscribe(channels.gamepad1Channel) {
            when (it) {
                is ContinuousGamepadMsg -> {
                    if (it.value.x) {
                        count+=50
                        channels.moveMotorChannel.send(MoveMotorMsg(true))
                        channels.debugChannel.send(DebugMsg("X IS BEING HELD DOWN"))

                    }
                    if (!it.value.x) {
                        channels.moveMotorChannel.send(MoveMotorMsg(false))
                    }
                }
            }
        }
    }
}