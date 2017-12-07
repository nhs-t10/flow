package org.firstinspires.ftc.teamcode.messages

/**
 * Created by max on 11/16/17.
 */

enum class GripperState {
    OPEN,
    MIDDLE,
    CLOSED,
}
data class GripperMsg(val state: GripperState, override val priority : Int) : Message