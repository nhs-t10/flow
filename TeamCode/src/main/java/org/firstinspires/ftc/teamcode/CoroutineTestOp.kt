package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import kotlinx.coroutines.experimental.runBlocking
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode

/**
 * Created by max on 5/29/18.
 */
@TeleOp(name = "Coroutine Test Op")
class CoroutineTestOp : OpMode() {
    val heartbeatNode : HeartbeatNode? = null
    override fun init() {
//        val nodes = listOf()
    }

    override fun loop() = runBlocking<Unit> {
        heartbeatNode?.sendBeat()
    }
}