package org.firstinspires.ftc.teamcode.messages

import java.util.*

/**
 * Created by davis on 10/10/17.
 */

/**
 * An opmode loop-congruent "tick" that allows for fixed refresh rates.
 */
data class HeartBeatMsg(val time: Long, override val priority: Int) : Message

/**
 * Generic text transport message.
 */
data class TextMsg(val text : String, override val priority : Int = 1) : Message

/**
 * Generic lined text transport message.
 */
data class LinesMsg(val lines : List<String>, override val priority: Int) : Message

/**
 * Empty msg for signals.
 */
data class UnitMsg(override val priority: Int = 1) : Message

/**
 * Map of callbacks.
 */
data class CallbackMapMsg(val map: HashMap<String, () -> Unit>, override val priority : Int) : Message

/**
 * Intent to transform value a -> b
 */
data class UpdateMsg<T>(val a : T, val b : T, override val priority: Int = 1) : Message

enum class IncrementState {ZERO, HOLD, INCREMENT}

data class IncrementMsg(val state: IncrementState, val increment : Double = 0.0, override val priority: Int = 1) : Message