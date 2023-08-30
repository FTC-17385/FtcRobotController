package org.firstinspires.ftc.teamcode.Scott;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class GamepadOpModeZach extends OpMode {
    @Override
    public void init() {
        //void past, on initialization. nothing happens in this code
    }

    @Override
    public void loop() {
        //void past code, on loop until code ended, display stuff on driver hub
        telemetry.addData("Left stick x", gamepad1.left_stick_x);
        telemetry.addData("Left stick y", gamepad1.left_stick_y);
        telemetry.addData("A button", gamepad1.a);
        //reporting data on driver hub of the left sticks axes and a-button
    }
}
