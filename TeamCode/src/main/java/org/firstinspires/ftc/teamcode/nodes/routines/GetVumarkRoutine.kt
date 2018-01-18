package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg
import org.firstinspires.ftc.teamcode.messages.VuforiaMsg

/**
 * Created by max on 1/17/18.
 */
class GetVumarkRoutine(val finalCallback : (RelicRecoveryVuMark) -> Unit) : RoutineNode("Get Vumark") {
    val COLLECTION_TIME = 1000
    val defaultMark : RelicRecoveryVuMark = RelicRecoveryVuMark.UNKNOWN
    var initialTime = 0L

    override fun start() {
        initialTime = System.currentTimeMillis()
    }

    fun receiveVision(m: Message) {
        val (mark) = m as VuforiaMsg
        if (mark != RelicRecoveryVuMark.UNKNOWN) {
            // omg we saw something, send it over and end
            publish("/status", TextMsg("Saw $mark vumark."))
            finalCallback(mark)
            end()
        }
    }

    fun receiveHeartbeat() {
        if (System.currentTimeMillis() - initialTime >= COLLECTION_TIME) {
            // Didn't see nothin. L
            publish("/warn", TextMsg("Didn't see any vumarks this time."))
            finalCallback(defaultMark)
            end()
        }
    }

    override fun subscriptions() {
        subscribe("/vuforia", {receiveVision(it)})
        subscribe("/heartbeat", {receiveHeartbeat()})
    }
}