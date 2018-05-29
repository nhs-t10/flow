package org.firstinspires.ftc.teamcode.old

import org.firstinspires.ftc.teamcode.old.Nodeable
import org.firstinspires.ftc.teamcode.old.messages.Message

/**
 * Created by shaash on 10/7/17.
 */

abstract class NodeOld(override val nodeName : String) : Nodeable {

    override fun subscribe(channel: String, callback: (Message) -> Unit) : String {
        return Dispatcher.subscribe(channel, nodeName, callback)
    }
    override fun publish(channel: String, message: Message) {
        Dispatcher.publish(channel, message)
    }

    override fun endNode() {}

    override fun start() {}

}
