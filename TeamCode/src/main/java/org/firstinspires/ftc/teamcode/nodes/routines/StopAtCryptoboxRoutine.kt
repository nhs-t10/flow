package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by dvw06 on 1/16/18.
 */

class StopAtCryptoboxRoutine(val vumark: RelicRecoveryVuMark) : RoutineNode("Drive to Cryptobox"){
    var counter = 0
    override fun begin() {
        this.publish("/drive", OmniDrive(upDown = 0.3f, rotation = 0f, leftRight = 0f, priority = 1))
    }

    override fun subscriptions() {
        subscribe("/digital/touch1", {
            if(vumark==RelicRecoveryVuMark.LEFT){stopIfThere(it, 3)}
            else if(vumark==RelicRecoveryVuMark.CENTER){stopIfThere(it, 2)}
            else if(vumark==RelicRecoveryVuMark.RIGHT){stopIfThere(it, 1)}
        })
    }

    fun stopIfThere(m: Message, flanges: Int){
        val (value) = m as DigitalMsg
        if((counter<flanges) && value) {
            counter++
            publish("/status", TextMsg("flanges detected: $counter"))
        } else {
            this.publish("/drive", OmniDrive(upDown = 0.0f, rotation = 0f, leftRight = 0f, priority = 1))
            end()
        }
    }
}
