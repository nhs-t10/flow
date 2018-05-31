package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode

/**
 * Takes in a "start" function which is called immediately upon start.
 * After the set time interval, the routine ends.
 */
class TimeoutRoutine(val initialCallback: () -> Unit, val time : Long) : RoutineNode("Timeout") {
    var initialTime = 0L
    var done = false // extra security
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
            end()
        }
    }
    fun stopIt() {
        done = true
        end()
    }
}