package org.firstinspires.ftc.teamcode.old

import org.firstinspires.ftc.teamcode.old.messages.MessageOld

/**
 * Created by shaash on 10/7/17.
 */

abstract class NodeOld(override val nodeName : String) : Nodeable {

    override fun subscribe(channel: String, callback: (MessageOld) -> Unit) : String {
        return Dispatcher.subscribe(channel, nodeName, callback)
    }
    override fun publish(channel: String, message: MessageOld) {
        Dispatcher.publish(channel, message)
    }

    override fun endNode() {}

    override fun start() {}

}
