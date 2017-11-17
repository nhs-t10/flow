package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.*
import org.firstinspires.ftc.teamcode.util.whenDown
import java.util.*

/**
 * Created by max on 11/10/17.
 */
class InspectorNode : Node("Inspector") {
    enum class STATES { OFF,
        MAIN, // main menu
        INSPECTALL, // list of channels
        CHANNELOPT, // channel menu
        TAIL, // watch live stream of channel
        TAILING
    }
    var state = STATES.OFF
    var tailIndice = 0
    var tailName = ""

    override fun subscriptions() {
        this.subscribe("/gamepad1/start", whenDown { main() })
        this.subscribe("/gamepad1/y", {tailBack(it as GamepadButtonMsg)} )
    }

    fun main() {
        this.state = STATES.MAIN
        val menu = hashMapOf(
                "Inspect Channels" to {inspectAll()},
                "Disable All Channels" to {disableAll()},
                "Exit" to {end()}
        )
        this.publish("/selector/begin", CallbackMapMsg(menu, priority = 1))
    }

    fun isDisabled(channel: String) : Boolean { // if not unlocked (null) or >-1
        return (Dispatcher.channels[channel]?.first ?: 0) == -1
    }

    fun inspectAll() {
        this.state = STATES.INSPECTALL
        val menu = HashMap<String, () -> Unit>()
        for (channel in Dispatcher.channels.keys) {
            menu.put("${if(isDisabled(channel)) "[DISABLED]" else ""} $channel [${Dispatcher.channels[channel]?.second?.size ?: 0} subs]", {inspect(channel)})
        }
        menu.put("Back", {main()})
        this.publish("/selector/begin", CallbackMapMsg(menu, 0))
    }

    fun inspect(channel : String) {
        this.state = STATES.CHANNELOPT

        val menu = hashMapOf(
                "${if (isDisabled(channel)) "Enable" else "Disable"}" to {
                    if (isDisabled(channel)) {
                        // POTENTIAL BUG NOTE: this unlocks channel completely,
                        // so any restrictions before are gone.
                        Dispatcher.unlock(channel)
                        this.publish("/selector/update", UpdateMsg<String>("Enable", "Disable"))
                    }
                    else {
                        Dispatcher.lock(channel, -1)
                        this.publish("/selector/update", UpdateMsg<String>("Disable", "Enable"))
                    }
                },
                "Tail" to {
                    this.publish("/selector/end", UnitMsg())
                    tail(channel)
                },
                "Back" to {inspectAll()}
        )

        this.publish("/selector/begin", CallbackMapMsg(menu, 0))
    }

    fun tail(channel: String) {
        this.publish("/telemetry/clear", UnitMsg())
        this.publish("/telemetry/staticLine", TextMsg("Tailing $channel"))
        this.publish("/telemetry/staticLine", TextMsg("Press A to go back"))
        tailIndice = Dispatcher.channels[channel]?.second?.size ?: -1
        tailName = channel
        this.subscribe(channel, {this.publish("/telemetry/line", TextMsg(it.toString()))})
        this.state = STATES.TAIL
    }

    fun tailBack(m: GamepadButtonMsg) {
        if(state == STATES.TAIL && !m.value ) {
            this.state = STATES.TAILING
        }
        else if(state == STATES.TAILING && m.value) {
            // TODO: Scarily risky, but it'll do
            Dispatcher.channels[tailName]?.second?.removeAt(tailIndice)
            this.publish("/telemetry/clear", UnitMsg())
            inspect(tailName)
        }
    }

    fun disableAll() {
        for (key in Dispatcher.channels.keys) {
            val disableList = arrayOf("/telemetry/line",
                    "/telemetry/staticLine",
                    "/telemetry/lines",
                    "/telemetry/clear",
                    "/warn",
                    "/error"
            )
            if(!disableList.contains(key)) Dispatcher.lock(key, -1)
        }
        this.publish("/selector/update", UpdateMsg<String>("Disable All Channels", "All Channels Disabled."))
    }

    fun end() {
        state = STATES.OFF
        this.publish("/selector/end", UnitMsg())
    }
}
