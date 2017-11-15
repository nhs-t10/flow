package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.TouchMsg

/**
 * Created by shaash on 11/14/17.
 */

class TouchNode(val hardwareMap: HardwareMap) : Node() {
    val touchSensors = HashMap<String, DigitalChannel>()
    init {
        addTouchSensors()
    }
    override fun subscriptions() {
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
    }
    fun addTouchSensors(){
        touchSensors.put("touchOne", hardwareMap?.digitalChannel.get("touch1")!!)
    }
    fun update(hb: HeartBeatMsg){
        //touch:
        for (key in touchSensors.keys){
            val isPressed = touchSensors[key]?.getState() ?: false
            this.publish("/touch/$key", TouchMsg(isPressed, 1))
        }
    }
}