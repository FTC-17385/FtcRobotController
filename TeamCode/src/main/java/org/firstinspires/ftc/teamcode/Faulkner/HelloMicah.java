package org.firstinspires.ftc.teamcode.Faulkner;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
public class HelloMicah extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello","Micah");
    }

    @Override
    public void loop() {
        
    }
}
