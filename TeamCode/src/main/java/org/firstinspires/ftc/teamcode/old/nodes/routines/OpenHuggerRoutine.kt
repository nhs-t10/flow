package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.HuggerMsg
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg

/**
 * Ensures knocker doesn't break the adjacent hugger. Closes it and then cancels it so the huggers don't hug themselves.
 */
class OpenHuggerRoutine : RoutineNode("Open Hugger") {
    override fun begin() {
        publish("/hugger", HuggerMsg(closeIt = true, priority = 1))
        end()
    }


    override fun subscriptions() {

    }

}