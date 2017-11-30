package org.firstinspires.ftc.teamcode.lib
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.lib.PID
import org.firstinspires.ftc.teamcode.messages.TextMsg
import java.lang.Math.abs

/**
 * Created by shaash on 11/18/17.
 */

class AngleTurning(val destination: Double){
    val kP = 2.0
    val kD = 0.0
    val kI = 0.0
    val turn = PID(kP, kI, kD, destination)
    fun computeHeading(current : Double):Double{
        var angleToTurn = turn.computePID(current)/180.0 //converts angle to motor vals
        // just in case, but angle to turn should never be > 1.
        // Don't want to break the motors while testing (can take out later):
        if(Math.abs(angleToTurn) > 1){
            angleToTurn = 1 * Math.signum(angleToTurn)
        }
        Dispatcher.publish("/debug", TextMsg(text = "angle to turn: $angleToTurn", priority = -7))
        return angleToTurn
    }
}
