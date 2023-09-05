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
    double Turn = gamepad1.right_stick_x / 2.0;
    telemetry.addData("Right Stick x", gamepad1.right_stick_x);
    telemetry.addData("Speed Turn", Turn);
    double righttrigger = gamepad1.right_trigger / 2.0;
    telemetry.addData("Right Triggers", gamepad1.right_trigger);
    telemetry.addData("Trigger Press", righttrigger);
    double lefttrigger = gamepad1.left_trigger / 2.0;
    telemetry.addData("Left trigger", gamepad1.left_trigger);
    telemetry.addData("Left trigger press", lefttrigger);

}
}
