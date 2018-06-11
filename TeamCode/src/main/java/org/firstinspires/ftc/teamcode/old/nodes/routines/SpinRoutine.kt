package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.AngleTurnMsg

/**
 * Created by shaash on 11/21/17.
 */
class SpinRoutine(val dest : Double) : RoutineNode("Spin Routine"){
    override fun begin() {
        this.publish("/AngleTurning/turnTo", AngleTurnMsg(angle = dest, callback = {end()}, priority = 1))
    }

    override fun subscriptions(){}
}