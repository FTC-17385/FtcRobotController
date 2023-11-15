package org.firstinspires.ftc.teamcode.Thomas;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp()
public class MathOpModeBT extends OpMode {
    @Override
    public void init() {
    }

    @Override
    public void loop() {
        /* These are the variables being displayed on the driver hub */
        telemetry.addData("Right stick y", gamepad1.right_stick_y);
        /* Makes the right stick work and show up on driver hub */
        boolean bButton = gamepad1.b;
        /* shows and lets the b button work on the driver hub */
        telemetry.addData("B Button", bButton);
        double StickDifference = gamepad1.left_stick_y - gamepad1.right_stick_y;
        /* shows the difference the sticks have when moved */
        telemetry.addData("Left Stick Difference", StickDifference);
        double TriggerSum = gamepad1.left_trigger + gamepad1.right_trigger;
        telemetry.addData("TriggerSum", TriggerSum);
        /* Adds the triggers together to equal a sum when pressed */
        

    }
}