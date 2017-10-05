package org.firstinspires.ftc.teamcode

/**
 * Created by dvw06 on 5/25/17.
 */

import android.provider.Settings
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import java.lang.Math.abs

//fun cube(x : Float) : Double { return Math.pow(x.toDouble(), 3.0) }

@TeleOp(name = "Omni Drive")
class OmniTeleop : OpMode() {
    var lf : DcMotor? = null
    var lr : DcMotor? = null
    var rf : DcMotor? = null
    var rr : DcMotor? = null

    override fun init() {
        lf = hardwareMap.dcMotor.get("m3")
        lr = hardwareMap.dcMotor.get("m1")
        rf = hardwareMap.dcMotor.get("m4")
        rr = hardwareMap.dcMotor.get("m2")
        lf?.direction = DcMotorSimple.Direction.REVERSE
        lr?.direction = DcMotorSimple.Direction.REVERSE
    }

    override fun loop() {
        val rotational = gamepad1.right_stick_x
        val northSouth = gamepad1.left_stick_y
        val eastWest = gamepad1.left_stick_x
        /*edit this*/
        val forwardMultiplier = arrayOf(1f, 1f,
                1f, 1f)
        val leftRightMultiplier = arrayOf(1f, -1f,
                -1f, 1f)
        val rotationalMultiplier = arrayOf(1f, -1f,
                1f, -1f)
        val forwardsComponent = forwardMultiplier.map { it*northSouth}
        val eastWestComponent = leftRightMultiplier.map { it*eastWest}
        val rotationalComponent = rotationalMultiplier.map { it*rotational}

        val motorvals = drive(forwardsComponent, eastWestComponent, rotationalComponent)
        lf?.setPower(motorvals[0].toDouble())
        rf?.setPower(motorvals[1].toDouble())
        lr?.setPower(motorvals[2].toDouble())
        rr?.setPower(motorvals[3].toDouble())
        telemetry.addLine(motorvals.toString())


        /*
        val lx = cube(gamepad1.left_stick_x)
        val ly = cube(- gamepad1.left_stick_y)
        val rot = cube(-gamepad1.right_stick_x)
        val theta = Math.atan2(lx, ly)
        val mag = Math.min(1.0, Math.sqrt(lx * lx + ly * ly))
        drive(theta, mag, rot)
         */
    }

    private fun drive(forwardback : List<Float>, leftright : List<Float>, rotation : List<Float>):List<Float> {
        val sumlf = forwardback[0] + leftright[0] + rotation[0]
        val sumrf = forwardback[1] + leftright[1] + rotation[1]
        val sumlr = forwardback[2] + leftright[2] + rotation[2]
        val sumrr = forwardback[3] + leftright[3] + rotation[3]
        val sums = arrayOf(sumlf, sumrf, sumlr, sumrr)
        val findhighestinthis = sums.map{abs(it)}
        val highestsum:Float? = findhighestinthis.max()
        var attenuationfactor = 1f
        if (highestsum == null){
            attenuationfactor = 1f
        }

        else if (abs(highestsum) > 1f) {
            attenuationfactor = highestsum
        } else {
            attenuationfactor = 1f
        }
        /*
        lf?.setPower((sumlf as Double) / attenuationfactor)
        rf?.setPower((sumrf as Double) / attenuationfactor)
        lf?.setPower((sumlr as Double) / attenuationfactor)
        rr?.setPower((sumrr as Double) / attenuationfactor)
        */
        val finalvals = sums.map { it / attenuationfactor }
        telemetry.addLine(attenuationfactor.toString())
        telemetry.addLine(highestsum.toString())



        return finalvals



        /*
        val s = velocity * Math.sin(direction + Math.PI / 4.0)
        val c = velocity * Math.cos(direction + Math.PI / 4.0)
        val lfp = s + rot
        val rfp = c - rot
        val lrp = c + rot
        val rrp = s - rot
        val mx = doubleArrayOf(1.0, lfp, rfp, lrp, rrp).reduce {
            acc, d -> Math.max(acc, Math.abs(d))
        }
        lf?.power = lfp / mx
        rf?.power = rfp / mx
        lr?.power = lrp / mx
        rr?.power = rrp / mx
        */
    }
}