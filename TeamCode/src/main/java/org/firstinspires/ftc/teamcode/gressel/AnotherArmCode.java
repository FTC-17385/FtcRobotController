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

    // Define positions for arm
    private static final int PICKUP_POSITION = 178;  // Adjusted value
    private static final int DROPOFF_POSITION = 5348;  // Adjusted value

    // For threaded arm control
    private ArmThread armThread;
    private Lock armLock = new ReentrantLock();
    private int armTargetPosition = 0;  // Initialize to ground position
    private int currentArmPosition = 0;  // Track the current arm position

    @Override
    public void init() {
        // Initialize motors from hardware map
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");

        // Reset motor modes
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Initialize current arm position to ground
        currentArmPosition = 0;

        // Start arm control thread
        armThread = new ArmThread(armMotor);  // Pass armMotor reference to the ArmThread
        armThread.start();
    }

    @Override
    public void loop() {
        // Drive control
        // ... (same as your previous code)

        // Arm control
        armLock.lock();
        try {
            if (gamepad1.a) {
                armTargetPosition = PICKUP_POSITION;
            } else if (gamepad1.y) {
                armTargetPosition = DROPOFF_POSITION;
            }
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
        private DcMotor localArmMotor;
        private boolean runFlag = true;

        public ArmThread(DcMotor armMotor) {
            this.localArmMotor = armMotor;
        }

        @Override
        public void run() {
            while (runFlag) {
                armLock.lock();
                try {
                    if (currentArmPosition != armTargetPosition) {
                        localArmMotor.setTargetPosition(armTargetPosition);
                        localArmMotor.setPower(0.2);  // Power to use when moving to the target position
                        currentArmPosition = armTargetPosition;
                    }
                } finally {
                    armLock.unlock();
                }

                // Sleep for a short period
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
