package org.firstinspires.ftc.teamcode.old

import org.firstinspires.ftc.teamcode.old.messages.Message

/**
 * Created by max on 2/18/18.
 */
interface Nodeable {
    fun subscribe (channel: String, callback: (Message) -> Unit) : String
    fun publish(channel: String, message: Message)

    fun subscriptions()

    fun endNode()

    fun start()

    val nodeName : String
}