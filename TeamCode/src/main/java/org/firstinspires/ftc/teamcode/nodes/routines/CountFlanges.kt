package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RobotState
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.AnalogMsg
import org.firstinspires.ftc.teamcode.messages.*

/**
 * Created by max on 1/17/18.
 */
class CountFlanges(getState: () -> RobotState) : RoutineNode("Count Flanges") {
    fun onInfrared(m: Message) {
        val (value) = m as AnalogMsg
        if (value > 0.2) {

        }
        end()
    }

    override fun subscriptions() {
        subscribe("/infrared", {onInfrared(it)})
    }

    override fun start() {}
}
