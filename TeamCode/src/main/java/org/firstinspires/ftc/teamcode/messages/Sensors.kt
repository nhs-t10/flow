package org.firstinspires.ftc.teamcode.messages

/**
 * Created by max on 8/31/17.
 */

data class UltrasonicDistance(val distance: Double, override val priority: Int) : Message
data class LightSensor(val lightDetected: Double, override val priority: Int) : Message
data class colorMsg(val red: Int, val blue : Int, val green : Int, override  val priority: Int) : Message
