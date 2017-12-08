package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode

/**
 * Created by dvw08 on 12/8/17.
 */
class TimedCallbackRoutine(val initialCallback: () -> Unit, val time : Long, val finalCallback : () -> Unit) : RoutineNode("Timed Callback") {
    var initialTime = 0L
    override fun start() {
        initialCallback()
        initialTime = System.currentTimeMillis()
    }
    override fun subscriptions() {
        subscribe("/heartbeat", {checkTime()})
    }
    fun checkTime() {
        if (System.currentTimeMillis() - initialTime >= time) finalCallback()
    }
}