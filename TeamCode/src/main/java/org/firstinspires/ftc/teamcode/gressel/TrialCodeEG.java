package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TrialCodeEG extends LinearOpMode {
    // Declare Motors
    private DcMotor armMotor;
    private Thread armThread;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        // Initialize Arm Motor
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Use encoder for control

        // Create thread to manage arm position
        armThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    if (gamepad1.y) {
                        armMotor.setTargetPosition(armMotor.getCurrentPosition() + 10); // Increase target position
                        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        armMotor.setPower(0.3); // Lift arm
                    } else if (gamepad1.a) {
                        armMotor.setTargetPosition(armMotor.getCurrentPosition() - 10); // Decrease target position
                        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        armMotor.setPower(0.3); // Lower arm
                    } else {
                        armMotor.setPower(0.0); // Stop arm
                    }
                    try {
                        Thread.sleep(10); // Small sleep to prevent tight looping
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        armThread.start();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;  // Forward/Backward movement
            double strafe = gamepad1.left_stick_x;    // Left/Right movement (strafing)
            double rotate = gamepad1.right_stick_x;   // Rotation

            double frontLeftPower = forward + strafe + rotate;
            double backLeftPower = forward - strafe + rotate;
            double frontRightPower = forward - strafe - rotate;
            double backRightPower = forward + strafe - rotate;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}
