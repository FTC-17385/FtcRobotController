package org.firstinspires.ftc.teamcode.Bot2;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.shared.MotionHardware2;

@TeleOp
public class DrivePro extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Thread liftThread;
    private Thread intakeThread;
    private Thread placementThread;
    private Thread planeThread;
    MotionHardware2 robot = new MotionHardware2(this);

    @Override
    public void runOpMode() {

        robot.init();
        initializeLiftThread();
        initializeIntakeThread();
        initializePlacementThread();
        initializePlaneThread();

        waitForStart();

        liftThread.start();
        intakeThread.start();
        placementThread.start();
        planeThread.start();

        telemetry.addData("Mode", "waiting");

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator; //
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);
        }
    }
    private void initializePlaneThread() {
        planeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    //directions
                }
            }
        });
    }
    private void initializeLiftThread() {
        liftThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    //directions
                }
            }
        });
    }
    private void initializePlacementThread() {
        placementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    //directions
                }
            }
        });
    }
    private void initializeIntakeThread() {
        intakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (gamepad1.a) {

                    }
                    //directions
                }
            }
        });
    }
}



