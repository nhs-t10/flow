package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode

/**
 * Created by dvw06 on 11/16/17.
 */
abstract class RoutineNode(val routineName : String) : HeartbeatNode(routineName), Routinable {

    val subscribedChannels = mutableListOf<Pair<String, String>>()
    var routineCallback : (() -> Unit)? = null

    var routineActive = false

    var routineStartTime = 0L

    val routineWarningTime = 10000

    fun callIfActive(cb: (Message) -> Unit) : (Message) -> Unit {
        return fun(m: Message) {
            if (routineActive) {
                if (System.currentTimeMillis() - routineStartTime >= routineWarningTime) {
                    publish("/warn", TextMsg("[${System.currentTimeMillis()-routineStartTime}] Routine $routineName has been running for at least 10 seconds..."))
                }
                cb(m)
            }
        }
    }

    override fun subscribe(channel: String, cb: (Message) -> Unit) : String {
        val id = Dispatcher.subscribe(channel, routineName, callIfActive { cb(it) })
        subscribedChannels.add(Pair(channel, id))
        return id
    }

    override fun beginRoutine(callback: () -> Unit) {
        this.routineCallback = callback
        this.publish("/status", TextMsg("$routineName began!"))
        this.subscriptions()
        this.begin()
        this.start()
        routineStartTime = System.currentTimeMillis()


        routineActive = true
    }

    abstract fun begin()

    fun end() {
        if (routineCallback != null){
            this.publish("/status", TextMsg("$routineName finished!"))
            this.routineCallback?.invoke()
            for ((channel, id) in subscribedChannels) {
                Dispatcher.unsubscribe(channel, id)
            }
        }
        routineActive = false
    }

    override fun onHeartbeat() {}
}