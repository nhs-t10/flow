package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.messages.UnitMsg
import org.firstinspires.ftc.teamcode.nodes.*
import org.firstinspires.ftc.teamcode.nodes.control.AngleTurningNode
import org.firstinspires.ftc.teamcode.nodes.hardware.*
import org.firstinspires.ftc.teamcode.nodes.human.DebugNode
import org.firstinspires.ftc.teamcode.nodes.human.TelemetryNode
import org.firstinspires.ftc.teamcode.nodes.mechanisms.GliftNode
import org.firstinspires.ftc.teamcode.nodes.mechanisms.GlyphHolderNode
import org.firstinspires.ftc.teamcode.nodes.mechanisms.OmniDtNode

/**
 * Created by max on 12/8/17.
 */
abstract class CoreOp : OpMode() {
    var nodes = mutableListOf<Node>()
    val heartbeatNode = HeartbeatNode()
    val systemNode = SystemNode()
    final override fun init() {
        nodes = mutableListOf( // common nodes
                systemNode,
                OmniDtNode(),
                ImuNode(hardwareMap),
                GliftNode(),
                GlyphHolderNode(),
                EffectorNode(hardwareMap),
                DebugNode(),
                TelemetryNode(telemetry),
                heartbeatNode,
                AngleTurningNode()
        )
        registration()
        initialize()
    }
    open fun initialize() {}
    abstract fun registration()
    open fun begin() {}

    fun register(node: Node) {
        nodes.add(node)
    }
    final override fun start() {
        nodes?.forEach{
            it.subscriptions()
        }
        systemNode.publishStart()
        begin()
        Dispatcher.publish("/cv/transition", UnitMsg())
    }

    final override fun loop() {
        heartbeatNode.beat((runtime*10).toLong())
    }
    final override fun stop() {
        systemNode.publishStop()
        Dispatcher.reset()
    }
}