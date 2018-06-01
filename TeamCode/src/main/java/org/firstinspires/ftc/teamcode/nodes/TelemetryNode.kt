package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.*

class TelemetryNode (val channels: Channels, val telemetry: Telemetry) : Node() {
    override fun subscriptions() {
        // this and telemetry.update() make telemetry work with threads
        telemetry.setAutoClear(false)
        telemetry.log().setCapacity(10)

        subscribe(channels.debugChannel, {onMsg(it)})
    }

    fun addLine(text: String) {
        telemetry.log().add(text)
        telemetry.update() // I think? you need this if it's threaded
    }
    fun clear() {
        telemetry.log().clear()
        telemetry.update()
    }

    fun onMsg(msg:Message) : Unit = when(msg) {
        is ClearMsg -> clear()
        is DebugMsg -> addLine("[DEBUG]: ${msg.value}")
        is StatusMsg -> addLine("[STATUS]: ${msg.value}")
        is ErrorMsg -> addLine("[ERROR]: ${msg.value}")
        else -> Unit
    }
}