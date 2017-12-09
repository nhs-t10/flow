package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Takes in a "start" function which is called immediately upon start.
 * After the set time interval, the "end" function is called and passed its own callback.
 * The "end" function must call this callback to complete the routine.
 */
class TimedCallbackRoutine(val initialCallback: () -> Unit, val time : Long, val finalCallback : (() -> Unit) -> Unit) : RoutineNode("Timed Callback") {
    var initialTime = 0L
    var done = false
    var warned = false
    override fun start() {
        initialCallback()
        initialTime = System.currentTimeMillis()
    }
    override fun subscriptions() {
        subscribe("/heartbeat", {checkTime()})
    }
    fun checkTime() {
        if (System.currentTimeMillis() - initialTime >= time && !done) {
            done = true
            finalCallback({
                end()
            })
        }
        // If we think you forgot to call the callback (stalling for a long time)
        if (done && !warned && System.currentTimeMillis() - initialTime >= (time + 8000)) {
            warned = true
            publish("/warn", TextMsg("[T=${System.currentTimeMillis() - initialTime}] Still waiting for final callback. Did you call cb() in the second callback?"))
        }
    }
}