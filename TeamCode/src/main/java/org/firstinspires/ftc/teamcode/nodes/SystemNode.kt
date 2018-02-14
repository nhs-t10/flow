package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.UnitMsg

/**
 * Created by max on 12/6/17.
 */
class SystemNode : Node("System Node") {
    override fun subscriptions() {
    }

    fun publishStart() {
        this.publish("/start", UnitMsg())
    }
    fun publishStop() {
        this.publish("/stop", UnitMsg())
    }
}