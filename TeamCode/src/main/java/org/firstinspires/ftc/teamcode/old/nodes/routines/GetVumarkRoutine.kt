package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.MessageOld
import org.firstinspires.ftc.teamcode.old.messages.TextMsg
import org.firstinspires.ftc.teamcode.old.messages.VuforiaMsg

/**
 * Created by max on 1/17/18.
 */
class GetVumarkRoutine(val finalCallback : (RelicRecoveryVuMark) -> Unit) : RoutineNode("Get Vumark") {
    val COLLECTION_TIME = 1000
    // TODO: this doesnt work for blue
    val defaultMark : RelicRecoveryVuMark = RelicRecoveryVuMark.RIGHT
    var initialTime = 0L

    override fun begin() {
        initialTime = System.currentTimeMillis()
    }

    fun receiveVision(m: MessageOld) {
        val (mark) = m as VuforiaMsg
        if (mark != RelicRecoveryVuMark.UNKNOWN) {
            // omg we saw something, send it over and end
            publish("/status", TextMsg("Saw $mark vumark."))
            finalCallback(mark)
            end()
        }
    }

    override fun onHeartbeat() {
        if (System.currentTimeMillis() - initialTime >= COLLECTION_TIME) {
            // Didn't see nothin. L
            publish("/warn", TextMsg("Didn't see any vumarks this time."))
            finalCallback(defaultMark)
            end()
        }
    }

    override fun subscriptions() {
        subscribe("/vuforia", {receiveVision(it)})
    }
}