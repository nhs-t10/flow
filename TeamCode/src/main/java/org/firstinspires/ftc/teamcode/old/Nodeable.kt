package org.firstinspires.ftc.teamcode.old

import org.firstinspires.ftc.teamcode.old.messages.MessageOld

/**
 * Created by max on 2/18/18.
 */
interface Nodeable {
    fun subscribe (channel: String, callback: (MessageOld) -> Unit) : String
    fun publish(channel: String, message: MessageOld)

    fun subscriptions()

    fun endNode()

    fun start()

    val nodeName : String
}