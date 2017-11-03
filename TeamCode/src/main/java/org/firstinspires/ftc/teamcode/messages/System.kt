package org.firstinspires.ftc.teamcode.messages
/**
 * Created by davis on 10/10/17.
 */
data class HeartBeatMsg(val time: Long, override val priority: Int) : Message
