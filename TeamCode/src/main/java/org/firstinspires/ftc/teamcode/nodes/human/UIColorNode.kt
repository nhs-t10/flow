package org.firstinspires.ftc.teamcode.nodes.human

import android.app.Activity
import android.graphics.Color
import android.view.View
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Node
import org.firstinspires.ftc.teamcode.messages.Message
import org.firstinspires.ftc.teamcode.messages.TextMsg

/**
 * Created by max on 1/24/18.
 */
class UIColorNode(hardwareMap: HardwareMap) : Node("UI Colors (extra)") {
    var relativeLayout : View? = null
    init {
        val relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName())
        relativeLayout = (hardwareMap.appContext as Activity).findViewById(relativeLayoutId)
    }

    override fun subscriptions() {
        subscribe("/changeUIColor", {onReceiveColor(it)})
    }

    fun onReceiveColor(m: Message) {
        val (text) = m as TextMsg
        changeColor(text)
    }

    fun changeColor(color: String) {
        relativeLayout?.post(Runnable { relativeLayout?.setBackgroundColor(Color.parseColor(color)) })
    }

}