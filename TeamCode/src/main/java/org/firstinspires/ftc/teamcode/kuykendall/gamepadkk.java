package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class gamepadkk extends OpMode {
    @Override
    public void init() {
    }
    @Override
    public void loop() {
    double speedForward = -gamepad1.left_stick_y / 2.0;
    telemetry.addData("Left Stick y", gamepad1.left_stick_y);
    telemetry.addData("Speed Forward", speedForward);
}
}
