package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.hardware.ColorSensor
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
class SensorNode : Node{
    var hardwareMap: HardwareMap? = null
    var imu : BNO055IMU? = null
    var angles: Orientation? = null
    val colorSensors = HashMap<String, ColorSensor>()
    constructor(hardwareMap: HardwareMap, imu: BNO055IMU){
        this.imu = imu
        this.hardwareMap = hardwareMap
        addColorSensors()
        initImu()
        publishImu()
        Dispatcher.subscribe("/heartbeat", {click(it as HeartBeatMsg)})
    }
    fun addColorSensors(){
        colorSensors.put("colorOne", hardwareMap?.colorSensor?.get("color1")!!)
    }
    fun initImu(){
        val parameters = BNO055IMU.Parameters()
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"
        parameters.loggingEnabled = true
        parameters.loggingTag = "IMU"
        parameters.accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()

        imu = hardwareMap?.get(BNO055IMU::class.java, "imu")
        imu?.initialize(parameters)

        imu?.startAccelerationIntegration(Position(), Velocity(), 1000)

    }
    fun publishImu(){
    }


    fun click(hb: HeartBeatMsg){
        val (time) = hb
        //color:
        for (key in colorSensors.keys){
            var red = colorSensors[key]?.red() ?: -1
            var blue = colorSensors[key]?.blue() ?: -1
            var green = colorSensors[key]?.green() ?: -1
            Dispatcher.publish("/colors/$key", ColorMsg(red = red, blue = blue, green = green, priority = 7))

        }
        //imu:
        angles = imu?.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
        var angleUnit = angles?.angleUnit
        var firstAngle = angles?.firstAngle?.toDouble() ?: 0.0
        var secondAngle = angles?.secondAngle?.toDouble() ?: 0.0
        var thirdAngle = angles?.thirdAngle?.toDouble() ?: 0.0
        if(angleUnit != null){
            var heading = AngleUnit.DEGREES.fromUnit(angleUnit, firstAngle)
            var roll = AngleUnit.DEGREES.fromUnit(angleUnit, secondAngle)
            var pitch = AngleUnit.DEGREES.fromUnit(angleUnit, thirdAngle)
            Dispatcher.publish("/imu", ImuMsg(heading, roll, pitch, priority = 1))
        }
    }

    fun formatAngle(angleUnit: AngleUnit, angle: Double): String {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle))
    }

    fun formatDegrees(degrees: Double): String {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees))
    }

}