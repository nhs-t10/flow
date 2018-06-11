package org.firstinspires.ftc.teamcode.old.messages

/**
 * Created by dvw06 on 10/12/17.
 */
data class GamepadButtonMsg(val value: Boolean, override val priority: Int) : MessageOld
data class GamepadJoyOrTrigMsg(val value: Float, override val priority: Int) : MessageOld

data class UpMessageOld(val isPressed: Boolean, override val priority: Int) : MessageOld
data class DownMessageOld(val isPressed: Boolean, override val priority: Int) : MessageOld