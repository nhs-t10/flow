package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.whenDown
import java.util.*

/**
 * Publish to /begin a CallbackMap of menu items
 * Update the appearance of items with /update "a" -> "b"
 * Exit with /end, but if you're transitioning menus then /begin with the new menu will do
 * Created by max on 11/11/17.
 */
class SelectorView : Node() {
    enum class STATES { OFF, ON }
    var state = STATES.OFF

    var index = 0
    var callbacks = HashMap<String, () -> Unit>()

    override fun subscriptions() {
        this.subscribe("/selector/begin", {this.begin(it as CallbackMapMsg)})
        this.subscribe("/selector/update", {this.update(it as UpdateMsg<String>)})
        this.subscribe("/selector/end", {this.end()})
        this.subscribe("/gamepad1/dpad_down", whenDown{down()})
        this.subscribe("/gamepad1/dpad_up", whenDown{up()})
        this.subscribe("/gamepad1/a", whenDown{select()})
    }

    fun down() {
        if (state == STATES.ON) {
            if (index >= callbacks.size - 1) {
                index = 0
            } else {
                index++
            }
            render()
        }
    }

    fun up() {
        if (state == STATES.ON) {
            if (index <= 0) {
                index = callbacks.size - 1
            } else {
                index--
            }
            render()
        }
    }

    fun select() {
        if (state == STATES.ON) {
            val cb = callbacks[callbacks.keys.elementAt(index)] ?: {}
            cb()
        }
    }

    fun render() {
        val mapped = listOf("Up and Down to move, A to select") + callbacks.keys.map{"${if (callbacks.keys.elementAt(index) == it) ">" else ""}$it"}
        this.publish("/telemetry/lines", LinesMsg(mapped, 0))
    }

    fun update(msg : UpdateMsg<String>) {
        val (a, b) = msg
        val cb = callbacks[a] ?: {}
        if (callbacks[a] == null) {
            this.publish("/warn", TextMsg("Can't update $a to $b, $a doesn't exist."))
        }
        callbacks.remove(a)
        callbacks.put(b, cb)
        this.render()
    }

    fun begin(msg : CallbackMapMsg) {
        val (callbacks) = msg
        this.callbacks = callbacks
        this.state = STATES.ON
        Dispatcher.lock("/debug", 0)
    }
    fun end() {
        this.publish("/telemetry/clear", UnitMsg())
        Dispatcher.unlock("/debug")
        this.state = STATES.OFF
    }
}