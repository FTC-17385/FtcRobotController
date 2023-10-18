package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

//@Autonomous
@Disabled
public class HelloEric extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello","Eric");
    }
/*
This displays Hello Eric
 */
    @Override
    public void loop() {

    }
}
