package org.firstinspires.ftc.teamcode.messages

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark

/**
 * Created by max on 8/31/17.
 */

data class UltrasonicDistance(val distance: Double, override val priority: Int) : Message
data class LightSensor(val lightDetected: Double, override val priority: Int) : Message
data class ColorMsg(val red: Int, val blue : Int, val green : Int, override val priority: Int) : Message
data class VuforiaMsg(val mark: RelicRecoveryVuMark, val x: Double?, val y : Double?, val z : Double?, override val priority: Int) : Message
data class ImuMsg(val heading: Double, val roll: Double, val pitch: Double, override val priority: Int) : Message
data class DigitalMsg(val value: Boolean, override val priority: Int) : Message
data class AnalogMsg(val value : Double, override val priority: Int) : Message