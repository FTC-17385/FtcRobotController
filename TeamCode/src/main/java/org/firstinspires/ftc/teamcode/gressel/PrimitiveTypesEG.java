package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp()
@Disabled
public class PrimitiveTypesEG extends OpMode {
    @Override
    public void init() {
        int teamNumber = 17385;
        /*
        The motorSpeed caps at one
        The boolean is yes or no
         */
        double motorSpeed = 0.5;
        boolean touchSensorPressed = true;
        int gradeNumber = 11;
        String myName= "Eric Gressel";
        /*
        This displays all the info
         */
        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Touch Sensor", touchSensorPressed);
        telemetry.addData("Grade", gradeNumber);

    }
    @Override
    public void loop() {

    }
}
