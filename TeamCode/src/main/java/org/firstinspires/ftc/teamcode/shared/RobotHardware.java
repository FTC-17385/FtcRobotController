package org.firstinspires.ftc.teamcode.shared;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class RobotHardware {
    private LinearOpMode myOpMode = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor armMotor = null;
    private Servo leftServo  = null;
    private Servo rightServo  = null;
    private Servo wristServo = null;
    private ElapsedTime runtime = new ElapsedTime();
    public static double PICKUP_POSITION = 0.16;
    public static double DROPOFF_POSITION = 0.78;
    public static double WRIST_START_POSITION = 0.05;

    // Gripper positions
    public static double LEFT_SERVO_OPEN = 0.35;
    public static double LEFT_SERVO_CLOSE = 0.0;
    public static double RIGHT_SERVO_OPEN = 0.25;
    public static double RIGHT_SERVO_CLOSE = 0.3;
    static final double     FORWARD_SPEED = -0.2;
    static final double     TURN_SPEED    = -0.2;
    static final double     COUNTS_PER_MOTOR_REV    = 484.5 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 1.889 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    //arm encoder values
    public static int PICKUP_POSITION_ENCODER = 0;
    public static int DROPOFF_POSITION_ENCODER = -3630;
    public static double driveScale = .3;
    public static double strafeScale = .5;
    public static double rotateScale = .3;

    // 180 turn constants
    public static double FAST_ROTATE_SPEED = 1.0;
    public static long TURN_180_TIME_MS = 333;
    private boolean isTurning180 = false;
    private long turnStartTime = 0;

    // Variables from planetest
    private Servo planeServo;
    private TelemetryPacket packet;
    public static double Launch_POSITION = 0.8;
    public static double HOLD_POSITION = 0.3;
    public RobotHardware (LinearOpMode opmode) {myOpMode = opmode; }

    public void init() {
        frontLeft = myOpMode.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = myOpMode.hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = myOpMode.hardwareMap.get(DcMotor.class, "backLeft");
        backRight = myOpMode.hardwareMap.get(DcMotor.class, "backRight");
        planeServo = myOpMode.hardwareMap.get(Servo.class, "planeServo");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        armMotor = myOpMode.hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wristServo = myOpMode.hardwareMap.get(Servo.class, "wristServo");
        leftServo = myOpMode.hardwareMap.get(Servo.class, "leftServo");
        rightServo = myOpMode.hardwareMap.get(Servo.class, "rightServo");
        wristServo.setPosition(WRIST_START_POSITION);
    }

    public void driveRobot(double time){
        frontLeft.setPower(-FORWARD_SPEED);
        backLeft.setPower(-FORWARD_SPEED);
        frontRight.setPower(-FORWARD_SPEED);
        backRight.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (myOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            myOpMode.telemetry.addData("Path", "%s: %4.1f S Elapsed", "", runtime.seconds());
            myOpMode.telemetry.update();
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);

    }

    public void turnRobotRight(double speed, double distance, double timeout){
    }
    public void driveRobot(double speed, double distance, double timeout){
        int newFrontRightTarget = frontRight.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackRightTarget = backRight.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newFrontLeftTarget = frontLeft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackLeftTarget = backLeft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        frontRight.setTargetPosition(newFrontRightTarget);
        backRight.setTargetPosition(newBackRightTarget);
        frontLeft.setTargetPosition(newFrontLeftTarget);
        backLeft.setTargetPosition(newBackLeftTarget);

        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        runtime.reset();
        frontRight.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(speed));
        frontLeft.setPower(Math.abs(speed));
        backLeft.setPower(Math.abs(speed));

        while ((runtime.seconds() < timeout) &&
                (frontRight.isBusy() && backRight.isBusy() && frontLeft.isBusy() && backLeft.isBusy()));

        myOpMode.telemetry.addData("Running to",  " %7d :%7d :%7d :%7d", newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
        myOpMode.telemetry.addData("Currently at",  " at %7d :%7d",
                frontRight.getCurrentPosition(), backRight.getCurrentPosition(), frontLeft.getCurrentPosition(), backLeft.getCurrentPosition());
        myOpMode.telemetry.update();

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}


