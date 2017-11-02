package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.servo

/**
 * Created by shaash on 10/26/17.
 */

class ServoNode : Node {
    var position = .2

    constructor() {
        Dispatcher.subscribe("/gamepad1/right_bumper", { open() })
        Dispatcher.subscribe("/gamepad1/left_bumper", { close() })
    }

    fun open() {
        position = 0.2
        Dispatcher.publish("/servos/s0", servo(position, priority = 1))
    }

    fun close() {
        position = 0.0
        Dispatcher.publish("/servos/s0", servo(position, priority = 1))
    }
}