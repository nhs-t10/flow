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

class VuforiaNode(hardwareMap: HardwareMap) : Node("Vuforia") {
    var vuforia : VuforiaLocalizer? = null
    var relicTemplate : VuforiaTrackable? = null
    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        val parameters = VuforiaLocalizer.Parameters(cameraMonitorViewId)
        parameters.vuforiaLicenseKey = "AS3FQkX/////AAAAGdhA07mf1U07qYC/gobmgK0IAEaYb2HVJDGHxXKOG6I3B5ii9zFBF90rBzAND2oa7JCBWHMk2nra5AoXsXOfChk/N8QS1GZk5MNwbQ/wVwisS/fz04KmSpSXmgDp0PIkdf3dihm/Ax1hNxK3CcSntpaIU6eEHY4INE1AUoOA39YwPcOsYx6TGG6OML2+to5IfoLsIzWJ4URXkSTrF2WoQ8KIBBrqaAAJ6rAoqE8PVl9Ejp/vXMAlyDqoYbRZo6F/5w4v15wUTWjSfuD3QyKOuYRA9nnY8JRDirlQGje8xiCLzsUzrW2QC8eseXiGmEToWpd56UPp9OnnIGWldIkKSdfTHToy+3PdaVLQ45UJ1fLr"
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters)
        val relicTrackables = this.vuforia?.loadTrackablesFromAsset("RelicVuMark")
        relicTemplate = relicTrackables?.get(0)
        relicTemplate?.setName("relicVuMarkTemplate") // can help in debugging; otherwise not necessary


    }
    override fun subscriptions() {
        this.subscribe("/heartbeat", {refresh()})
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

                this.publish("/vuforia", VuforiaMsg(mark = vuMark, x = x, y = y, z = z, priority = 1))
            }
            else this.publish("/vuforia", VuforiaMsg(mark = vuMark, x = null, y = null, z = null, priority = 1))
        }
    }
}