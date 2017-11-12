package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.LinesMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by max on 11/10/17.
 */

/**
 * A centralized effector for sending telemetry messages.
 */
class TelemetryNode(val telemetry : Telemetry) : Node() {

    init {
        telemetry.log().setCapacity(30)
    }

    override fun subscriptions() {
        this.subscribe("/telemetry/line", {this.addLine(it as TextMsg)})
        this.subscribe("/telemetry/staticLine", {this.staticLine(it as TextMsg)})
        this.subscribe("/telemetry/lines", {this.renderLines(it as LinesMsg)})
        this.subscribe("/telemetry/clear", {this.clear()})
    }

    fun addLine(m: TextMsg) {
        telemetry.log().add(m.text)
    }

    fun renderLines(msg: LinesMsg) {
        telemetry.clear()
        for (i in msg.lines.indices) {
            telemetry.addData(i as String, msg.lines[i])
        }
    }

    fun staticLine(msg: TextMsg) {
        telemetry.addData("0", msg.text)
    }

    fun clear() {
        telemetry.clearAll()
    }
}