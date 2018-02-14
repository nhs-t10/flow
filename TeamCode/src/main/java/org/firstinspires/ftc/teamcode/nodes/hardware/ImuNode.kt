package org.firstinspires.ftc.teamcode.nodes.hardware

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Position
import org.firstinspires.ftc.robotcore.external.navigation.Velocity
import org.firstinspires.ftc.teamcode.messages.ImuMsg
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode

/**
 * Created by shaash on 10/26/17.
 */
class ImuNode(val hardwareMap: HardwareMap) : HeartbeatNode("IMU"){
    var imu : BNO055IMU? = null
    init {
        initImu()
    }

    override fun subscriptions() {
    }

    fun initImu(){
        val parameters = BNO055IMU.Parameters()
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"
        parameters.loggingEnabled = true
        parameters.loggingTag = "IMU"
        parameters.accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()

        imu = hardwareMap.get(BNO055IMU::class.java, "imu")
        imu?.initialize(parameters)

        imu?.startAccelerationIntegration(Position(), Velocity(), 1000)

    }

    override fun onHeartbeat(){
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