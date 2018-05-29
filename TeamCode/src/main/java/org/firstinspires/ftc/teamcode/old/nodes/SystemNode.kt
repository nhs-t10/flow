package org.firstinspires.ftc.teamcode.old.nodes

import org.firstinspires.ftc.teamcode.old.Node
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg

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