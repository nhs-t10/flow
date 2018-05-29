package org.firstinspires.ftc.teamcode.old

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.old.Nodeable
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg
import org.firstinspires.ftc.teamcode.old.nodes.*
import org.firstinspires.ftc.teamcode.old.nodes.control.AngleTurningNode
import org.firstinspires.ftc.teamcode.old.nodes.hardware.*
import org.firstinspires.ftc.teamcode.old.nodes.human.DebugNode
import org.firstinspires.ftc.teamcode.old.nodes.human.TelemetryNode
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.GliftNode
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.GlyphHolderNode
import org.firstinspires.ftc.teamcode.old.nodes.mechanisms.OmniDtNode
import org.firstinspires.ftc.teamcode.old.nodes.system.HeartbeatInvokerUnthreadedNode

/**
 * Created by max on 12/8/17.
 */
abstract class CoreOp : OpMode() {
    var nodes = mutableListOf<Nodeable>()
    val systemNode = SystemNode()
    val heartbeatInvokerUnthreadedNode = HeartbeatInvokerUnthreadedNode()
    final override fun init() {
        nodes = mutableListOf( // common nodes
            systemNode,
            heartbeatInvokerUnthreadedNode,
            OmniDtNode(),
            ImuNode(hardwareMap),
            GliftNode(),
            GlyphHolderNode(),
            EffectorNode(hardwareMap),
            DebugNode(),
            TelemetryNode(telemetry),
            AngleTurningNode(),
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