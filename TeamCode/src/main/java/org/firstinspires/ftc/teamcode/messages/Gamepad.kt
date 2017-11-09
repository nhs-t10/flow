package org.firstinspires.ftc.teamcode.messages

/**
 * Created by dvw06 on 10/12/17.
 */
data class GamepadButtonMsg(val value: Boolean, override val priority: Int) : Message
data class GamepadJoyOrTrigMsg(val value: Float, override val priority: Int) : Message

data class UpMessage(val isPressed: Boolean, override val priority: Int) : Message
data class DownMessage(val isPressed: Boolean, override val priority: Int) : Message