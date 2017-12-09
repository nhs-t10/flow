package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.HuggerMsg
import org.firstinspires.ftc.teamcode.messages.UnitMsg

/**
 * Created by dvw04 on 12/7/17.
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