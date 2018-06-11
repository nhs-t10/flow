package org.firstinspires.ftc.teamcode.old.nodes.mechanisms

import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.*
import java.lang.Math.abs

/**
 * Created by shaash on 10/17/17.
 */
class OmniDtNodeOld : NodeOld("Omni Drivetrain"){
    var isSlow: Boolean = true
    var speedscale = 0.5f
    override fun subscriptions() {
        this.subscribe("/drive", {this.recieveMessage(driveCommands = it)})
        this.subscribe("/drive/fast", {slow(it)})
        //this.subscribe("/gamepad1/x", whenDown { slowModeToggle() })
    }

    fun slow(m : MessageOld){
        val (speed) = m as SpeedMsg
        isSlow = !speed
    }
    /*
    fun slowModeToggle (){
        this.isSlow = !this.isSlow
        this.publish("/debug", TextMsg("SLOW MODE: ${this.isSlow}"))
    }
    */
    fun recieveMessage(driveCommands : MessageOld, slow : Boolean = isSlow){
        if(slow){
           speedscale = 0.5f
        } else {
            speedscale = 1.0f
        }
        val (upDown, leftRight, rotation) = driveCommands as OmniDrive
        val forwardMultiplier = arrayOf(-1f, -1f,
                -1f, -1f)
        val leftRightMultiplier = arrayOf(1f, -1f,
                -1f, 1f)
        val rotationalMultiplier = arrayOf(-1f, 1f,
                -1f, 1f)
        // Made forward negative because it wasn't working for some reason.
        var forwardsComponent = forwardMultiplier.map {it * -upDown}
        var eastWestComponent = leftRightMultiplier.map { it * leftRight}
        var rotationalComponent = rotationalMultiplier.map { it * rotation}
        forwardsComponent = forwardsComponent.map { it * speedscale}
        eastWestComponent = eastWestComponent.map { it * speedscale}
        rotationalComponent = rotationalComponent.map { it * speedscale }

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
