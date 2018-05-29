package org.firstinspires.ftc.teamcode.old.nodes.hardware

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.old.NodeOld
import java.util.*
import org.firstinspires.ftc.teamcode.old.messages.AnalogMsg
import org.firstinspires.ftc.teamcode.old.nodes.HeartbeatNode

/**
 * Created by shaash on 11/14/17.
 */

class AnalogSensorNode(val hardwareMap: HardwareMap) : HeartbeatNode("Analog Sensors") {
    val sensors = HashMap<String, AnalogInput>()

    init {
        addSensors()
    }
    override fun subscriptions() {}

    fun addSensors(){
        //sensors.put("infrared", hardwareMap.analogInput.get("ir1")!!)
        sensors.put("ultra1", hardwareMap.analogInput.get("ultra1"))
    }

    override fun onHeartbeat (){
        for (key in sensors.keys){
            val value = sensors[key]?.getVoltage() ?: 0.0
            this.publish("/analog/$key", AnalogMsg(value, 1))
        }
    }
}