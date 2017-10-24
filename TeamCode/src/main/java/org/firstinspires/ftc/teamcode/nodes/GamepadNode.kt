package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeat
import org.firstinspires.ftc.teamcode.messages.gamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.gamepadJoyOrTrigMsg

import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Created by dvw06 on 10/12/17.
 */

class GamepadNode : Node
{
    var gamepadButtons = HashMap<String, Boolean>()
    var gamepadJoyOrTrigs = HashMap<String, Float>()
    var gamepad1 : Gamepad? = null
    var gamepad2 : Gamepad? = null
    constructor(gamepad1: Gamepad, gamepad2: Gamepad){
        if (Dispatcher.channels.containsKey("/heartbeat") || Dispatcher.channels.get("/heartbeat") != null){
            Dispatcher.subscribe("/heartbeat", {click(it as HeartBeat)})
        }
        this.gamepad1 = gamepad1
        this.gamepad2 = gamepad2
        for (property in Gamepad::class.declaredMemberProperties) {
            if(property.isAccessible){
                if(property.get(gamepad1) != null && property.get(gamepad1) is Boolean){
                    gamepadButtons.put(property.name, property.get(gamepad1) as Boolean)
                } else if (property.get(gamepad1) != null && property.get(gamepad1) is Float) {
                    gamepadJoyOrTrigs.put(property.name, property.get(gamepad1) as Float)
                }
            }
        }
    }
    fun click(hb: HeartBeat){
        val (time) = hb
        if(gamepad1 == null){
            return
        }
        for (prop in Gamepad::class.memberProperties) {
            if(prop.isAccessible){
                if (prop.get(gamepad1 as Gamepad) != null && prop.get(gamepad1 as Gamepad) is Boolean) {
                    if (gamepadButtons.get(prop.name) != null) {
                        if (gamepadButtons.get(prop.name) != prop.get(gamepad1 as Gamepad)) {
                            gamepadButtons.put(prop.name, prop.get(gamepad1 as Gamepad) as Boolean)
                            Dispatcher.publish("/gamepad1/${prop.name}", gamepadButtonMsg(value = prop.get(gamepad1 as Gamepad) as Boolean, priority = 3))
                        }
                    }
                }
                else if (prop.get(gamepad1 as Gamepad) != null && prop.get(gamepad1 as Gamepad) is Float) {
                    if (gamepadJoyOrTrigs.get(prop.name) != null) {
                        if (gamepadJoyOrTrigs.get(prop.name) != prop.get(gamepad1 as Gamepad)) {
                            gamepadJoyOrTrigs.put(prop.name, prop.get(gamepad1 as Gamepad) as Float)
                            Dispatcher.publish("/gamepad1/${prop.name}", gamepadJoyOrTrigMsg(value = prop.get(gamepad1 as Gamepad) as Float, priority = 2))
                        }
                    }
                }
            }
        }
    }
}
