package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.AnalogMsg
import org.firstinspires.ftc.teamcode.old.messages.Message
import org.firstinspires.ftc.teamcode.old.messages.OmniDrive
import org.firstinspires.ftc.teamcode.old.messages.TextMsg
import org.firstinspires.ftc.teamcode.old.util.TeamColor

/**
 * Created by max on 1/19/18.
 */

class DriveToCryptoboxRoutine(val teamColor : TeamColor) : RoutineNode("Drive to cryptobox") {
    var buffer = Pair(0.0, 0.0)

    override fun subscriptions() {
        subscribe("/analog/ultra1", {bufferDistance(it)})
    }

    fun bufferDistance(m: Message) {
        val (value) = m as AnalogMsg
        val (fst, snd) = buffer
        // Making sure a drastic change is legit
        val expectedDelta = 0.03
        if (Math.abs(value - (fst + snd)/2) <= expectedDelta) {
            publish("/debug", TextMsg("Observed normal ultrasonic value: $value"))
            onDistance(value)
        }
        buffer = Pair(snd, value)
    }

    fun onDistance(value : Double) {
        if (value < 0.04) {
            publish("/drive", OmniDrive(0f, 0f, 0f, 1))
            end()
        }
    }

    override fun begin() {
        val sign = if (teamColor == TeamColor.RED) -1 else 1
        val speed = 0.6f
        publish("/drive", OmniDrive(sign * speed, 0f, 0f, 1))
    }
}
