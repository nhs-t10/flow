package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.RobotState
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.AnalogMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.util.TeamColor
import org.firstinspires.ftc.teamcode.messages.OmniDrive
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by dvw06 on 1/16/18.
 */

class StopAtCryptoboxRoutine(val teamColor: TeamColor, val vumark: RelicRecoveryVuMark) : RoutineNode("Drive to Cryptobox") {
    val distanceThreshold = 0.05
    override fun start() {
        this.publish("/drive", OmniDrive(upDown = 0.3f, rotation = 0f, leftRight = 0f, priority = 1))

    }

    override fun subscriptions() {
        subscribe("/analog/ultra1", {
            if(vumark==RelicRecoveryVuMark.LEFT){stopIfThere(it, 3)}
            else if(vumark==RelicRecoveryVuMark.CENTER){stopIfThere(it, 2)}
            else if(vumark==RelicRecoveryVuMark.RIGHT){stopIfThere(it, 1)}

        })
    }
    fun stopIfThere(m: Message, flanges: Int){

        val (value) = m as AnalogMsg
        publish("/status", TextMsg("distance: $value"))
        if(value < distanceThreshold){
            this.publish("/status", TextMsg("Cryptobox Detected!"))
            this.publish("/drive", OmniDrive(upDown = 0.0f, rotation = 0f, leftRight = 0f, priority = 1))
            end()
        }
    }
}
