package org.firstinspires.ftc.teamcode.messages

/**
 * Created by shaash on 10/24/17.
 */
data class DebugMsg(val print : String, override val priority : Int = 1) : Message
data class WarningMsg(val print : String, override val priority : Int = 0) : Message