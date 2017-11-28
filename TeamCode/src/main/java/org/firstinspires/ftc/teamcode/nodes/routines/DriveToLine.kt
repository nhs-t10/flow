package org.firstinspires.ftc.teamcode.nodes.routines

import org.firstinspires.ftc.teamcode.RoutineNode
import org.firstinspires.ftc.teamcode.messages.ColorMsg
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.OmniDrive

/**
 * Created by dvw08 on 11/28/17.
 */
class DriveToLine : RoutineNode("Drive to line") {
    override fun start() {



    }




    override fun subscriptions() {
        this.subscribe("/color/colorOne",{this.checkColor(it as ColorMsg)} )
    }
    fun checkColor(m:ColorMsg){
        val (Red,Green,Blue)=m

        if(Red>=200 && Green<55 && Blue<55){
            this.publish("/drive",OmniDrive(0f,0f,0f,1))
        } else {
            this.publish("/drive",OmniDrive(1f,0f,0f,1))
        }



    }
}