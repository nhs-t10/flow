package org.firstinspires.ftc.teamcode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg
import java.util.*

/**
 * Created by shaash on 10/7/17.
 */
object Dispatcher {

    val channels = HashMap<String, Pair<Int?, MutableList<(Message) -> Unit>>>()

    var telemetry : Telemetry? = null

    fun setChannel(channel: String,
                   content: MutableList<(Message) -> Unit> = mutableListOf<(Message) -> Unit>(),
                   priority : Int? = null) {
        channels.put(channel, Pair(priority, content))
    }

    /**
     * TODO: currently this creates a computational graph with a trie that propagates into further mutations and calls.
     * This could lead to concurrency issues.
     */
    fun publish(channel: String, message: Message){
        val found = channels[channel]
        if (found != null) {
            val (priority, listeners) = found
            if (priority == null || priority >= message.priority) {
                val locked = listeners.toTypedArray()
                var x = 0
                for (callback in locked) {
                    try {
                        callback(message)
                    }
                    catch(e:Exception) {
                        if (channel != "/error" && channel != "/telemetry/line") { // infinite recursion proof
                            this.publish("/error", TextMsg("$message sent to $channel caused $e", 0))
                        }
                    }
                    x++
                }
            }
            // otherwise, ignore message
        }
        else { // this ensures a one-time warning. slightly hacky, but shouldn't cause breaking bugs
            if (channel != "/warn") { // infinite recursion proof
                this.publish("/warn", TextMsg("Nobody heard a message sent to $channel."))
                setChannel(channel)
            }
        }
    }

    /**
     * Lets a node subscribe to a channel.
     * @param channel The channel name e,g "/motors/m1"
     * @param callback The callback lambda, called whenever a message is received. Syntax: `{myCallback(it)}`
     */
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
            this.publish("/warn", TextMsg("Nobody's listening to $channel, so it wasn't locked."))
        }
    }

    fun unlock(channel: String) {
        val found = channels[channel]
        if (found != null) {
            setChannel(channel, found.second, null)
        }
        else {
            this.publish("/warn", TextMsg("Nobody's listening to $channel, so it wasn't unlocked."))
        }
    }
    fun reset() {
        channels.clear()
    }

    fun tempSetTelemetry(t: Telemetry) {
        telemetry = t
    }
}
