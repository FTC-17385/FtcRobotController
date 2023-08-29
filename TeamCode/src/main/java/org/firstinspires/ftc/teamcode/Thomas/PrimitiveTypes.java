package org.firstinspires.ftc.teamcode.Thomas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
/* calls to teleOp */
public class PrimitiveTypes extends OpMode {
    @Override
    public void init() {
        int teamNumber = 17385;
        double motorSpeed = 0.5;
        boolean touchedSensorPressed = true;
        /* display variables to driver hub */

        telemetry.addData("teamNumber", teamNumber);
        telemetry.addData("motorSpeed", motorSpeed);
        telemetry.addData("touchSensorPresses", touchedSensorPressed);
        /* display the data to driver hub */
    }

    @Override
    public void loop() {

    }
}
