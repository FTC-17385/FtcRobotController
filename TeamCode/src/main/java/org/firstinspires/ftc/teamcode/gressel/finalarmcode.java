package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="finalarmcode", group="TeleOp")
public class finalarmcode extends OpMode {

    // Declare the motor variables
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void init() {
        // Initialize the motors from the hardware map
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Set the motor directions
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        // Get the values from the gamepads
        double drive = gamepad1.left_stick_x;   // Forward and backward
        double strafe = -gamepad1.left_stick_y;   // Left and right
        double rotate = gamepad1.right_stick_x;  // Rotation

        // Adjusted power calculations for mecanum drive
        double frontLeftPower = drive + rotate + strafe;
        double frontRightPower = drive - rotate - strafe;
        double backLeftPower = drive + rotate - strafe;
        double backRightPower = drive - rotate + strafe;

        // Make sure the powers are within the acceptable range
        frontLeftPower = Range.clip(frontLeftPower, -.5, .5);
        frontRightPower = Range.clip(frontRightPower, -.5, .5);
        backLeftPower = Range.clip(backLeftPower, -.5, .5);
        backRightPower = Range.clip(backRightPower, -.5, .5);

        // Set the power to the motors
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Back Left Power", backLeftPower);
        telemetry.addData("Back Right Power", backRightPower);
        telemetry.update();
    }
}
