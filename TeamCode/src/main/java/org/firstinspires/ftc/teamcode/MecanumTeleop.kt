package org.firstinspires.ftc.teamcode

/**
 * Created by dvw06 on 5/25/17.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple


@TeleOp(name = "Basic Mecanum")
class MecanumTeleop : OpMode() {
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
        lr?.direction = DcMotorSimple.Direction.REVERSE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      }

    override fun loop() {

        /**
        val lx = cube(gamepad1.left_stick_x)
        val ly = cube(- gamepad1.left_stick_y)
        val rot = cube(-gamepad1.right_stick_x)
        val theta = Math.atan2(lx, ly)
        val mag = Math.min(1.0, Math.sqrt(lx * lx + ly * ly))
        drive(theta, mag, rot)
        */
    }

    private fun drive(direction : Double, velocity: Double, rot : Double) {
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
    }
}