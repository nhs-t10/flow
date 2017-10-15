package org.firstinspires.ftc.teamcode.messages

/**
 * Created by dvw06 on 10/12/17.
 */
data class gamepadButtonMsg(val value: Boolean, override val priority: Int) : Message
data class gamepadJoyOrTrigMsg(val value: Float, override val priority: Int) : Message
