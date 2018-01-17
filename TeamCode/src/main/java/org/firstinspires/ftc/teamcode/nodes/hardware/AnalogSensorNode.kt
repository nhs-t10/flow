package org.firstinspires.ftc.teamcode.nodes.hardware

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import java.util.*
import org.firstinspires.ftc.teamcode.messages.AnalogMsg

/**
 * Created by shaash on 11/14/17.
 */

class AnalogSensorNode(val hardwareMap: HardwareMap) : Node("Analog Sensors") {
    val sensors = HashMap<String, AnalogInput>()

    init {
        addSensors()
    }
    override fun subscriptions() {
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
    }
    fun addSensors(){
        sensors.put("infrared", hardwareMap.analogInput.get("ir1")!!)
    }

    fun update(hb: HeartBeatMsg){

        for (key in sensors.keys){
            val value = sensors[key]?.getVoltage() ?: 0.0
            this.publish("/analog/$key", AnalogMsg(value, 1))
        }
    }
}