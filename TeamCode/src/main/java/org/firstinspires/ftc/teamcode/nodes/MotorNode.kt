package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.Channels
import org.firstinspires.ftc.teamcode.MoveMotorMsg
import org.firstinspires.ftc.teamcode.Node

class MotorNode(val channels: Channels, val motor: DcMotor) : Node() {
    override fun subscriptions() {

        subscribe(channels.moveMotorChannel, {
            when(it) {
                is MoveMotorMsg -> {
                    if (it.on) {
                        motor.setPower(0.4)
                    }
                    else {
                        motor.setPower(0.0)
                    }
                }
            }
        })
    }
}