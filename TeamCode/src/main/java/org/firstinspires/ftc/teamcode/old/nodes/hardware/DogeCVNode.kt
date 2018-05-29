package org.firstinspires.ftc.teamcode.old.nodes.hardware

import com.disnodeteam.dogecv.CameraViewDisplay
import com.disnodeteam.dogecv.detectors.CryptoboxDetector
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.old.NodeOld
import org.firstinspires.ftc.teamcode.old.messages.TextMsg
import org.firstinspires.ftc.teamcode.old.nodes.HeartbeatNode

/**
 * Created by max on 1/18/18.
 */
class DogeCVNode(hardwareMap: HardwareMap) : HeartbeatNode("DogeCV") {
    var cryptoboxDetector : CryptoboxDetector? = null
    var active = false

    init {
        cryptoboxDetector = CryptoboxDetector()
        cryptoboxDetector?.init(hardwareMap.appContext, CameraViewDisplay.getInstance())

        cryptoboxDetector?.rotateMat = false
    }

    override fun subscriptions() {
        subscribe("/cv/transition", {transition()})
        subscribe("/stop", {done()})
    }
    override fun onHeartbeat() {
        if(active) {
            publish("/dogecv", TextMsg("""
                ${cryptoboxDetector?.isCryptoBoxDetected()}
                ${cryptoboxDetector?.isColumnDetected()}
                ${cryptoboxDetector?.getCryptoBoxLeftPosition()}
                ${cryptoboxDetector?.getCryptoBoxCenterPosition()}
                ${cryptoboxDetector?.getCryptoBoxRightPosition()}
            """.trimIndent()))
        }
    }
    fun transition() {
        publish("/status", TextMsg("Switched camera ownership from Vuforia -> DogeCV."))
        cryptoboxDetector?.enable()
        active = true
    }
    fun done() {
        cryptoboxDetector?.disable()
        active = false
        // MR. SANDMAN PASS ME A DRINK MR. SANDMAN MAKE HIM A CUTIE
    }
}