package org.firstinspires.ftc.teamcode.nodes.human

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.HeartBeatMsg
import org.firstinspires.ftc.teamcode.messages.GamepadButtonMsg
import org.firstinspires.ftc.teamcode.messages.GamepadJoyOrTrigMsg
import org.firstinspires.ftc.teamcode.nodes.HeartbeatNode
import java.util.*

import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

/**
 * Created by dvw06 on 10/12/17.
 */

class GamepadNode(val gamepad1: Gamepad, val gamepad2: Gamepad) : HeartbeatNode("Gamepad") {
    var gamepad1Buttons = HashMap<String, Boolean>()
    var gamepad1JoyOrTrigs = HashMap<String, Float>()
    var gamepad2Buttons = HashMap<String, Boolean>()
    var gamepad2JoyOrTrigs = HashMap<String, Float>()
    init {
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
    override fun subscriptions() {
    }

    override fun onHeartbeat(){
        //gamepad 1 data publishing
        if(gamepad1 == null){
            return
        }
        for (prop in Gamepad::class.memberProperties) {
            prop.isAccessible = true
            if (prop.get(gamepad1 as Gamepad) != null && prop.get(gamepad1 as Gamepad) is Boolean) {
                if (gamepad1Buttons[prop.name] != null) {
                    val value = prop.get(gamepad1 as Gamepad) as Boolean
                    if (gamepad1Buttons[prop.name] != value) {
                        gamepad1Buttons.put(prop.name, value)
                        this.publish("/gamepad1/${prop.name}", GamepadButtonMsg(value = value, priority = 1))
                    }
                }
            }
            else if (prop.get(gamepad1 as Gamepad) != null && prop.get(gamepad1 as Gamepad) is Float) {
                if (gamepad1JoyOrTrigs[prop.name] != null) {
                    if (gamepad1JoyOrTrigs[prop.name] != prop.get(gamepad1 as Gamepad)) {
                        gamepad1JoyOrTrigs.put(prop.name, prop.get(gamepad1 as Gamepad) as Float)
                        this.publish("/gamepad1/${prop.name}", GamepadJoyOrTrigMsg(value = prop.get(gamepad1 as Gamepad) as Float, priority = 1))
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
                        this.publish("/gamepad2/${prop.name}", GamepadButtonMsg(value = prop.get(gamepad2 as Gamepad) as Boolean, priority = 1))
                    }
                }
            }
            else if (prop.get(gamepad2 as Gamepad) != null && prop.get(gamepad2 as Gamepad) is Float) {
                if (gamepad2JoyOrTrigs[prop.name] != null) {
                    if (gamepad2JoyOrTrigs[prop.name] != prop.get(gamepad2 as Gamepad)) {
                        gamepad2JoyOrTrigs.put(prop.name, prop.get(gamepad2 as Gamepad) as Float)
                        this.publish("/gamepad2/${prop.name}", GamepadJoyOrTrigMsg(value = prop.get(gamepad2 as Gamepad) as Float, priority = 1))
                    }
                }
            }
        }
    }
}
