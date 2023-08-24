package org.firstinspires.ftc.teamcode.Thomas;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
public class HelloBen extends OpMode {

    @Override
    public void init() { telemetry.addData("Hello","Ben"); }
    @Override
    public void loop() {

    }
}

