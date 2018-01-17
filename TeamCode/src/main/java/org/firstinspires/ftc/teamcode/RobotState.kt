package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import org.firstinspires.ftc.teamcode.util.TeamColor

/**
 * Created by max on 1/17/18.
 */
data class RobotState(
        var vuMark : RelicRecoveryVuMark = RelicRecoveryVuMark.UNKNOWN
)