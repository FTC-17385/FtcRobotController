package org.firstinspires.ftc.teamcode.Faulkner;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
@Disabled
public class GamepadandBasicMath extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        telemetry.addData("Left stick x", gamepad1.left_stick_x);
        telemetry.addData("Left stick y", gamepad1.left_stick_y);
        telemetry.addData("A button", gamepad1.a);
    }
}
