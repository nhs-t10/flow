package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.messages.Message
import java.util.*

/**
 * Created by shaash on 9/28/17.
 */
public class Heart{
    val published = hashMapOf<String, Message>()

    fun publish (toPublish:String,message:Message){
        published.put(toPublish, message)
    }
    fun subscribe (toSubscribe:String):Message? {
        return published.get(toSubscribe)
    }
}
