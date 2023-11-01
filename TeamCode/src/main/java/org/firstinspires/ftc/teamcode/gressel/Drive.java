package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.Gamepad;
@TeleOp
public class Drive extends OpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private Gamepad gamepad;

    @Override
    public void init() {
        // Initialize drivetrain motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        gamepad = gamepad1; // You can use gamepad1 or gamepad2, depending on which controller you want to use
    }

    @Override
    public void loop() {
        boolean objectIsInFront = gamepad.a; // Set objectIsInFront to true when the "A" button is pressed

        // If the object is in front, move forward slowly
        if (objectIsInFront) {
            moveForward(0.2); // Move forward with a power level of 0.2 (slower)
        } else {
            // Stop the robot if the object is not in front
            stopRobot();
        }
    }

    private void moveForward(double power) {
        setMotorPower(power, power, power, power);
    }

    private void stopRobot() {
        setMotorPower(0, 0, 0, 0);
    }

    private void setMotorPower(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }
}
