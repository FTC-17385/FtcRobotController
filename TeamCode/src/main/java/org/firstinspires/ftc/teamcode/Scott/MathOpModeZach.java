package org.firstinspires.ftc.teamcode.Scott;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
@Disabled
public class MathOpModeZach extends OpMode {
    //code is public, everything below is the code
    @Override
    //Override past part, new code
    public void init() {
        //void past code, on initialization, stuff happens. nothing happens
    }

    @Override
    //override, new code incoming
    public void loop() {
        //void, new code comes. loop until ended.
        double stickSubtraction = gamepad1.left_stick_y - gamepad1.right_stick_y;
        telemetry.addData("Stick Subtraction", stickSubtraction);
        //string value= left stick y - right stick y. "string subtraction", string value
        telemetry.addData("Right Stick", gamepad1.right_stick_y);
        //adding telemetry to right stick
        telemetry.addData("B button",gamepad1.b);
        //saying if b button is pressed. could probably use a boolean value
        boolean bButton = gamepad1.b;
        telemetry.addData("Boolean B Button", bButton);
        //i added boolean value of the b button.
        double triggerAddition = gamepad1.left_trigger + gamepad1.right_trigger;
        telemetry.addData("Trigger Addition", triggerAddition);
//left trigger+right trigger. Displayed on the driver hub. Explained how it works on stickSubtraction

    }
}
