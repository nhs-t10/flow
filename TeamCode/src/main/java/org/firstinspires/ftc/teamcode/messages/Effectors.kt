package org.firstinspires.ftc.teamcode.messages

/**
 * Created by shaash on 10/17/17.
 */
data class MotorMsg(val power: Double, override val priority: Int) : Message
data class ServoMsg(val position: Double, override val priority: Int) : Message
