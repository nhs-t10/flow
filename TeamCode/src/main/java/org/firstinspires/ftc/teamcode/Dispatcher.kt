package org.firstinspires.ftc.teamcode
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.WarningMsg
import java.util.*

/**
 * Created by shaash on 10/7/17.
 */
object Dispatcher {

    val channels = HashMap<String, Pair<Int?, MutableList<(Message) -> Unit>>>()

    fun setChannel(channel: String,
                   content: MutableList<(Message) -> Unit> = mutableListOf<(Message) -> Unit>(),
                   priority : Int? = null) {
        channels.put(channel, Pair(priority, content))
    }

    fun publish(channel: String, message: Message){
        val found = channels[channel]
        if (found != null) {
            val (priority, listeners) = found
            if (priority == null || priority >= message.priority) {
                val iterator = listeners?.listIterator()
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        val callback = iterator.next()
                        callback(message)
                    }
                }
            }
            // otherwise, ignore message
        }
        else { // this ensures a one-time warning. slightly hacky, but shouldn't cause breaking bugs
            this.publish("/warn", WarningMsg("Nobody heard a message sent to $channel."))
            setChannel(channel)
        }
    }

    fun subscribe(channel:String, callback: (Message) -> Unit) {
        val found = channels[channel]
        if (found != null) {
            val (priority, currentListeners) = found
            currentListeners.add(callback)
            setChannel(channel=channel, content=currentListeners, priority=priority)
        }
        else {
            setChannel(channel=channel, content=mutableListOf(callback))
        }
    }

    fun lock(channel: String, priority: Int) {
        val found = channels[channel]
        if (found != null) {
            setChannel(channel, found.second, priority)
        }
        else {
            this.publish("/warn", WarningMsg("Nobody's listening to $channel, so it wasn't locked."))
        }
    }

    fun unlock(channel: String) {
        val found = channels[channel]
        if (found != null) {
            setChannel(channel, found.second, null)
        }
        else {
            this.publish("/warn", WarningMsg("Nobody's listening to $channel, so it wasn't unlocked."))
        }
    }
}
