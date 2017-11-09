package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by shaash on 10/7/17.
 */
abstract class Node {
    fun subscribe(channel: String, callback: (Message) -> Unit) {
        Dispatcher.subscribe(channel, callback)
    }
    fun publish(channel: String, message: Message) {
        Dispatcher.publish(channel, message)
    }
    abstract fun init()

}
