package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class HelloEric extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello","Eric");
    }

    @Override
    public void loop() {

    }
}
