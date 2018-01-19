package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.AnalogMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.OmniDrive

/**
 * Created by max on 1/19/18.
 */
class DriveToCryptoboxRoutine : RoutineNode("Drive to cryptobox") {
    override fun subscriptions() {
        subscribe("/ultra1", {onDistance(it)})
    }

    fun onDistance(m: Message) {
        val (value) = m as AnalogMsg
        if (value >= 0.15) {
            publish("/drive", OmniDrive(0f, 0f, 0f, 1))
            end()
        }
    }

    override fun start() {
        publish("/drive", OmniDrive(0.2f, 0f, 0f, 1))
    }
}