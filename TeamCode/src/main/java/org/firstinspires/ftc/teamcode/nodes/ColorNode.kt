package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.robocol.Heartbeat
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import java.util.*
import org.firstinspires.ftc.robotcore.external.Func
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation
import org.firstinspires.ftc.robotcore.external.navigation.Position
import org.firstinspires.ftc.robotcore.external.navigation.Velocity
import org.firstinspires.ftc.teamcode.messages.ImuMsg

/**
 * Created by shaash on 10/26/17.
 */

class ColorNode(val hardwareMap: HardwareMap) : Node("Color"){
    val colorSensors = HashMap<String, ColorSensor>()
    init {
        addColorSensors()
    }
    override fun subscriptions() {
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
    }
    fun addColorSensors(){
        colorSensors.put("knocker", hardwareMap?.colorSensor?.get("color1")!!)
//        colorSensors["knocker"]?.enableLed(false)
    }

    fun update(hb: HeartBeatMsg){
        val (time) = hb
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