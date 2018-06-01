package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.experimental.runBlocking
import org.firstinspires.ftc.teamcode.nodes.GamepadNode
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode
import org.firstinspires.ftc.teamcode.nodes.TelemetryNode
import org.firstinspires.ftc.teamcode.nodes.TrollNode

/**
 * Created by max on 5/29/18.
 */
@TeleOp(name = "Coroutine Test Op")
class CoroutineTestOp : OpMode() {
    var heartbeatNode : HeartbeatNode? = null
    override fun init() = runBlocking {
        val channels = Channels()

        heartbeatNode = HeartbeatNode(channels)

        val nodes = listOf(
            heartbeatNode,
            TelemetryNode(channels, telemetry),
            GamepadNode(channels, gamepad1, gamepad2),
            TrollNode(channels)
        )


        nodes.forEach {
            it?.subscriptions()
        }
    }

    override fun loop() = runBlocking<Unit> {
        heartbeatNode?.sendBeat()
    }
}