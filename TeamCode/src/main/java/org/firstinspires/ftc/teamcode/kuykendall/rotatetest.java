package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

// @TeleOp(name = "RotateTest", group = "Test")
@Disabled
public class rotatetest extends OpMode {

    // Declare the motor variables
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // Power scaling factors for each motor
    public static double FRONT_LEFT_SCALE = 0.5;
    public static double FRONT_RIGHT_SCALE = 0.5;
    public static double BACK_LEFT_SCALE = -0.5;
    public static double BACK_RIGHT_SCALE = 0.5;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
    }

    @Override
    public void loop() {
        double rotate = gamepad1.right_stick_x;

        if (rotate != 0) {
            double scaledPower = Math.abs(rotate);

            // Set the power for right rotation
            double backLeftPower = Math.abs(BACK_LEFT_SCALE) * scaledPower;
            double frontLeftPower = -FRONT_LEFT_SCALE * scaledPower;
            double frontRightPower = FRONT_RIGHT_SCALE * scaledPower;
            double backRightPower = -BACK_RIGHT_SCALE * scaledPower;

            // If rotating left, invert the power
            if (rotate < 0) {
                backLeftPower = -backLeftPower;
                frontLeftPower = -frontLeftPower;
                frontRightPower = -frontRightPower;
                backRightPower = -backRightPower;
            }

            backLeft.setPower(backLeftPower);
            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
        } else {  // no rotation input
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
    }
}
