package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.GamepadJoyOrTrigMsg
import java.util.*

import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Created by dvw06 on 10/12/17.
 */

class GamepadNode : Node
{
    var gamepad1Buttons = HashMap<String, Boolean>()
    var gamepad1JoyOrTrigs = HashMap<String, Float>()
    var gamepad2Buttons = HashMap<String, Boolean>()
    var gamepad2JoyOrTrigs = HashMap<String, Float>()
    var gamepad1 : Gamepad? = null
    var gamepad2 : Gamepad? = null
    constructor(gamepad1: Gamepad, gamepad2: Gamepad){
        this.gamepad1 = gamepad1
        this.gamepad2 = gamepad2
        //fill gamepad1 maps
        for (property in Gamepad::class.declaredMemberProperties) {
            property.isAccessible = true
            if(property.get(gamepad1) != null && property.get(gamepad1) is Boolean){
                gamepad1Buttons.put(property.name, property.get(gamepad1) as Boolean)
            } else if (property.get(gamepad1) != null && property.get(gamepad1) is Float) {
                gamepad1JoyOrTrigs.put(property.name, property.get(gamepad1) as Float)
            }
        }
        //fill gamepad2 maps
        for (property in Gamepad::class.declaredMemberProperties) {
            property.isAccessible = true
            if(property.get(gamepad2) != null && property.get(gamepad2) is Boolean){
                gamepad2Buttons.put(property.name, property.get(gamepad2) as Boolean)
            } else if (property.get(gamepad1) != null && property.get(gamepad2) is Float) {
                gamepad2JoyOrTrigs.put(property.name, property.get(gamepad2) as Float)
            }
        }
    }
    override fun init() {
        this.subscribe("/heartbeat", {update(it as HeartBeatMsg)})
    }
    fun update(hb: HeartBeatMsg){
        val (time) = hb
        //gamepad 1 data publishing
        if(gamepad1 == null){
            return
        }
        for (prop in Gamepad::class.memberProperties) {
            prop.isAccessible = true
            if (prop.get(gamepad1 as Gamepad) != null && prop.get(gamepad1 as Gamepad) is Boolean) {
                if (gamepad1Buttons[prop.name] != null) {
                    if (gamepad1Buttons[prop.name] != prop.get(gamepad1 as Gamepad)) {
                        gamepad1Buttons.put(prop.name, prop.get(gamepad1 as Gamepad) as Boolean)
                        this.publish("/gamepad1/${prop.name}", GamepadButtonMsg(value = prop.get(gamepad1 as Gamepad) as Boolean, priority = 3))
                    }
                }
            }
            else if (prop.get(gamepad1 as Gamepad) != null && prop.get(gamepad1 as Gamepad) is Float) {
                if (gamepad1JoyOrTrigs[prop.name] != null) {
                    if (gamepad1JoyOrTrigs[prop.name] != prop.get(gamepad1 as Gamepad)) {
                        gamepad1JoyOrTrigs.put(prop.name, prop.get(gamepad1 as Gamepad) as Float)
                        this.publish("/gamepad1/${prop.name}", GamepadJoyOrTrigMsg(value = prop.get(gamepad1 as Gamepad) as Float, priority = 2))
                    }
                }
            }
        }
        //gamepad 2 data publishing
        if(gamepad2 == null){
            return
        }
        for (prop in Gamepad::class.memberProperties) {
            prop.isAccessible = true
            if (prop.get(gamepad2 as Gamepad) != null && prop.get(gamepad2 as Gamepad) is Boolean) {
                if (gamepad2Buttons[prop.name] != null) {
                    if (gamepad2Buttons[prop.name] != prop.get(gamepad2 as Gamepad)) {
                        gamepad2Buttons.put(prop.name, prop.get(gamepad2 as Gamepad) as Boolean)
                        this.publish("/gamepad2/${prop.name}", GamepadButtonMsg(value = prop.get(gamepad2 as Gamepad) as Boolean, priority = 3))
                    }
                }
            }
            else if (prop.get(gamepad2 as Gamepad) != null && prop.get(gamepad2 as Gamepad) is Float) {
                if (gamepad2JoyOrTrigs[prop.name] != null) {
                    if (gamepad2JoyOrTrigs[prop.name] != prop.get(gamepad2 as Gamepad)) {
                        gamepad2JoyOrTrigs.put(prop.name, prop.get(gamepad2 as Gamepad) as Float)
                        this.publish("/gamepad2/${prop.name}", GamepadJoyOrTrigMsg(value = prop.get(gamepad2 as Gamepad) as Float, priority = 2))
                    }
                }
            }
        }
    }
}
