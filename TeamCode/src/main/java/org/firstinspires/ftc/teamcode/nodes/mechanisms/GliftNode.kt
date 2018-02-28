package org.firstinspires.ftc.teamcode.nodes.mechanisms

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.messages.IncrementState

class GliftNode : Node("Glyph Lift") {
    override fun subscriptions() {
        this.subscribe("/glift/goUp",  { this.goUp(it) })
        this.subscribe("/glift/goDown", { this.goDown(it) })
    }
    fun goUp(m: Message){
        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/glift", MotorMsg(power = (value.toDouble()), priority = 1))
    }

    fun goDown(m:Message){
        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/glift", MotorMsg(power = (value.toDouble()*-1), priority = 1))
    }
}
