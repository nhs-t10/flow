package org.firstinspires.ftc.teamcode.old.nodes.routines

import org.firstinspires.ftc.teamcode.old.RoutineNode
import org.firstinspires.ftc.teamcode.old.messages.Message

/**
 * Created by max on 4/5/18.
 *
 * Usage:
 * DoUntilRoutine(hashMapOf(
 *      "/analog/ultra1" to {msg ->
 *          // This ends the opmode
 *          if (msg as AnalogMsg).value > 0.5 {
 *              Dispatcher.publish("/drive", OmniMsg(0.0f, 0.0f, 0.0f, 1))
 *              return true
 *          }
 *          else return false
 *      },
 *      "/digital/touch1" to {msg ->
 *          Dispatcher.publish("/digital/touch1", {msg ->
 *              // This also ends the opmode if it happens first
 *              if (msg as DigitalMsg).value) return true
 *              return false
 *          }
 *      }
 * ), {
 *     Dispatcher.publish("/drive", OmniMsg(1.0f, 0.0f, 0.0f, 1))
 * })
 */
class DoUntilRoutine(val predicates: HashMap<String, (Message) -> Boolean?>, val onStart : (() -> Unit)?) : RoutineNode("Do Until") {
    override fun begin() {
        onStart?.invoke()
    }

    override fun subscriptions() {
        for(channel in predicates.keys) {
            subscribe(channel, {msg ->
                val done = predicates[channel]?.invoke(msg) ?: false
                if (done) {
                    end()
                }
            })
        }
    }
}