package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.RoutineGroup
import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by dvw06 on 1/16/18.
 */

class CountFlangesRoutine(val vumark: RelicRecoveryVuMark) : RoutineNode("Drive to Cryptobox"){
    var counter = 0
    override fun begin() {
        this.publish("/drive/straight", DriveStraightMsg(-90.0, 0.3, true, 1))
    }

    override fun subscriptions() {
        subscribe("/digital/left_contact", {
            if(vumark==RelicRecoveryVuMark.LEFT){stopIfThere(it, 3)}
            else if(vumark==RelicRecoveryVuMark.CENTER){stopIfThere(it, 2)}
            else if(vumark==RelicRecoveryVuMark.RIGHT){stopIfThere(it, 1)}
        })
    }

    fun skrtalil() : RoutineGroup = RoutineGroup(listOf(
            TimeoutRoutine({
                publish("/servos/wood", ServoMsg(0.5, 1))
            }, 400),
            TimeoutRoutine({
                this.publish("/drive/straight", DriveStraightMsg(-90.0, 0.3, true, 1))
            }, 600),
            TimeoutRoutine({
                this.publish("/servos/wood", ServoMsg(0.0, 1))
            }, 200)

    ))

    fun stopIfThere(m: Message, flanges: Int){
        val (value) = m as DigitalMsg
        if(value){
            this.publish("/drive/straight", DriveStraightMsg(-90.0, 0.0, false, 1))
            if((counter<flanges) && value) {
                skrtalil().beginRoutine {
                    counter++
                    this.publish("/drive/straight", DriveStraightMsg(-90.0, 0.3, true, 1))
                    publish("/status", TextMsg("flanges detected: $counter"))
                }
            } else {
                end()
            }
        }

    }
}
