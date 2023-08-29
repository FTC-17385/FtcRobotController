package org.firstinspires.ftc.teamcode.Scott;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
//android studio added the import line for TeleOp when i wrote @TeleOp
public class UseStringZach extends OpMode {
    //public class=name of code. all below is the code
    @Override
    //override parent class
    public void init() {
        //void past code, on initialization...
        String myName = "Zach Scott";
        int grade = 10;
        telemetry.addData("Grade", grade);
        telemetry.addData("Hello", myName);
        //uses a string value and integer into the telemetry.addData
        //"telemetry.addData displays whatever is in the parenthesis on the driver hub.
        //For this it displays "Grade=10" and "Hello=Zach Scott
    }

    @Override
    public void loop() {
//override parents, void past actions, loop to finish code.
    }
}