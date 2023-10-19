package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@TeleOp(name = "BasicTeleOp1", group = "TeleOp")
public class AnotherArmCode extends OpMode {

    // Define motor objects
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor armMotor;

    // For threaded arm control
    private ArmThread armThread;
    private Lock armLock = new ReentrantLock();
    private int armTargetPosition = 0;

    // Variables for arm control states
    private boolean armUp = false;
    private boolean armDown = false;

    @Override
    public void init() {
        // Initialize motors from hardware map
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");

        // Reset motor modes and set default target position
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(armTargetPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start arm control thread
        armThread = new ArmThread();
        armThread.start();
    }

    @Override
    public void loop() {
        // Drive control
        double drive = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        // Calculate wheel powers based on the desired behavior
        double frontLeftPower = drive + strafe - rotate;
        double frontRightPower = drive - strafe + rotate;
        double backLeftPower = drive - strafe - rotate;
        double backRightPower = drive + strafe + rotate;

        // Set the power to the motors
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);

        // Arm control
        armLock.lock();
        try {
            armUp = gamepad1.a;
            armDown = gamepad1.y;
        } finally {
            armLock.unlock();
        }
    }

    @Override
    public void stop() {
        // Stop the arm control thread
        armThread.stopRunning();
        try {
            armThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ArmThread extends Thread {
        private boolean runFlag = true;

        @Override
        public void run() {
            while (runFlag) {
                armLock.lock();
                try {
                    if (armUp) {
                        armMotor.setPower(0.2);
                    } else if (armDown) {
                        armMotor.setPower(-0.2);
                    } else {
                        armMotor.setPower(0.0);
                    }
                } finally {
                    armLock.unlock();
                }

                // Sleep for a short period to prevent high CPU usage
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopRunning() {
            runFlag = false;
        }
    }
}