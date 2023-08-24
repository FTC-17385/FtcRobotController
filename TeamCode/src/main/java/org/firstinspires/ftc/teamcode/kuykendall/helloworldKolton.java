package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous()
public class helloworldKolton extends OpMode {
    /* This tells the code to be used in Teleop mode, and calls to the file class. */
    @Override
    public void init() {
        telemetry.addData("Hello", "Kolton");
    }
/* This loops the op mode */
    @Override
    public void loop() {

    }
}