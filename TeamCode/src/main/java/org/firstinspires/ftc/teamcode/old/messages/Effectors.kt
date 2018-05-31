package org.firstinspires.ftc.teamcode.old.messages

/**
 * Created by shaash on 10/17/17.
 */
data class MotorMsg(val power: Double, override val priority: Int) : MessageOld
data class ServoMsg(val position: Double, override val priority: Int) : MessageOld
