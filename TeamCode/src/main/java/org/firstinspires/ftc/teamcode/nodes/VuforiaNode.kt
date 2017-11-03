package org.firstinspires.ftc.teamcode.nodes

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix
import org.firstinspires.ftc.robotcore.external.matrices.VectorF
import org.firstinspires.ftc.robotcore.external.navigation.*
import org.firstinspires.ftc.teamcode.Dispatcher
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.VuforiaMsg

/**
 * Created by max on 11/2/17.
 */

class VuforiaNode : Node {
    var vuforia : VuforiaLocalizer? = null
    var relicTemplate : VuforiaTrackable? = null
    constructor(hardwareMap: HardwareMap) {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        val parameters = VuforiaLocalizer.Parameters(cameraMonitorViewId)
        parameters.vuforiaLicenseKey = "ATsODcD/////AAAAAVw2lR...d45oGpdljdOh5LuFB9nDNfckoxb8COxKSFX" // TODO: populate
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters)
        val relicTrackables = this.vuforia?.loadTrackablesFromAsset("RelicVuMark")
        relicTemplate = relicTrackables?.get(0)
        relicTemplate?.setName("relicVuMarkTemplate") // can help in debugging; otherwise not necessary

        Dispatcher.subscribe("/heartbeat", {refresh()})
    }
    fun refresh() {
        val vuMark = RelicRecoveryVuMark.from(relicTemplate)
        if (vuMark != RelicRecoveryVuMark.UNKNOWN && relicTemplate != null) {
            val pose : OpenGLMatrix? = (relicTemplate?.getListener() as VuforiaTrackableDefaultListener).getPose()
            if (pose != null) {
                val translation : VectorF = pose?.getTranslation()

                val x = translation.get(0) as Double
                val y = translation.get(1) as Double
                val z = translation.get(2) as Double

                Dispatcher.publish("/vuforia", VuforiaMsg(mark = vuMark, x = x, y = y, z = z, priority = 1))
            }
            else Dispatcher.publish("/vuforia", VuforiaMsg(mark = vuMark, x = null, y = null, z = null, priority = 1))
        }
    }
}