package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.nodes.*

/**
 * Created by shaash on 10/26/17.
 */
@Autonomous(name = "autonomous")
class T10Autonomous : OpMode(){
    var routine : RoutineGroup? = null
    var nodes : List<Node>? = null

    val heartbeat = HeartbeatNode()

    override fun init() {
        routine = RoutineGroup(listOf(

        ))
        nodes = listOf(
                GamepadNode(gamepad1, gamepad2),
                OmniDtNode(),
                OmniJoyNode(),
                ImuNode(hardwareMap),
                DigitalSensorNode(hardwareMap),
                GliftNode(),
                GlyphHolderNode(),
                EffectorNode(hardwareMap),
                DebugNode(),
                TelemetryNode(telemetry),
                heartbeat,
                SelectorViewNode(),
                InspectorNode()
//                VuforiaNode(hardwareMap)
        )
    }

    override fun start() {
        nodes?.forEach{
            it.subscriptions()
        }
        routine?.begin {  }
    }

    override fun loop() {
        heartbeat.beat((runtime*10).toLong())
    }
    override fun stop(){
        Dispatcher.reset()
    }
}