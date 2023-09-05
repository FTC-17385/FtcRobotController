package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
/* This tells the code to be used in Teleop mode, and calls to the file class. */
@TeleOp()
public class mathopmodekk extends OpMode {
    @Override
    public void init() {
    }
    /* this initializes the op mode */
    @Override
    public void loop() {
        /* These are the variables being displayed on the driver hub */
        telemetry.addData("Right stick y", gamepad1.right_stick_y);
        /* This adds right stick to driver hub */
        boolean bButton = gamepad1.b;
        /* this boolean shows if the B button is pressed */
        telemetry.addData("B Button", bButton);
        double StickDifference = gamepad1.left_stick_y - gamepad1.right_stick_y;
        /* shows the difference between the sticks have when moved */
        telemetry.addData("Left Stick Difference", StickDifference);
        /* displayes the stick difference on the driver hub */
        double TriggerSum = gamepad1.left_trigger + gamepad1.right_trigger;
        telemetry.addData("TriggerSum", TriggerSum);
        /* Shows the sum of the triggers when pressed */


    }
}