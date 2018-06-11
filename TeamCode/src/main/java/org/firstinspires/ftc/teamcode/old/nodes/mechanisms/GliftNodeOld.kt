package org.firstinspires.ftc.teamcode.old.nodes.mechanisms

/**
 * Created by shaash on 10/15/17.
 */

import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.*

class GliftNodeOld : NodeOld("Glyph Lift") {
    override fun subscriptions() {
        this.subscribe("/glift/goUp",  { this.goUp(it) })
        this.subscribe("/glift/goDown", { this.goDown(it) })
    }
    fun goUp(m: MessageOld){
//        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/glift", m)
    }

    fun goDown(m:MessageOld){
//        val (value) = m as GamepadJoyOrTrigMsg
        publish("/motors/glift", m)
    }
}
