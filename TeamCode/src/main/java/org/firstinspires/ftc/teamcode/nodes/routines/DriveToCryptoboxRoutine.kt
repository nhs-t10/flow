package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.AnalogMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.util.TeamColor

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
        if (Math.abs(value - (fst + snd)/2) <= 0.036) {
            onDistance(value)
        }
        buffer = Pair(snd, value)
    }

    fun onDistance(value : Double) {
        publish("/drive", OmniDrive(0f, 0f, 0f, 1))
        end()
    }

    override fun begin() {
        val sign = if (teamColor == TeamColor.RED) -1 else 1
        publish("/drive", OmniDrive(sign * 0.6f, 0f, 0f, 1))
    }
}
