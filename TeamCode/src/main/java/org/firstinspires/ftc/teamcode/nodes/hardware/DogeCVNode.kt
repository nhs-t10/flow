package org.firstinspires.ftc.teamcode.nodes.hardware

import com.disnodeteam.dogecv.CameraViewDisplay
import com.disnodeteam.dogecv.detectors.CryptoboxDetector
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by max on 1/18/18.
 */
class DogeCVNode(hardwareMap: HardwareMap) : Node("DogeCV") {
    var cryptoboxDetector : CryptoboxDetector? = null
    var active = false

    init {
        cryptoboxDetector = CryptoboxDetector()
        cryptoboxDetector?.init(hardwareMap.appContext, CameraViewDisplay.getInstance())

        cryptoboxDetector?.rotateMat = false
    }

    override fun subscriptions() {
        subscribe("/cv/transition", {start()})
        subscribe("/stop", {stop()})
        subscribe("/heartbeat", {updateCV()})
    }
    fun updateCV() {
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
    fun start() {
        publish("/status", TextMsg("Switched camera ownership from Vuforia -> DogeCV."))
        cryptoboxDetector?.enable()
        active = true
    }
    fun stop() {
        cryptoboxDetector?.disable()
        active = false
        // MR. SANDMAN PASS ME A DRINK MR. SANDMAN MAKE HIM A CUTIE
    }
}