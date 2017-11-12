package org.firstinspires.ftc.teamcode.util

import org.firstinspires.ftc.teamcode.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.Message

/**
 * Created by max on 11/11/17.
 */

/**
 * Higher order function which calls a callback when given a gamepad button that's DOWN.
 */
fun whenDown(callback: () -> Unit) : (msg: Message) -> Unit {
    return fun(msg: Message) {
        val (value) = msg as GamepadButtonMsg
        if (value) {
            callback()
        }
    }
}