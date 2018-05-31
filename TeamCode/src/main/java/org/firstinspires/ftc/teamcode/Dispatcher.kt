package org.firstinspires.ftc.teamcode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * Created by shaash on 10/7/17.
 */
object Dispatcher {

    val channels = HashMap<String, Pair<Int?, MutableList<Pair<String, (Message) -> Unit>>>>()

    var telemetry : Telemetry? = null

    fun setChannel(channel: String,
                   content: MutableList<Pair<String, (Message) -> Unit>> = mutableListOf(),
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
                for ((name, callback) in locked) {
                    try {
                        val timeTaken = measureTimeMillis {
                            callback(message)
                        }
                        if (channel != "/error" && channel != "/telemetry/line" && channel != "/status") {
                            this.publish("/status", TextMsg("Took $timeTaken to publish $name via $channel"))
                        }
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
    fun subscribe(channel:String, nodeName: String, callback: (Message) -> Unit) : String {
        val found = channels[channel]
        val uuid = nodeName + UUID.randomUUID().toString()
        if (found != null) {
            val (priority, currentListeners) = found
            currentListeners.add(Pair(uuid, callback))
            setChannel(channel=channel, content=currentListeners, priority=priority)
        }
        else {
            setChannel(channel=channel, content=mutableListOf(Pair(uuid, callback)))
        }
        return uuid
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
    fun unsubscribe(channel: String, id: String) {
        val found = channels[channel]
        if (found != null) {
            val filtered = found.second.filter { (name, _) -> name != id }
            setChannel(channel, found.second, found.first)
        }
        else {
            this.publish("/warn", TextMsg("Couldn't unsubscribe from nonexistent channel $channel."))
        }
    }
    fun reset() {
        channels.clear()
    }

    fun tempSetTelemetry(t: Telemetry) {
        telemetry = t
    }
}
