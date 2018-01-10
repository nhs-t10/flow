package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.DistanceMsg

/**
 * Created by shaash on 1/9/2018.
 */
class DistanceColorNode(val hardwareMap: HardwareMap) : Node("Distance Color") {
    val distanceSensors = HashMap<String, DistanceSensor>()
    init {
        addColorSensors()
    }
    override fun subscriptions() {
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
    }
    fun addColorSensors(){
        distanceSensors.put("CryptomoxDetect", hardwareMap.get(("sensor_color_distance")) as DistanceSensor)
    }

    fun update(hb: HeartBeatMsg){
        val (time) = hb
        //color:
        for (key in distanceSensors.keys){
            val distance = distanceSensors[key]?.getDistance(DistanceUnit.CM) as Double
            this.publish("/distance/$key", DistanceMsg(distance = distance, priority = 1))

        }
    }
}