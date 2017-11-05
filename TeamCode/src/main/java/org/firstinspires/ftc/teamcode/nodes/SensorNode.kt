package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.robocol.Heartbeat
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import java.util.*

/**
 * Created by shaash on 10/26/17.
 */
class SensorNode : Node{
    var hardwareMap: HardwareMap? = null
    val colorSensors = HashMap<String, ColorSensor>()
    constructor(hardwareMap: HardwareMap){
        this.hardwareMap = hardwareMap
        addColorSensors()
        Dispatcher.subscribe("/heartbeat", {click(it as HeartBeatMsg)})
    }
    fun addColorSensors(){
        colorSensors.put("colorOne", hardwareMap?.colorSensor?.get("color1")!!)
        for (key in colorSensors.keys){
            if(colorSensors[key]?.red() != null){
                var red = colorSensors[key]?.red() ?: -1
                var blue = colorSensors[key]?.blue() ?: -1
                var green = colorSensors[key]?.green() ?: -1
                Dispatcher.publish("/colors/$key", ColorMsg(red = red, blue = blue, green = green, priority = 7))
            }
        }
    }


    fun click(hb: HeartBeatMsg){
        //Dispatcher.publish()
    }

}