package org.firstinspires.ftc.teamcode.old.nodes.mechanisms

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.old.Node
import org.firstinspires.ftc.teamcode.old.messages.*
import org.firstinspires.ftc.teamcode.old.messages.IncrementState

class GliftNode : Node("Glyph Lift") {
    override fun subscriptions() {
        this.subscribe("/glift/goUp",  { this.goUp(it) })
        this.subscribe("/glift/goDown", { this.goDown(it) })
    }
    fun goUp(m: Message){
//        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/glift", m)
    }

    fun goDown(m:Message){
//        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/glift", m)
    }
}
