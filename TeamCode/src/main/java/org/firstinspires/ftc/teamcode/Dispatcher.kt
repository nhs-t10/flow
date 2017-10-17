package org.firstinspires.ftc.teamcode
import org.firstinspires.ftc.teamcode.messages.Message
import java.util.*

/**
 * Created by shaash on 10/7/17.
 */
object Dispatcher{

    var channels = HashMap<String, MutableList<(Message) -> Unit>>()

    fun publish(channel: String, message: Message){
        val listeners = channels.get(channel)
        val iterator = listeners?.listIterator()
        if(iterator != null){
            while(iterator.hasNext()){
                val callback = iterator.next()
                callback(message)
            }
        }

    }

    fun subscribe(channel:String, callback: (Message) -> Unit) {
        val currentListeners = channels.get(channel) ?: mutableListOf<(Message) -> Unit>()
        currentListeners.add(callback)
        channels.put(channel, currentListeners)
    }
}
