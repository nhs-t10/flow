package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.*

class TelemetryNode (val channels: Channels, val telemetry: Telemetry) : Node() {
    override fun subscriptions() {
        // this and telemetry.update() make telemetry work with threads
        telemetry.setAutoClear(false)

        subscribe(channels.debugChannel, {onMsg(it)})
    }

    private fun addLine(text: String) {
        telemetry.addLine(text)
        telemetry.update() // I think? you need this if it's threaded
    }
    private fun clear() {
        telemetry.clear()
        telemetry.update()
    }

    private fun onMsg(msg:Message) : Unit = when(msg) {
        is ClearMsg -> clear()
        is DebugMsg -> addLine("[DEBUG]: ${msg.value}")
        is StatusMsg -> addLine("[STATUS]: ${msg.value}")
        is ErrorMsg -> addLine("[ERROR]: ${msg.value}")
        else -> Unit
    }
}