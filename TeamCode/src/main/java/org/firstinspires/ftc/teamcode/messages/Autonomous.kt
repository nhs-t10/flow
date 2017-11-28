package org.firstinspires.ftc.teamcode.messages

/**
 * Created by shaash on 11/21/17.
 */
data class AngleTurnMsg(val angle: Double, val callback : () -> Unit, override val priority: Int) : Message //motorVals go lf, rf, lb, rb
