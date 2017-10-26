package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.servo

/**
 * Created by shaash on 10/26/17.
 */
var position = .2
var isOpen = true
class ServoNode : Node{
    constructor(){
        Dispatcher.subscribe("/gamepad1/right_trigger", {recieveMessage(it as Boolean)})
    }
}
fun recieveMessage(rightbumper : Boolean){
    if(isOpen){
        position = 0.2
        isOpen = false
    } else {
        position = 0.0
        isOpen = true

    }
    Dispatcher.publish("servos/s0", servo(position, priority=1))
}