package org.firstinspires.ftc.teamcode.old.nodes

import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.UnitMsg

/**
 * Created by max on 12/6/17.
 */
class SystemNodeOld : NodeOld("System NodeOld") {
    override fun subscriptions() {

    }
    fun publishStart() {
        this.publish("/start", UnitMsg())
    }
    fun publishStop() {
        this.publish("/stop", UnitMsg())
    }
}