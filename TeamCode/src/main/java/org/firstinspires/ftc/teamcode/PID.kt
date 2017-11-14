package org.firstinspires.ftc.teamcode

/**
 * Created by shaash on 11/12/17.
 */


class PID {

    var p = 0.0
    var i = 0.0
    var d = 0.0
    var destination = 0.0
    var error = 0.0
    var current = 0.0
    fun setConstants(kP : Double, kI : Double , kD : Double){
        p = kP
        i = kI
        d = kD
    }
    fun setDest(dest : Double){
        destination = dest
    }
    fun setCurr(curr : Double){
        current = curr
    }
    fun calculatePID(){

    }
    fun computeOutput() : Double{
        val raw_error = current - destination
        val b = (360 - Math.abs(raw_error))
        if(raw_error > 0){
            if(Math.abs(raw_error) > Math.abs((b))){
                error = -b
            } else {
                error = raw_error
            }
        } else {
            if(Math.abs(raw_error) > Math.abs((b))){
                error = b
            } else {
                error = raw_error
            }
        }
        return (p*error + i*error + d*error)
    }
}