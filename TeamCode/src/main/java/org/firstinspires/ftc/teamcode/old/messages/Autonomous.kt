package org.firstinspires.ftc.teamcode.old.messages

/**
 * Created by shaash on 11/21/17.
 */
data class AngleTurnMsg(val angle: Double, val callback : () -> Unit, override val priority: Int) : MessageOld //motorVals go lf, rf, lb, rb
data class KnockBallMsg(val callback : () -> Unit, override val priority: Int) : MessageOld

//
//enum class SpeedyMessage {
//    motor_1,
//    motor_2
//}
//
//data class SpeedyPoop(val speedy : Double, val channel: SpeedyMessage)
