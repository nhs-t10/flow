package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.RoutineNode

/**
 * Created by max on 11/19/17.
 */

class RoutineGroup(val routines: List<RoutineNode>) : Routinable {
    var index = 0
    var stopped = false
    var callback : (() -> Unit)? = null
    init {
        Dispatcher.subscribe("/macros/cancel", {cancel()})
    }
    fun cancel() {
        stopped = true
    }
    fun cb() {
        if (index < routines.size && !stopped) {
            routines[index].beginRoutine { cb() }
            index++
        }
        else {
            if (callback != null) this.callback?.invoke()
        }
    }
    override fun beginRoutine(callback : () -> Unit) {
        index = 0
        this.callback = callback

        cb()
    }
}