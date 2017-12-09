package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode

/**
 * Takes in a "start" function which is called immediately upon start.
 * After the set time interval, the "end" function is called and passed its own callback.
 * The "end" function must call this callback to complete the routine.
 */
class TimedCallbackRoutine(val initialCallback: () -> Unit, val time : Long, val finalCallback : (() -> Unit) -> Unit) : RoutineNode("Timed Callback") {
    var initialTime = 0L
    override fun start() {
        initialCallback()
        initialTime = System.currentTimeMillis()
    }
    override fun subscriptions() {
        subscribe("/heartbeat", {checkTime()})
    }
    fun checkTime() {
        if (System.currentTimeMillis() - initialTime >= time) {
            finalCallback({
                end()
            })
        }
    }
}