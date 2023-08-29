package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class Primitivetypeskk extends OpMode {
    /* This tells the code to be used in Teleop mode, and calls to the file class. */
    @Override
    /* these are the variables that will be displayed to the driver hub */
    public void init() {
       int teamNumber = 17385;
       double motorSpeed = 0.5;
       boolean touchSensorPressed = true;
/* This is the telemetry data being displayed to the driver hub */
       telemetry.addData("Team Number", teamNumber);
         telemetry.addData("Motor Speed", motorSpeed);
            telemetry.addData("Touch Sensor Pressed", touchSensorPressed);
    }
/* This loops the op mode */
    @Override
    public void loop() {

    }
}