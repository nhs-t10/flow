package org.firstinspires.ftc.teamcode.messages

/**
 * Created by max on 8/31/17.
 */
data class HoloDrive(val angle: Double, val magnitude: Double, override val priority: Int) : Message
data class OmniDrive(val motorVals : List<Float>, override val priority: Int) : Message //motorVals go lf, rf, lb, rb
