package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.TextMsg

/**
 * Takes in a "start" function which is called immediately upon start.
 * After the set time interval, the "end" function is called and passed its own callback.
 * The "end" function must call this callback to complete the routine.
 */
class TimedCallbackRoutine(val initialCallback: () -> Unit, val time : Long, val finalCallback : (() -> Unit) -> Unit) : RoutineNode("Timed Callback") {
    var initialTime = 0L
    var done = false
    var warned = false
    override fun begin() {
        if (!done) {
            initialCallback()
            initialTime = System.currentTimeMillis()
        }
    }
    override fun subscriptions() {
        subscribe("/macros/cancel", {stopIt()})
    }

    override fun onHeartbeat() {
        if (System.currentTimeMillis() - initialTime >= time && !done) {
            done = true
            finalCallback({
                end()
            })
        }
        // If we think you forgot to call the callback (stalling for a long time)
        if (done && !warned && System.currentTimeMillis() - initialTime >= (time + 8000)) {
            warned = true
            publish("/warn", TextMsg("[T=${System.currentTimeMillis() - initialTime}] Still waiting for final callback in use of TimedCallbackRoutine. Did you call cb() in the second callback?"))
        }
    }
    fun stopIt() {
        done = true
        end()
    }
}