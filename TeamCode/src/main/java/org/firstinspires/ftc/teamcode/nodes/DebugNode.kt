package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.MotorMsg
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by shaash on 10/24/17.
 */
class DebugNode : Node("Debug") {
    override fun subscriptions() {
        this.subscribe("/debug", {this.printMsg(it)})
        this.subscribe("/warn"){this.printWarning(it as TextMsg)}
        this.subscribe("/error", {this.printError(it as TextMsg)})
    }
    fun printMsg(m : Message){
        this.publish("/telemetry/line", TextMsg(m.toString()))
    }
    fun printWarning(m : TextMsg) {
        this.publish("/telemetry/line", TextMsg(text="WARNING: ${m.text}", priority = 0))
    }
    fun printError(m : TextMsg) {
        this.publish("/telemetry/line", TextMsg("ERROR: ${m.text}", -1))
    }
}
