package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.TouchMsg
import java.util.*

/**
 * Created by shaash on 11/14/17.
 */

class DigitalSensorNode(val hardwareMap: HardwareMap) : Node("Touch Sensor") {
    val sensors = HashMap<String, DigitalChannel>()
    init {
        addSensors()
    }
    override fun subscriptions() {
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
    }
    fun addSensors(){
        sensors.put("touchOne", hardwareMap?.digitalChannel.get("touch1")!!)
    }
    fun update(hb: HeartBeatMsg){
        for (key in sensors.keys){
            val isPressed = sensors[key]?.getState() ?: false
            this.publish("/digital/$key", TouchMsg(isPressed, 1))
        }
    }
}