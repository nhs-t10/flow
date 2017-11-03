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
        colorSensors.put("colorBall", hardwareMap?.colorSensor?.get("color1")!!)
        for (key in colorSensors.keys){
//            Dispatcher.subscribe("/color/${key}", {setColors(it)})
        }
    }
    fun setColors(colorvals: ColorMsg){
        colorvals.blue
    }
    fun click(hb: HeartBeatMsg){
        //Dispatcher.publish()
    }

}