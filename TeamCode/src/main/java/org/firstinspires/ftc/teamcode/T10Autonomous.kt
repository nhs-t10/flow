package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode

/**
 * Created by shaash on 10/26/17.
 */
@Autonomous(name = "autonomous")
class T10Autonomous : OpMode(){
    var routine : RoutineGroup? = null
    override fun init() {
        routine = RoutineGroup(listOf())
    }

    override fun start() {
        routine?.begin {  }
    }

    override fun loop() {

    }
    override fun stop(){
        Dispatcher.reset()
    }
}