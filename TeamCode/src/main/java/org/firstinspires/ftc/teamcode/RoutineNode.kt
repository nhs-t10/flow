package org.firstinspires.ftc.teamcode

/**
 * Created by dvw06 on 11/16/17.
 */
abstract class RoutineNode(name : String) : Node(name), Routinable {

    var callback : (() -> Unit)? = null

    override fun begin(callback: () -> Unit) {
        this.callback = callback
        this.subscriptions()
        this.start()
    }

    abstract fun start()

    fun end() {
        if (callback != null) this.callback?.invoke()
    }
}