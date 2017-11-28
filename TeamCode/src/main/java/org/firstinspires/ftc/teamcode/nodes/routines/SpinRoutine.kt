package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.AngleTurnMsg

/**
 * Created by shaash on 11/21/17.
 */
class SpinRoutine : RoutineNode(name = "Spin Routine"){
    override fun start() {
        this.publish("/AngleTurning/turnTo", AngleTurnMsg(angle = 30.0, callback = {stop()}, priority = 1))
    }
    fun stop(){
        end()
    }

    override fun subscriptions() {
    }
}