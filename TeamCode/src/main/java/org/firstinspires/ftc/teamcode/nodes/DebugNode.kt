package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by shaash on 10/24/17.
 */
class DebugNode : Node{
    var telemetry : Telemetry? = null
    constructor(telemetry: Telemetry){
        this.telemetry = telemetry
    }
    override fun init() {
        this.telemetry?.log()?.setCapacity(25)
        //this.subscribe("/servos/s0"){this.printMsg(it)}
        //this.subscribe("/colors/colorOne"){this.printMsg(it)}
        //this.subscribe("/vuforia"){this.printMsg(it)}
        this.subscribe("/imu"){this.printMsg(it)}
        // this.subscribe("/debug", {this.printMsg(it)})
        this.subscribe("/warn"){this.printWarning(it)}
    }
    fun printMsg(m : Message){
        telemetry?.log()?.add(m.toString())
    }
    fun printWarning(m : Message) {
        telemetry?.log()?.add("WARNING: $m")
    }
}
