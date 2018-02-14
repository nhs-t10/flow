package org.firstinspires.ftc.teamcode.nodes.hardware

import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.DistanceMsg
import org.firstinspires.ftc.teamcode.messages.TextMsg
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode

/**
 * Created by shaash on 1/9/2018.
 */
class DistanceColorNode(val hardwareMap: HardwareMap) : HeartbeatNode("Distance Color") {
    val sensors = HashMap<String, DistanceSensor>()
    init {
        addDistanceSensors()
    }
    override fun subscriptions() {
    }
    fun addDistanceSensors(){
//        sensors.put("ground", hardwareMap.get(("sensor_color_distance")) as DistanceSensor)
    }

    override fun onHeartbeat() {
        for (key in sensors.keys){
            val sensor = sensors[key]!!
            val distance = sensor.getDistance(DistanceUnit.CM) as Double
            this.publish("/distance/$key", DistanceMsg(distance = distance, priority = 1))
        }
    }
}