package org.firstinspires.ftc.teamcode.old

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.old.Nodeable
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg
import org.firstinspires.ftc.teamcode.old.nodes.*
import org.firstinspires.ftc.teamcode.old.nodes.control.AngleTurningNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.hardware.*
import org.firstinspires.ftc.teamcode.old.nodes.human.DebugNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.human.TelemetryNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.GliftNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.GlyphHolderNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.OmniDtNodeOld
import org.firstinspires.ftc.teamcode.old.nodes.system.HeartbeatInvokerUnthreadedNodeOld

/**
 * Created by max on 12/8/17.
 */
abstract class CoreOp : OpMode() {
    var nodes = mutableListOf<Nodeable>()
    val systemNode = SystemNodeOld()
    val heartbeatInvokerUnthreadedNode = HeartbeatInvokerUnthreadedNodeOld()
    final override fun init() {
        nodes = mutableListOf( // common nodes
            systemNode,
            heartbeatInvokerUnthreadedNode,
            OmniDtNodeOld(),
            ImuNode(hardwareMap),
            GliftNodeOld(),
            GlyphHolderNodeOld(),
            EffectorNode(hardwareMap),
            DebugNodeOld(),
            TelemetryNodeOld(telemetry),
            AngleTurningNodeOld(),
                DigitalSensorNode(hardwareMap)
        )
        registration()
        initialize()
    }
    open fun initialize() {}
    abstract fun registration()
    open fun begin() {}

    fun register(node: Nodeable) {
        nodes.add(node)
    }
    final override fun start() {
        nodes.forEach{
            it.subscriptions()
            it.start()
        }
        systemNode.publishStart()
        begin()
        Dispatcher.publish("/cv/transition", UnitMsg())
    }

    final override fun loop() {
        heartbeatInvokerUnthreadedNode.publishBeat()
    }
    final override fun stop() {
        systemNode.publishStop()
        nodes.forEach { it.endNode() }
        Dispatcher.reset()
    }
}