package org.firstinspires.ftc.teamcode.nodes

import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.CallbackMapMsg
import org.firstinspires.ftc.teamcode.messages.TextMsg
import org.firstinspires.ftc.teamcode.messages.UnitMsg
import org.firstinspires.ftc.teamcode.messages.UpdateMsg
import org.firstinspires.ftc.teamcode.util.whenDown
import java.util.*

/**
 * Created by max on 11/10/17.
 */
class InspectorNode : Node() {
    enum class STATES { OFF,
        MAIN, // main menu
        INSPECTALL, // list of channels
        CHANNELOPT, // channel menu
        TAIL // watch live stream of channel
    }
    var state = STATES.OFF
    var tailIndice = 0
    var tailName = ""

    override fun subscriptions() {
        this.subscribe("/gamepad1/back", whenDown { main() })
        this.subscribe("/gamepad1/a", whenDown { tailBack() })
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

    fun isDisabled(channel: String) : Boolean {
        return Dispatcher.channels[channel]?.first ?: -1 == -1
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
                "$channel" to {},
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
        this.state = STATES.TAIL
        this.publish("/telemetry/staticLine", TextMsg("Press A to go back"))
        tailIndice = Dispatcher.channels[channel]?.second?.size ?: -1
        tailName = channel
        this.subscribe(channel, {this.publish("/telemetry/line", TextMsg(it.toString()))})
    }

    fun tailBack() {
        if(state == STATES.TAIL) {
            // TODO: Scarily risky, but it'll do
            Dispatcher.channels[tailName]?.second?.removeAt(tailIndice)
            this.publish("/telemetry/clear", UnitMsg())
            inspect(tailName)
        }
    }

    fun disableAll() {
        for (key in Dispatcher.channels.keys) {
            Dispatcher.lock(key, -1)
        }
        this.publish("/selector/update", UpdateMsg<String>("Disable All Channels", "All Channels Disabled."))
    }

    fun end() {
        state = STATES.OFF
        this.publish("/selector/end", UnitMsg())
    }
}