package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import java.lang.Math.abs
import  org.firstinspires.ftc.teamcode.util.whenDown

/**
 * Created by shaash on 10/17/17.
 */
class OmniDtNode : Node("Omni Drivetrain"){
    var isSlow: Boolean = true

    override fun subscriptions() {
        this.subscribe("/drive", {this.recieveMessage(driveCommands = it)})
        this.subscribe("/gamepad1/x", whenDown { slowModeToggle() })
    }
    fun slowModeToggle (){
        this.isSlow = !this.isSlow
        this.publish("/debug", TextMsg("SLOW MODE: ${this.isSlow}"))
    }
    fun recieveMessage(driveCommands : Message){

        val (upDown, leftRight, rotation) = driveCommands as OmniDrive
        val forwardMultiplier = arrayOf(-1f, -1f,
                -1f, -1f)
        val leftRightMultiplier = arrayOf(1f, -1f,
                -1f, 1f)
        val rotationalMultiplier = arrayOf(-1f, 1f,
                -1f, 1f)
        var forwardsComponent = forwardMultiplier.map { it*upDown}
        var eastWestComponent = leftRightMultiplier.map { it*leftRight}
        var rotationalComponent = rotationalMultiplier.map { it*rotation}
        if(this.isSlow){
            forwardsComponent = forwardsComponent.map { it * 0.5f}
            eastWestComponent = eastWestComponent.map { it * 0.5f}
            rotationalComponent = rotationalComponent.map { it * 0.5f}
        }
        val motorvals = drive(forwardsComponent, eastWestComponent, rotationalComponent).map{it.toDouble()}
        val priority = 1
        this.publish("/motors/lr", MotorMsg(motorvals[0], priority = priority))
        this.publish("/motors/rr", MotorMsg(motorvals[1], priority = priority))
        this.publish("/motors/lf", MotorMsg(motorvals[2], priority = priority))
        this.publish("/motors/rf", MotorMsg(motorvals[3], priority = priority))
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
