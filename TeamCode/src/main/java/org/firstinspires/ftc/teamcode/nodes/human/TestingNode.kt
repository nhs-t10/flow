package org.firstinspires.ftc.teamcode.nodes.human

import com.qualcomm.robotcore.hardware.ColorSensor
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.whenDown
import java.util.HashMap

/**
 * Created by max on 1/19/18.
 */
class TestingNode : Node("Testing Node") {


    val cancelLambda = whenDown {
        publish("/macros/cancel", UnitMsg())
        publish("/AngleTurning/cancel", UnitMsg())
        publish("/servos/knocker", ServoMsg(0.08, 0))
        publish("/hugger", HuggerMsg(closeIt = false, priority = 0))
    }

    val cyclableChannels = listOf(
            "/analog/ultra1",
            "/color/ground",
            "/imu",
            "/vuforia",
            "/dogecv"
    )

    var mode = 0

    fun receivedMessage(channel : String, message: Message) {
        if (cyclableChannels[mode] == channel) {
            publish("/debug", message)
        }
    }

    fun renderMode() {
        publish("/telemetry/staticLine", TextMsg("Tailing ${cyclableChannels[mode]}. < and > to change."))
    }

    override fun subscriptions() {
        subscribe("/gamepad1/a", whenDown {
            publish("/AngleTurning/turnTo", AngleTurnMsg(90.0, {}, 1))
        })

        for (channel in cyclableChannels) {
            subscribe(channel, {receivedMessage(channel, it)})
        }
        renderMode()

        subscribe("/gamepad1/right_stick_button", cancelLambda)

        subscribe("/gamepad1/dpad_left", whenDown {
            if (mode <= 0) mode = cyclableChannels.size-1
            else mode--
            renderMode()
        })
        subscribe("/gamepad1/dpad_right", whenDown {
            if (mode >= cyclableChannels.size-1) mode = 0
            else mode++
            renderMode()
        })
    }
}