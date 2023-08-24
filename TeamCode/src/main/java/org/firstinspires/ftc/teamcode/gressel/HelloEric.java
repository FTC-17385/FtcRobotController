package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class HelloEric extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello","Eric");
    }

    @Override
    public void loop() {

    }
}
