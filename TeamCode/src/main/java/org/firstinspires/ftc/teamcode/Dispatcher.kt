package org.firstinspires.ftc.teamcode
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.WarningMsg
import java.util.*

/**
 * Created by shaash on 10/7/17.
 */
object Dispatcher {

    val channels = HashMap<String, Pair<Int?, MutableList<(Message) -> Unit>>>()

    fun publish(channel: String, message: Message){
        val found = channels.get(channel)
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
    }

    fun subscribe(channel:String, callback: (Message) -> Unit) {
        val found = channels.get(channel)
        var currentListeners = mutableListOf<(Message) -> Unit>()
        if (found != null) {
            currentListeners = found.second // the list in the pair of priority, integer
        }
        currentListeners.add(callback)
        channels.put(channel, Pair(null, currentListeners))
    }

    fun lock(channel: String, priority: Int) {
        val found = channels.get(channel)
        if (found != null) {
            val subscribers = found.second
            channels.put(channel, Pair(priority, subscribers))
        }
        else {
            this.publish("/warn", WarningMsg("Nobody's listening to $channel (yet), so it wasn't locked."))
        }
    }

    fun unlock(channel: String) {
        val found = channels.get(channel)
        if (found != null) {
            val subscribers = found.second
            channels.put(channel, Pair(null, subscribers))
        }
        else {
            this.publish("/warn", WarningMsg("Nobody's listening to $channel (yet), so it wasn't unlocked."))
        }
    }
}
