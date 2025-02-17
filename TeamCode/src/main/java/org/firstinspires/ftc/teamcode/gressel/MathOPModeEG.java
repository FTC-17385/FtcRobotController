package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
@Disabled
public class MathOPModeEG extends OpMode {
    @Override
    public void init() {

    }
    @Override
    public void loop() {
        /*
        These are different values that are changed mathematically
         */
        double speedForward = -gamepad1.left_stick_y / 2.0;
        double difference = gamepad1.left_stick_y - gamepad1.right_stick_y;
        double sum = gamepad1.left_trigger + gamepad1.right_trigger;
        /*
        Everything under this adds all of the values to the screen to be seen by the controller
         */
        telemetry.addData("Left stick y", gamepad1.left_stick_y);
        telemetry.addData("Right stick x", gamepad1.right_stick_x);
        telemetry.addData("B button pressed", gamepad1.b);
        telemetry.addData("speed Forward", speedForward);
        telemetry.addData("Difference",difference);
        telemetry.addData("Sum", sum);

    }

}