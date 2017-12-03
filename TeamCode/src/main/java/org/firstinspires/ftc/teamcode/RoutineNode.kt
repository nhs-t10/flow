package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by dvw06 on 11/16/17.
 */
abstract class RoutineNode(name : String ) : Node(name), Routinable {

    var callback : (() -> Unit)? = null

    var active = false

    fun callIfActive(callback: (Message) -> Unit) : (Message) -> Unit {
        return fun(m: Message) {
            if (active) callback(m)
        }
    }

    override fun subscribe(channel: String, callback: (Message) -> Unit) {
        Dispatcher.subscribe(channel, callIfActive { callback })
    }

    override fun begin(callback: () -> Unit) {
        this.callback = callback
        this.subscriptions()
        this.start()


        active = true
    }

    abstract fun start()

    fun end() {
        if (callback != null) this.callback?.invoke()
        active = false
    }
}