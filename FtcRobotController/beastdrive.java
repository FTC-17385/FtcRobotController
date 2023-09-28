package org.firstinspires.ftc.teamcode.beastdrive;
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class beastdrive extends LinearOpMode {

    // Declare Motors
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor armMotor;

    // Declare Servo (Commented out as per your request)
    // private Servo gripperServo;

    @Override
    public void runOpMode() {
        // Initialize Motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // Initialize Servo (Commented out as per your request)
        // gripperServo = hardwareMap.get(Servo.class, "gripperServo");

        waitForStart();

        while (opModeIsActive()) {

            // Left Stick for forward, backward, and strafing
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;

            // Right Stick for rotation
            double rotate = gamepad1.right_stick_x;

            // Calculate motor powers
            double frontLeftPower = drive + rotate + strafe;
            double frontRightPower = drive - rotate - strafe;
            double backLeftPower = drive + rotate - strafe;
            double backRightPower = drive - rotate + strafe;

            // Set motor powers
            frontLeftMotor.setPower(frontLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backLeftMotor.setPower(backLeftPower);
            backRightMotor.setPower(backRightPower);

            // X to open gripper, B to close gripper (Commented out as per your request)
            /*
            if (gamepad1.x) {
                gripperServo.setPosition(1.0);  // Open
            } else if (gamepad1.b) {
                gripperServo.setPosition(0.0);  // Close
            }
            */

            // Y to lift arm, A to lower arm
            if (gamepad1.y) {
                armMotor.setPower(1.0);  // Lift arm
            } else if (gamepad1.a) {
                armMotor.setPower(-1.0);  // Lower arm
            } else {
                armMotor.setPower(0.0);  // Stop arm
            }

            // Optional: Telemetry data for debugging
            telemetry.addData("Front Left Motor Power", frontLeftPower);
            telemetry.addData("Front Right Motor Power", frontRightPower);
            telemetry.addData("Back Left Motor Power", backLeftPower);
            telemetry.addData("Back Right Motor Power", backRightPower);
            telemetry.update();
        }
    }
}
