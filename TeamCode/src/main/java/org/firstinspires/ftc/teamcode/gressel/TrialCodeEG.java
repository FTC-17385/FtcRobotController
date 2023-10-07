package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TrialCodeEG extends LinearOpMode {
    // Declare Motors
    private DcMotor armMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        // Initialize Arm Motor
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Use encoder for control
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Set motor to brake mode when power is zero

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

            // Arm Motor Control
            if (gamepad1.y) {
                armMotor.setTargetPosition(armMotor.getCurrentPosition() + 10);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.5);
            } else if (gamepad1.a) {
                armMotor.setTargetPosition(armMotor.getCurrentPosition() - 10);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.5);
            } else {
                armMotor.setPower(0); // Let the BRAKE mode do the work
            }
        }
    }
}
