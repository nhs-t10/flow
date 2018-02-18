package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by max on 2/18/18.
 */
interface Nodeable {
    fun subscribe (channel: String, callback: (Message) -> Unit)
    fun publish(channel: String, message: Message)

    fun subscriptions()

    fun endNode()

    fun start()

    val nodeName : String
}