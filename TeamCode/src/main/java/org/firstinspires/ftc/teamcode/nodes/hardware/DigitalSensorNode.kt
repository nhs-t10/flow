package org.firstinspires.ftc.teamcode.nodes.hardware

import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.DigitalChannelImpl
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.DigitalMsg
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode
import java.util.*
import com.qualcomm.robotcore.hardware.ColorSensor
import org.firstinspires.ftc.teamcode.messages.TextMsg


/**
 * Created by shaash on 11/14/17.
 */

class DigitalSensorNode(val hardwareMap: HardwareMap) : HeartbeatNode("Digital Sensors") {
    val sensors = HashMap<String, DigitalChannel>()
    val sensorStates = HashMap<String, Boolean>()

    init {
        addSensors()
    }
    override fun subscriptions() {
    }
    fun addSensors(){
        sensors.put("front_contact", hardwareMap.digitalChannel.get("touch2")!!)
        sensors.put("left_contact", hardwareMap.digitalChannel.get("touch3")!!)
        // DO NOT PUT MORE SENSORS BELOW THIS LINE
        addSensorStates()
    }
    fun addSensorStates() {
        for (key in sensors.keys) {
            sensorStates.put(key, false)
        }
    }

    override fun onHeartbeat() {
        val allOnHardware = hardwareMap.getAll(DigitalChannel::class.java)
        var names = ""
        for (sensor in allOnHardware) {
            names += hardwareMap.getNamesOf(sensor).toString()
//            sensors.put(sensor.deviceName, sensor)
        }
        publish("/debug", TextMsg(names))
        for (key in sensors.keys){
            val isPressed = sensors[key]?.getState() ?: false
            if (isPressed != sensorStates[key]) {
                sensorStates.put(key, isPressed)
                this.publish("/digital/$key", DigitalMsg(isPressed, 1))
            }
        }
    }
}