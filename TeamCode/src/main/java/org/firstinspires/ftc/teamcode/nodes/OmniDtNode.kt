package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeat
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.messages.motor
import java.lang.Math.abs

/**
 * Created by shaash on 10/17/17.
 */
class OmniDtNode : Node{

    constructor(){
        Dispatcher.subscribe("/drive", {this.recieveMessage(it)})
    }
    fun recieveMessage(driveCommands : Message){
        val (upDown, leftRight, rotation) = driveCommands as OmniDrive
        val forwardMultiplier = arrayOf(1f, 1f,
                1f, 1f)
        val leftRightMultiplier = arrayOf(1f, -1f,
                -1f, 1f)
        val rotationalMultiplier = arrayOf(1f, -1f,
                1f, -1f)
        val forwardsComponent = forwardMultiplier.map { it*upDown}
        val eastWestComponent = leftRightMultiplier.map { it*leftRight}
        val rotationalComponent = rotationalMultiplier.map { it*rotation}

        val motorvals = drive(forwardsComponent, eastWestComponent, rotationalComponent).map{it as Double}
        val priority = 2
        Dispatcher.publish("/motors/m1", motor(motorvals[0], priority = priority))
        Dispatcher.publish("/motors/m2", motor(motorvals[1], priority = priority))
        Dispatcher.publish("/motors/m3", motor(motorvals[2], priority = priority))
        Dispatcher.publish("/motors/m4", motor(motorvals[3], priority = priority))
    }
    fun drive(forwardback : List<Float>, leftright : List<Float>, rotation : List<Float>):List<Float>{
        val sumlf = forwardback[0] + leftright[0] + rotation[0]
        val sumrf = forwardback[1] + leftright[1] + rotation[1]
        val sumlr = forwardback[2] + leftright[2] + rotation[2]
        val sumrr = forwardback[3] + leftright[3] + rotation[3]
        val sums = arrayOf(sumlf, sumrf, sumlr, sumrr)
        val findhighestinthis = sums.map{abs(it)}
        val highestsum:Float? = findhighestinthis.max()
        var attenuationfactor = 1f
        if (highestsum == null){
            attenuationfactor = 1f
        }

        else if (abs(highestsum) > 1f) {
            attenuationfactor = highestsum
        } else {
            attenuationfactor = 1f
        }

        val finalvals = sums.map { it / attenuationfactor }
        return finalvals
    }

}
