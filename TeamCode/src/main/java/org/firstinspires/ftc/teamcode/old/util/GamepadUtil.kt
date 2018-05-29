package org.firstinspires.ftc.teamcode.old.util

import org.firstinspires.ftc.teamcode.old.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.old.messages.GamepadJoyOrTrigMsg
import org.firstinspires.ftc.teamcode.old.messages.Message

/**
 * Created by max on 11/11/17.
 */

/**
 * Higher order function which calls a callback when given a gamepad button or trigger that's DOWN.
 */
fun whenDown(callback: () -> Unit) : (msg: Message) -> Unit {
    return fun(msg: Message) {
        val value = when(msg) {
            is GamepadButtonMsg -> msg.value
            is GamepadJoyOrTrigMsg -> msg.value > 0.5
            else -> false
        }
        if (value) callback()
    }
}
