package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.RoutineNode

/**
 * Created by max on 11/19/17.
 */

class RoutineGroup(val routines: List<RoutineNode>) : Routinable {
    var index = 0
    var callback : (() -> Unit)? = null
    fun cb() {
        if (index < routines.size) {
            routines[index].begin { cb() }
            index++
        }
        else {
            if (callback != null) this.callback?.invoke()
        }
    }
    override fun begin(callback : () -> Unit) {
        index = 0
        this.callback = callback

        cb()
    }
}