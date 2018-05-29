package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

/**
 * Created by max on 5/29/18.
 */
@Autonomous(name = "Coroutine Test Auto")
class CoroutineTestAuto : OpMode() {
    suspend fun moveMotor(motor: DcMotor, duration: Long):Int {
        motor.setPower(0.5)
        delay(duration)
        motor.setPower(0.0)
        logline("donezo")
        return 10
    }

    override fun init() {

    }

    override fun loop() {

    }
    fun logline(message: String) {
        telemetry.addLine(message)
        telemetry.update()
    }
    //TODO: continuous threaded space
    override fun start() = runBlocking {
        msStuckDetectStart = 10000
        telemetry.setAutoClear(false)
        val m3 = hardwareMap.dcMotor.get("m3")
        val m2 = hardwareMap.dcMotor.get("m2")
        val time = measureTimeMillis {
            val one = async { moveMotor(m2, 1000L) }
            val two = async { moveMotor(m3, 700L) }
            logline("The answer is ${one.await() + two.await()}")
        }
        delay(500)
        logline("Completed in $time ms")
        delay(500)
    }
}