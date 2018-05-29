package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

/**
 * Created by max on 5/29/18.
 */
@Autonomous(name = "Coroutine Test Auto")
class CoroutineTestAuto : LinearOpMode() {
    suspend fun moveMotor(motor: DcMotor, duration: Long) : Unit {
        motor.setPower(0.5)
        delay(duration)
        motor.setPower(0.0)
    }
    override fun runOpMode() = runBlocking<Unit> {
        val m3 = hardwareMap.dcMotor.get("m3")
        val m2 = hardwareMap.dcMotor.get("m2")
        val timeTaken = measureTimeMillis {
            launch {
                moveMotor(m3, 2000)
                moveMotor(m2, 1000)
            }
        }
        telemetry.addLine("Done in $timeTaken seconds.")
    }
}