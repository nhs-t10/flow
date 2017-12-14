package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.util.TeamColor
import org.firstinspires.ftc.teamcode.messages.OmniDrive
/**
 * Created by dvw04 on 12/12/17.
 */
class DriveToLineRoutine(val teamColor: TeamColor) : RoutineNode("Drive to Line") {
    var tempRed = 0
    var tempBlue = 0
    var tempGreen = 0
    override fun start() {
        this.publish("/drive", OmniDrive(upDown = .2f, leftRight = 0f, rotation = 0f, priority = 1))
    }

    override fun subscriptions() {
        subscribe("/color/bottom", {getOffBlockRoutine(it)})
    }
    fun getOffBlockRoutine(m : Message){
        TimedCallbackRoutine({}, 750, {cb ->
            receiveColor(m)
            cb()
        })
    }

    fun receiveColor(m: Message){
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