package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.Gamepad

interface Message

object HeartbeatMsg : Message

class ContinuousGamepadMsg (val value: Gamepad) : Message

class MoveMotorMsg(val on: Boolean) : Message

open class TextMsg (val value: String) : Message
class DebugMsg (value: String) : TextMsg(value)
class StatusMsg (value: String) : TextMsg(value)
class ErrorMsg (value: String) : TextMsg(value)

object ClearMsg : Message