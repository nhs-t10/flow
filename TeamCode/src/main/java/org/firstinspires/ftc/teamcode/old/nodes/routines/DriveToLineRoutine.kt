package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.ColorMsg
import org.firstinspires.ftc.teamcode.old.messages.MessageOld
import org.firstinspires.ftc.teamcode.old.util.TeamColor
import org.firstinspires.ftc.teamcode.old.messages.OmniDrive
/**
 * Created by dvw04 on 12/12/17.
 */
class DriveToLineRoutine(val teamColor: TeamColor) : RoutineNode("Drive to Line") {
    var tempRed = 0
    var tempBlue = 0
    var tempGreen = 0
    override fun begin() {
        this.publish("/drive", OmniDrive(upDown = .2f, leftRight = 0f, rotation = 0f, priority = 1))
    }
    override fun subscriptions() {
        subscribe("/color/bottom", {getOffBlockRoutine(it)})
    }
    fun getOffBlockRoutine(m : MessageOld){
        TimedCallbackRoutine({}, 750, {cb ->
            receiveColor(m)
            cb()
        })
    }

    fun receiveColor(m: MessageOld){
        val (red, blue, green) = m as ColorMsg

        if (tempBlue + tempGreen + tempRed != 0) {
            if(teamColor == TeamColor.RED && red - tempRed > 10){
                this.publish("/drive", OmniDrive(upDown = 0f, leftRight = 0f, rotation = 0f, priority = 1))
                end()
            } else if (teamColor == TeamColor.BLUE && blue - tempBlue > 10) {
                this.publish("/drive", OmniDrive(upDown = .2f, leftRight = 0f, rotation = 0f, priority = 1))
                end()
            }
        }

        tempRed = red
        tempBlue = blue
        tempGreen = green
    }

}