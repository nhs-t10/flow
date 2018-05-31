package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.Gamepad

sealed class Message

object HeartbeatMsg : Message()

class ContinuousGamepadMsg (val value: Gamepad) : Message()

