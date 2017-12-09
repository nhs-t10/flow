package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.HuggerMsg
import org.firstinspires.ftc.teamcode.messages.UnitMsg

/**
 * Ensures knocker doesn't break the adjacent hugger. Closes it and then cancels it so the huggers don't hug themselves.
 */
class OpenHuggerRoutine : RoutineNode("Open Hugger") {
    override fun start() {
        publish("/hugger", HuggerMsg(closeIt = true, onClosed = {
            publish("/hugger/cancel", UnitMsg())
            end()
        }, priority = 1))
    }

    override fun subscriptions() {

    }

}