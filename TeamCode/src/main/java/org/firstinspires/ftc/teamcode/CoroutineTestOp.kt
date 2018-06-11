package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by max on 5/29/18.
 */
@TeleOp(name = "Coroutine Test Op")
class CoroutineTestOp : OpMode() {
    val channels = Channels()
    var heartbeatNode : HeartbeatNode? = null

    override fun init() = runBlocking {

        heartbeatNode = HeartbeatNode(channels)

        val nodes = listOf(
            heartbeatNode,
            TelemetryNode(channels, telemetry),
            GamepadNode(channels, gamepad1, gamepad2),
            TrollNode(channels),
            MotorNode(channels, hardwareMap.dcMotor.get("m1")),
            InspectorNode(channels)
        )


        nodes.forEach {
            launch {
                it?.subscriptions()
            }
        }
    }

    override fun loop() = runBlocking<Unit> {
        heartbeatNode?.sendBeat()
    }

    override fun stop() {
        for (channel in channels.allChannels.values) {
            channel.close()
        }
    }
}