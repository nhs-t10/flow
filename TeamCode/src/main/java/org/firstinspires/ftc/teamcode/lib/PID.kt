package org.firstinspires.ftc.teamcode.lib

/**
 * Created by shaash on 11/12/17.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

class PID(val kP : Double, val kI : Double, val kD : Double, val destination: Double) {

    private var error = 0.0
    private var preverror = 0.0
    private var starttime = 0.0
    private var sumerror = 0.0
    private var prevtime = 0.0
    fun init(){
        prevtime = getElapsedTime()
    }

    fun computePID(curr : Double) : Double{
        val currenttime = getCurrentTime()
        val elapsedtime = currenttime - prevtime
        error = destination - curr
        val p = kP * error
        val d = -Math.signum(error) * Math.abs(kD * ((error - preverror)/elapsedtime))
        sumerror += (error*elapsedtime)/1000
        val i = kI * sumerror
        preverror = error
        prevtime = currenttime
        return p+i+d
    }

    private fun getElapsedTime() : Double{
        return (System.currentTimeMillis() - starttime)
    }
    private fun getCurrentTime() : Double{
        return System.currentTimeMillis().toDouble()
    }

}