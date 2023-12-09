package org.firstinspires.ftc.teamcode.shared;

import static android.os.SystemClock.sleep;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Scott.VisionI1.DEBUG;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Locale;
public class MotionHardware2 {
    public static boolean DEBUG = false;
    public LinearOpMode myOpMode = null;
    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor rightLeadScrew;
    public DcMotor leftLeadScrew;
    public DcMotor linearExtension;
    public Servo leftIntakeLifter;
    public Servo rightIntakeLifter;
    public Servo intakeWrist;
    public Servo intake;
    public Servo bucketWrist;
    public Servo leftClimb;
    public Servo rightClimb;
    public Servo planeServo;

    //Variables
    private static final double SQUARE_SIZE = 23.0;
    public static final double FORWARD_SPEED = 0.4;
    public static final double TURN_SPEED = 0.4;
    private static final double MAX_SPEED = 1;
    public static double detectWait = 6.0;
    public static double wristDown = 1;
    public static double wristUp = -1;

    public enum Direction {
        RIGHT,
        LEFT
    }

    static final double COUNTS_PER_MOTOR_REV = 537.7;    // eg: goBilda Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 4;    //3.778 ;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public MotionHardware2(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    public void init() {

        frontLeftMotor = myOpMode.hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = myOpMode.hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = myOpMode.hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = myOpMode.hardwareMap.get(DcMotor.class, "backRightMotor");
        rightLeadScrew = myOpMode.hardwareMap.get(DcMotor.class, "rightLeadScrew");
        leftLeadScrew = myOpMode.hardwareMap.get(DcMotor.class, "leftLeadScrew");
        linearExtension = myOpMode.hardwareMap.get(DcMotor.class, "linearExtension");

        //frontLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        //frontRightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        //backLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        //backRightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        rightLeadScrew.setDirection(DcMotor.Direction.REVERSE);
        leftLeadScrew.setDirection(DcMotor.Direction.REVERSE);
        linearExtension.setDirection(DcMotor.Direction.REVERSE);


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLeadScrew.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLeadScrew.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearExtension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLeadScrew.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLeadScrew.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLeadScrew.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLeadScrew.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //TODO Once wrist/gripper is fixed move pixel load step to new function
        leftIntakeLifter = myOpMode.hardwareMap.get(Servo.class, "leftGripper");
        rightIntakeLifter = myOpMode.hardwareMap.get(Servo.class, "rightGripper");
        intakeWrist = myOpMode.hardwareMap.get(Servo.class, "intakeWrist");
        intake = myOpMode.hardwareMap.get(Servo.class, "wristServo");
        bucketWrist = myOpMode.hardwareMap.get(Servo.class, "bucketWrist");
        leftClimb = myOpMode.hardwareMap.get(Servo.class, "leftClimb");
        rightClimb = myOpMode.hardwareMap.get(Servo.class, "rightClimb");
        planeServo = myOpMode.hardwareMap.get(Servo.class, "planeServo");


    }

    public void moveRobot(String path, double time) {

        switch (path.toLowerCase(Locale.ROOT)) {
            case "back":
                frontLeftMotor.setPower(FORWARD_SPEED);
                backLeftMotor.setPower(FORWARD_SPEED);
                frontRightMotor.setPower(FORWARD_SPEED);
                backRightMotor.setPower(FORWARD_SPEED);
                runtime.reset();
                while (myOpMode.opModeIsActive() && (runtime.seconds() < time)) {
                    // telemetry.addData("Path", "%s: %4.1f S Elapsed", path, runtime.seconds());
                    //telemetry.update();
                }

                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                break;
            default:
                frontLeftMotor.setPower(-FORWARD_SPEED);
                backLeftMotor.setPower(-FORWARD_SPEED);
                frontRightMotor.setPower(-FORWARD_SPEED);
                backRightMotor.setPower(-FORWARD_SPEED);
                runtime.reset();
                while (myOpMode.opModeIsActive() && (runtime.seconds() < time)) {
                    myOpMode.telemetry.addData("Path", "%s: %4.1f S Elapsed", path, runtime.seconds());
                    myOpMode.telemetry.update();
                }

                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
        }
    }
    public void moveRobot(double speed, double distance, double timeoutS) {

        //frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //newLeftTarget = leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        int newFrontLeftTarget = frontLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackLeftTarget = backLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newFrontRightTarget = frontRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackRightTarget = backRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        frontLeftMotor.setTargetPosition(newFrontLeftTarget);
        backLeftMotor.setTargetPosition(newBackLeftTarget);
        frontRightMotor.setTargetPosition(newFrontRightTarget);
        backRightMotor.setTargetPosition(newBackLeftTarget);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        frontRightMotor.setPower(Math.abs(speed));
        frontLeftMotor.setPower(Math.abs(speed));
        backRightMotor.setPower(Math.abs(speed));
        backLeftMotor.setPower(Math.abs(speed));

        while (myOpMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy())) {

            // Display it for the driver.
            myOpMode.telemetry.addData("Running to",  " %7d :%7d :%7d :%7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
            myOpMode.telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                    frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition(), backRightMotor.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        myOpMode.telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition(), backRightMotor.getCurrentPosition());
        myOpMode.telemetry.update();
        sleep(1000);
    }
    public void turnRobot(MotionHardware.Direction direction, double distance, double speed, double timeoutS) {



        int newFrontLeftTarget = (direction == MotionHardware.Direction.LEFT) ?
                frontLeftMotor.getCurrentPosition() + (int)((-distance) * COUNTS_PER_INCH) :
                frontLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackLeftTarget = (direction == MotionHardware.Direction.LEFT) ?
                backLeftMotor.getCurrentPosition() + (int)((-distance) * COUNTS_PER_INCH) :
                backLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newFrontRightTarget = (direction == MotionHardware.Direction.LEFT) ?
                frontRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH) :
                frontRightMotor.getCurrentPosition() + (int)((-distance) * COUNTS_PER_INCH);
        int newBackRightTarget = (direction == MotionHardware.Direction.LEFT) ?
                backRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH) :
                backRightMotor.getCurrentPosition() + (int)((-distance) * COUNTS_PER_INCH);

        frontLeftMotor.setTargetPosition(newFrontLeftTarget);
        backLeftMotor.setTargetPosition(newBackLeftTarget);
        frontRightMotor.setTargetPosition(newFrontRightTarget);
        backRightMotor.setTargetPosition(newBackRightTarget);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        frontRightMotor.setPower(Math.abs(speed));
        frontLeftMotor.setPower(Math.abs(speed));
        backRightMotor.setPower(Math.abs(speed));
        backLeftMotor.setPower(Math.abs(speed));

        switch (direction) {
            case LEFT:
                while (myOpMode.opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy())) {

                    // Display it for the driver.
                    myOpMode.telemetry.addData("Running to",  " %7d :%7d :%7d :%7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                    myOpMode.telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                            frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition(), backRightMotor.getCurrentPosition());
                    myOpMode.telemetry.update();
                }
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                break;
            case RIGHT:
                while (myOpMode.opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy())) {

                    // Display it for the driver.
                    myOpMode.telemetry.addData("Running to",  " %7d :%7d :%7d :%7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                    myOpMode.telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                            frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition(), backRightMotor.getCurrentPosition());
                    myOpMode.telemetry.update();
                }
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                break;

        }
    }
    public void strafe(double distance, double speed, MotionHardware.Direction direction, double timeoutS) {
        int newFrontLeftTarget = frontLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackLeftTarget = backLeftMotor.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
        int newFrontRightTarget = frontRightMotor.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
        int newBackRightTarget = backRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        frontLeftMotor.setTargetPosition(newFrontLeftTarget);
        backLeftMotor.setTargetPosition(newBackLeftTarget);
        frontRightMotor.setTargetPosition(newFrontRightTarget);
        backLeftMotor.setTargetPosition(newBackLeftTarget);

        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        runtime.reset();
        frontRightMotor.setPower(Math.abs(speed));
        frontLeftMotor.setPower(Math.abs(speed));
        backRightMotor.setPower(Math.abs(speed));
        backLeftMotor.setPower(Math.abs(speed));

        while (myOpMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy())) {

            // Display it for the driver.
            myOpMode.telemetry.addData("Running to",  " %7d :%7d :%7d :%7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
            myOpMode.telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                    frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition(), backRightMotor.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        myOpMode.telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                frontLeftMotor.getCurrentPosition(), frontRightMotor.getCurrentPosition(), backLeftMotor.getCurrentPosition(), backRightMotor.getCurrentPosition());
        myOpMode.telemetry.update();
        sleep(1000);
    }
    public void stopRobot() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }
    private void debugWait() {
        if (DEBUG) {
            sleep(5000);
        } else {
            sleep(1000);
        }
    }
}

