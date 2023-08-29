package org.firstinspires.ftc.teamcode.Scott;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
//import above with TeleOp on the last word comes from the above command
public class PrimitiveTypesZach extends OpMode {
    //class or name of code is whats below. this is shown through the "extends OpMode" line
    @Override
    //Override parent class
    public void init(){
        //past code voided to create new code. init means on initialization the things below will happen.
        double motorSpeed = 0.5;
        //double=decimals
        int teamNumber = 17385;
        //int=integers
        boolean touchSensorPressed = true;
        //boolean=true and false

        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Touch Sensor", touchSensorPressed);
        //uses value above these lines and references then inside the parenthesis
    }

    @Override
    public void loop() {
        //Override parent class, void to create new code, and must have loop so they add it down here.

    }
}