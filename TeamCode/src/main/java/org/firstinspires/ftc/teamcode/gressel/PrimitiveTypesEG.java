package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class PrimitiveTypesEG extends OpMode {
    @Override
    public void init() {
        int teamNumber = 17385;
        double motorSpeed = 0.5;
        boolean touchSensorPressed = true;
        int gradeNumber = 11;
        String myName= "Eric Gressel";
        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Touch Sensor", touchSensorPressed);
        telemetry.addData("Grade", gradeNumber);

    }
    @Override
    public void loop() {

    }
}
