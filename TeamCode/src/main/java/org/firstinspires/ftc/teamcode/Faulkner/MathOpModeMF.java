package org.firstinspires.ftc.teamcode.Faulkner;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
@Disabled
public class MathOpModeMF extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        double speedForward = -gamepad1.left_stick_y/2.0;
        telemetry.addData("Left stick y", gamepad1.left_stick_y);
        telemetry.addData("Right stick y", gamepad1.right_stick_y);
        telemetry.addData("B button", gamepad1.b);
        telemetry.addData("speed Forward", speedForward);
        double stickDifference = gamepad1.left_stick_y- gamepad1.right_stick_y;
        double triggerSum = gamepad1.right_trigger += gamepad1.left_trigger;
        telemetry.addData("Trigger Sum", triggerSum);
        telemetry.addData("Stick Difference", stickDifference);
    }
}
