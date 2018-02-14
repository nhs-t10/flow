package org.firstinspires.ftc.teamcode.lib

/**
 * Created by shaash on 11/12/17.
 */

class PID(val kP : Double, val kI : Double, val kD : Double) {

    private var preverror = 0.0
    private var starttime = 0.0
    private var integratetime = 0.0
    private var sumerror = 0.0//shaash
    private var prevtime = 0.0
    private var integratedError = 0.0;
    fun init(){
        prevtime = getElapsedTimeFromStart()
    }

    fun computePID(error : Double) : Double{
        val currenttime = getCurrentTime()
        val elapsedtime = currenttime - prevtime
        val p = kP * error
        val d = -Math.signum(error) * Math.abs(kD * ((error - preverror)/elapsedtime))
        sumerror += (error*elapsedtime)/1000
        val i = (kI/1000) * sumerror
        preverror = error
        prevtime = currenttime
        if(((4.0 > error && error > 0.0) || (-4.0 < error && error < 0.0)) && (Math.round(error) == Math.round(preverror))){
            integratedError+=error
            if(integratedError>100){
                return 0.0
            }
        }
        return p+i+d
    }

    private fun getElapsedTimeFromStart() : Double{
        return (System.currentTimeMillis() - starttime)
    }
    private fun getCurrentTime() : Double{
        return System.currentTimeMillis().toDouble()
    }
}
