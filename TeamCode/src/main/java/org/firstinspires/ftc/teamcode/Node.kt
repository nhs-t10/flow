package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by shaash on 10/7/17.
 */

abstract class Node(val nodeName : String) : Thread() {

    open fun subscribe(channel: String, callback: (Message) -> Unit) {
        Dispatcher.subscribe(channel, callback)
    }
    open fun publish(channel: String, message: Message) {
        Dispatcher.publish(channel, message)
    }
    abstract fun subscriptions()

    open fun endNode() {}

}
