package org.firstinspires.ftc.teamcode.lib

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by shaash on 11/12/17.
 */

class PID(val kP : Double, val kI : Double, val kD : Double) {

    private var preverror = 0.0
    private var starttime = 0.0
    private var sumerror = 0.0
    private var prevtime = 0.0
    fun init(){
        prevtime = getElapsedTime()
    }

    fun computePID(error : Double) : Double{
        val currenttime = getCurrentTime()
        val elapsedtime = currenttime - prevtime
        val p = kP * error
        val d = -Math.signum(error) * Math.abs(kD * ((error - preverror)/elapsedtime))
        sumerror += (error*elapsedtime)/1000
        val i = kI * sumerror
        preverror = error
        prevtime = currenttime
        if(((1.5 > error && error > 0 && error < preverror) || (-1.5 < error && error < 0 && error > preverror)) && Math.abs(d) < .05){
            return 0.0
        }
        return p+i+d
    }

    private fun getElapsedTime() : Double{
        return (System.currentTimeMillis() - starttime)
    }
    private fun getCurrentTime() : Double{
        return System.currentTimeMillis().toDouble()
    }

}