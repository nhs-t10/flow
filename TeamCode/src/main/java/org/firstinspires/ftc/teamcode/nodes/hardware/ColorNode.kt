package org.firstinspires.ftc.teamcode.nodes.hardware

import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import java.util.*
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode

/**
 * Created by shaash on 10/26/17.
 */

class ColorNode(val hardwareMap: HardwareMap) : HeartbeatNode("Color"){
    val colorSensors = HashMap<String, ColorSensor>()
    init {
        addColorSensors()
    }
    override fun subscriptions() {
    }
    fun addColorSensors(){
        colorSensors.put("knocker", hardwareMap?.colorSensor?.get("color1")!!)
//        colorSensors.put("ground", hardwareMap?.colorSensor?.get("sensor_color_distance")!!)
//        colorSensors["knocker"]?.enableLed(false)
    }

    override fun onHeartbeat() {
        //color:
        for (key in colorSensors.keys){
            val red = colorSensors[key]?.red() ?: -1
            val blue = colorSensors[key]?.blue() ?: -1
            val green = colorSensors[key]?.green() ?: -1
            val alpha = colorSensors[key]?.alpha() ?: -1
            val hue = colorSensors[key]?.argb() ?: -1
            this.publish("/color/$key", ColorMsg(red = red, blue = blue, green = green, alpha = alpha, hue = hue, priority = 1))

        }
    }
}