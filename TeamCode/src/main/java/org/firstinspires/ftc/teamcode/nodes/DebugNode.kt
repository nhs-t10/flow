package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.gamepadJoyOrTrigMsg

/**
 * Created by shaash on 10/24/17.
 */
class DebugNode : Node{
    var telemetry : Telemetry? = null
    constructor(telemetry: Telemetry){
        this.telemetry = telemetry
        Dispatcher.subscribe("/gamepad1/left_stick_y", {this.printMsg(it)})
        Dispatcher.subscribe("/debug", {this.printMsg(it)})
    }
    fun printMsg(m : Message){
        telemetry?.log()?.add(m.toString())
    }
}
