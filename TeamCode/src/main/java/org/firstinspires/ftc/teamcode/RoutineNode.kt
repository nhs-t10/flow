package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by dvw06 on 11/16/17.
 */
abstract class RoutineNode(val name : String) : Node(name), Routinable {

    var routineCallback : (() -> Unit)? = null

    var routineActive = false

    var routineStartTime = 0L

    val routineWarningTime = 10000

    fun callIfActive(cb: (Message) -> Unit) : (Message) -> Unit {
        return fun(m: Message) {
            if (routineActive) {
                if (System.currentTimeMillis() - routineStartTime >= routineWarningTime) {
                    publish("/warn", TextMsg("[${System.currentTimeMillis()-routineStartTime}] Routine $name has been running for at least 10 seconds..."))
                }
                cb(m)
            }
        }
    }

    override fun subscribe(channel: String, cb: (Message) -> Unit) {
        Dispatcher.subscribe(channel, callIfActive { cb(it) })
    }

    override fun begin(callback: () -> Unit) {
        this.routineCallback = callback
        this.publish("/status", TextMsg("$name began!"))
        this.subscriptions()
        this.start()
        routineStartTime = System.currentTimeMillis()


        routineActive = true
    }

    abstract fun start()

    fun end() {
        if (routineCallback != null){
            this.publish("/status", TextMsg("$name finished!"))
            this.routineCallback?.invoke()
        }
        routineActive = false
    }
}