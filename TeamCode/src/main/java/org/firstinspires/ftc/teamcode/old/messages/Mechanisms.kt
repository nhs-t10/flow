package org.firstinspires.ftc.teamcode.old.messages

/**
 * Created by max on 11/16/17.
 */

enum class GripperState {
    OPEN,
    MIDDLE,
    CLOSED,
}


data class GripperMsg(val state: GripperState, override val priority : Int) : Message

data class HuggerMsg(val closeIt: Boolean, override val priority: Int) : Message


enum class LiftState {
    TOP,
    MIDDLE,
    UPPER_BOTTOM,
    BOTTOM
}

data class LiftMsg(val state: LiftState, override val priority: Int) : Message
