package org.firstinspires.ftc.teamcode.old.messages

/**
 * Created by max on 8/31/17.
 */
data class OmniDrive(val upDown : Float, val leftRight : Float, val rotation : Float, override val priority: Int) : MessageOld //motorVals go lf, rf, lb, rb
data class SpeedMsg(val speed : Boolean, override val priority: Int) : MessageOld
data class DriveStraightMsg(val angle : Double, val power: Double, val sideways : Boolean = false, override val priority: Int) : MessageOld