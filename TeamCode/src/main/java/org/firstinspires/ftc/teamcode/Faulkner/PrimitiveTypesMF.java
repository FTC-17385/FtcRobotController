package org.firstinspires.ftc.teamcode.Faulkner;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class PrimitiveTypesMF extends OpMode {
    @Override
    public void init() {
        double motorSpeed = 0.5;
        int teamNumber = 17385;
        boolean touchSensorPressed = true;
        int gradeNumber = 12;
        String myName = "Micah Faulkner";

        /*these are variables that can be called by the function name*/

        telemetry.addData("Hello", myName);
        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Touch Sensor", touchSensorPressed);
        telemetry.addData("Grade", gradeNumber);
        /*these push the code up*/
    }

    @Override
    public void loop() {}
}
