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
class ImuNode : Node{
    var hardwareMap: HardwareMap? = null
    var imu : BNO055IMU? = null
    constructor(hardwareMap: HardwareMap){
        this.hardwareMap = hardwareMap
    }
    override fun init() {
        initImu()
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
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

    fun update(hb: HeartBeatMsg){
        val (time) = hb
        //imu:
        val angles = imu?.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)
        val angleUnit = angles?.angleUnit
        val firstAngle = angles?.firstAngle?.toDouble() ?: 0.0
        val secondAngle = angles?.secondAngle?.toDouble() ?: 0.0
        val thirdAngle = angles?.thirdAngle?.toDouble() ?: 0.0
        if(angleUnit != null){
            val heading = AngleUnit.DEGREES.fromUnit(angleUnit, firstAngle)
            val roll = AngleUnit.DEGREES.fromUnit(angleUnit, secondAngle)
            val pitch = AngleUnit.DEGREES.fromUnit(angleUnit, thirdAngle)
            this.publish("/imu", ImuMsg(heading, roll, pitch, priority = 1))
        }
    }
}