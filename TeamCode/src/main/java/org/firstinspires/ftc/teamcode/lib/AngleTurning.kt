package org.firstinspires.ftc.teamcode.lib
import org.firstinspires.ftc.teamcode.lib.PID
import java.lang.Math.abs

/**
 * Created by shaash on 11/18/17.
 */

class AngleTurning(val kP : Double, val kI : Double, val kD : Double, val destination: Double){
    val turn = PID(kP, kI, kD, destination)
    fun computeTurnMotorVals(current : Double):List<Double>{
        val rotationalMultiplier = arrayOf<Double>(1.0, -1.0, 1.0, -1.0)
        var angleToTurn = turn.computePID(current)/180.0 //converts angle to motor vals
        // just in case, but angle to turn should never be > 1.
        // Don't want to break the motors while testing (can take out later):
        if(Math.abs(angleToTurn) > 1){
            angleToTurn = 1 * Math.signum(angleToTurn)
        }

        val motorvals = rotationalMultiplier.map { it * angleToTurn }
        return motorvals
    }
}
