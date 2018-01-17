package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by dvw06 on 11/16/17.
 */
abstract class RoutineNode(name : String ) : Node(name), Routinable {

    var routineCallback : (() -> Unit)? = null

    var routineActive = false

    fun callIfActive(cb: (Message) -> Unit) : (Message) -> Unit {
        return fun(m: Message) {
            if (routineActive) {
                cb(m)
            }
        }
    }

    override fun subscribe(channel: String, cb: (Message) -> Unit) {
        Dispatcher.subscribe(channel, callIfActive { cb(it) })
    }

    override fun begin(callback: () -> Unit) {
        this.routineCallback = callback
        this.subscriptions()
        this.start()


        routineActive = true
    }

    abstract fun start()

    fun end() {
        if (routineCallback != null) this.routineCallback?.invoke()
        routineActive = false
    }
}