package org.firstinspires.ftc.teamcode.shared;


import static android.os.SystemClock.sleep;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
////////////////////////////////////////////////////////////////////////////////////////////////////
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.lang.annotation.Annotation;
import java.util.Locale;

public class MotionHardware2 {

    public static boolean DEBUG = false;

    public GlobalConfig globalConfig = null;
    private LinearOpMode myOpMode = null;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;
    private Servo leftGripper = null;
    private Servo rightGripper = null;
    private Servo wrist = null;
    private DcMotor armMotor = null;
    private Servo dropper = null;

    // Variables

    private static final double SQUARE_SIZE = 23.0;
    public static final double FORWARD_SPEED = 0.4;
    public static final double TURN_SPEED = 0.4;
    private static final double MAX_SPEED = 1;
    public static double detectWait = 6.0;
    public static double wristStart = 0.4;
    public static double wristRight = 0;
    public static double wristLeft = 1;


    public static double LEFT_GRIPPER_OPEN = 0.9;
    public static double RIGHT_GRIPPER_OPEN = 0.1;
    public static double LEFT_GRIPPER_CLOSE = 2.2;
    public static double RIGHT_GRIPPER_CLOSE = -1.1;
    public static double WRIST_LOAD_PIXEL = -0.8;
    public static double WRIST_DROP_PIXEL = 1;
    public static double DROPPER_LOAD_PIXEL = 0.48;
    public static double DROPPER_DROP_PIXEL = -1.0;

    static final double ARM_SPEED = 1.0;
    static final int ARM_DROP_POS_LOW = -4530;
    static final int ARM_DROP_POS_HIGH = -3550;
    static final int ARM_DRIVE_POS = -800;
    static final int ARM_INTAKE_POS = 25;

    //  Set the GAIN constants to control the relationship between the measured position error, and how much power is
    //  applied to the drive motors to correct the error.
    //  Drive = Error * Gain    Make these values smaller for smoother control, or larger for a more aggressive response.
    final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
    final double TURN_GAIN   =  0.01  ;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)

    final double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_STRAFE= 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)

    public enum Direction {
        RIGHT,
        LEFT
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;    // eg: goBilda Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.5;    //3.778 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679);

    public MotionHardware2(LinearOpMode opmode) {
        myOpMode = opmode;
    }
    public MotionHardware2(LinearOpMode opmode, GlobalConfig globalConfig) {
        myOpMode = opmode;
        this.globalConfig = globalConfig;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void init() {

        frontLeftMotor = myOpMode.hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = myOpMode.hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = myOpMode.hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = myOpMode.hardwareMap.get(DcMotor.class, "backRightMotor");
        armMotor = myOpMode.hardwareMap.get(DcMotor.class, "armMotor");


        //frontLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        //frontRightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        //backLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        //backRightMotor.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        //armMotor.setDirection(DcMotor.Direction.REVERSE);



        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //TODO Once wrist/gripper is fixed move pixel load step to new function
        leftGripper = myOpMode.hardwareMap.get(Servo.class, "leftGripper");
        rightGripper = myOpMode.hardwareMap.get(Servo.class, "rightGripper");
        armMotor = myOpMode.hardwareMap.get(DcMotor.class, "armMotor");
        wrist = myOpMode.hardwareMap.servo.get("wristServo");
        dropper = myOpMode.hardwareMap.servo.get("dropperServo");

        runtime.reset();
        moveArmMotorToPosition(-830, 1);

        leftGripper.setPosition(LEFT_GRIPPER_OPEN); // Adjust the position value as needed
        rightGripper.setPosition(RIGHT_GRIPPER_OPEN); // Adjust the position value as needed

        //Dropper and Arm/Gripper mode will load a pixel into the grippers
        sleep(3000);

        leftGripper.setPosition(LEFT_GRIPPER_CLOSE); // Adjust the position value as needed
        rightGripper.setPosition(RIGHT_GRIPPER_CLOSE); // Adjust the position value as needed
        sleep(1000);

        Annotation[] annots = this.myOpMode.getClass().getAnnotations();
        for (int i = 0; i < annots.length; i++) {
            if(annots[i].toString().contains("Autonomous")) {
                GlobalConfig.isAutonomous = true;
            }
        }

        if(globalConfig.getActiveDeliveryMode() == GlobalConfig.AUTONOMOUS_DELIVERY_MODES.DROPPER &&
                GlobalConfig.isAutonomous == true) {
            dropper.setPosition(DROPPER_LOAD_PIXEL);
        }

        moveArmMotorToPosition(-10, 1);




        // Send telemetry message to indicate successful Encoder reset
        myOpMode.telemetry.addData("Starting at",  "%7d %7d %7d %7d",
                frontLeftMotor.getCurrentPosition(),
                backLeftMotor.getCurrentPosition(),
                frontRightMotor.getCurrentPosition(),
                backRightMotor.getCurrentPosition());
        myOpMode.telemetry.update();


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

    //TODO Current we go 1 inch farther than we are telling the robot.
    //TODO The back right motor always goes farther than instructed.  Removing it from the isBusy check
    /**
     * Moves robot provided distance using motor encoders.  Reverse movement is achieved
     * by passing in a negative distance value.  Motion will stop if:
     * - The motors reach their target
     * - Timeout has been exceeded
     * - opMode is cancelled/stopped
     *
     * @param  speed    speed of the motor
     * @param  distance distance in inches you want the robot to move
     * @param  timeoutS failsafe time to stop motion if motors are still busy
     */
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

    /////////////////////////////////////////////////lll////////////////////////////////////////////


    //Turn Right or Left 90 degrees
    //TODO Refactor to use new stopRobot() function
    //TODO Refactor to remove switch statement (redundant now that we are setting direction with ternary)
    public void turnRobot(Direction direction, double distance, double speed, double timeoutS) {

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        //frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        //backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        int newFrontLeftTarget = (direction == Direction.LEFT) ?
                frontLeftMotor.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH) :
                frontLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newBackLeftTarget = (direction == Direction.LEFT) ?
                backLeftMotor.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH) :
                backLeftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
        int newFrontRightTarget = (direction == Direction.LEFT) ?
                frontRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH) :
                frontRightMotor.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);
        int newBackRightTarget = (direction == Direction.LEFT) ?
                backRightMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH) :
                backRightMotor.getCurrentPosition() + (int)(-distance * COUNTS_PER_INCH);

        myOpMode.telemetry.addData("FL FR BL BR", "%7d :%7d :%7d :%7d",
                newFrontLeftTarget, newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
        debugWait();

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
                        (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy())) {

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
                        (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy())) {

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
        debugWait();
    }

    // 1 = camera position
    // 2 = pixel drop position

    private void dropProp() {
        telemetry.addData("Dropping Pixel", "");
        telemetry.update();
        leftGripper.setPosition(0.9); // Adjust the position value as needed
        rightGripper.setPosition(0.1); // Adjust the position value as needed
        telemetry.addData("Pixel Dropped", "");
        telemetry.update();
        myOpMode.sleep(1000);
    }



    /**
     * Strafe the robot in a specified direction.
     *
     * @param speed     The speed at which to strafe (e.g., 0 to 1).
     * @param direction The direction in which to strafe (0 to 360 degrees).
     *                  0   = Forward
     *                  90  = Right
     *                  180 = Back
     *                  270 = Left
     */
    public void strafeWithTime(double speed, double direction, double time) {
        double radians = Math.toRadians(direction);
        double frontLeftSpeed = speed * Math.sin(radians + Math.PI / 4);
        double frontRightSpeed = speed * Math.cos(radians + Math.PI / 4);
        double backLeftSpeed = speed * Math.cos(radians + Math.PI / 4);
        double backRightSpeed = speed * Math.sin(radians + Math.PI / 4);

        runtime.reset();

        // Set the speed for each motor
        frontLeftMotor.setPower(frontLeftSpeed);
        frontRightMotor.setPower(frontRightSpeed);
        backLeftMotor.setPower(backLeftSpeed);
        backRightMotor.setPower(backRightSpeed);

        while (myOpMode.opModeIsActive() &&
                (runtime.seconds() < time)) {
            myOpMode.telemetry.addData("Strafing: ", direction);
            myOpMode.telemetry.update();
        }
        stopRobot();
    }

    public void stopRobot() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void dropPixel() {

        switch(globalConfig.getActiveDeliveryMode()) {
            case ARM:
                wrist.setPosition(WRIST_DROP_PIXEL);

                sleep(700);

                rightGripper.setPosition(RIGHT_GRIPPER_OPEN);
                leftGripper.setPosition(LEFT_GRIPPER_OPEN);
            case DROPPER:
                dropper.setPosition(DROPPER_DROP_PIXEL);
                sleep(700);
        }

    }

    /**
     * Moves arm provided distance using motor encoders.  Reverse movement is achieved
     * by passing in a negative distance value.  Motion will stop if:
     * - The motors reach their target
     * - Timeout has been exceeded
     * - opMode is cancelled/stopped
     *
     * @param  speed    speed of the motor
     * @param  distance distance in inches you want the robot to move
     * @param  timeoutS failsafe time to stop motion if motors are still busy
     */
    public void moveArm(double speed, double distance, double timeoutS) {

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        int newArmTarget = armMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        armMotor.setTargetPosition(newArmTarget);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        armMotor.setPower(Math.abs(speed));

        while (myOpMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (armMotor.isBusy())) {

            // Display it for the driver.
            myOpMode.telemetry.addData("Running to",  " %7d", newArmTarget);
            myOpMode.telemetry.addData("Currently at",  " at %7d",
                    armMotor.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        armMotor.setPower(0);

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);
    }

    /**
     * Moves arm provided distance using motor encoders.  Reverse movement is achieved
     * by passing in a negative distance value.  Motion will stop if:
     * - The motors reach their target
     * - Timeout has been exceeded
     * - opMode is cancelled/stopped
     *
     * @param  speed    speed of the motor
     * @param  position specific encoder position
     * @param  timeoutS failsafe time to stop motion if motors are still busy
     */
    public void moveArm(double speed, int position, double timeoutS) {

        //armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //int newArmTarget = armMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

        armMotor.setTargetPosition(position);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();
        armMotor.setPower(Math.abs(speed));

        while (myOpMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (armMotor.isBusy())) {

            // Display it for the driver.
            myOpMode.telemetry.addData("Running to",  " %7d", position);
            myOpMode.telemetry.addData("Currently at",  " at %7d",
                    armMotor.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        armMotor.setPower(0);

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);
    }

    public void moveArmMotorToPosition(int position, double timeoutS) {
        ElapsedTime runtime = new ElapsedTime();

        runtime.reset();
        armMotor.setTargetPosition(position);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(ARM_SPEED); // Set your desired power
        while ((armMotor.isBusy()) && (runtime.seconds() < timeoutS)) {

            myOpMode.telemetry.addData("Running to", "%7d", position);
            myOpMode.telemetry.addData("Currently at", "%7d", armMotor.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        wrist.setPosition(0.80);
        sleep(1000);
        leftGripper.setPosition(LEFT_GRIPPER_OPEN); // Adjust the position value as needed
        rightGripper.setPosition(RIGHT_GRIPPER_OPEN); // Adjust the position value as needed
        sleep(3000);
        armMotor.setPower(0); // Stop the motor once the position is reached
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void navToTag(AprilTagDetection desiredTag, int DESIRED_DISTANCE) {
        double  drive           = 0;        // Desired forward power/speed (-1 to +1)
        double  strafe          = 0;        // Desired strafe power/speed (-1 to +1)
        double  turn            = 0;        // Desired turning power/speed (-1 to +1)

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);


        while(desiredTag.ftcPose.range > DESIRED_DISTANCE) {
            // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
            double  rangeError      = (desiredTag.ftcPose.range - DESIRED_DISTANCE);
            double  headingError    = desiredTag.ftcPose.bearing;
            double  yawError        = desiredTag.ftcPose.yaw;

            // Use the speed and turn "gains" to calculate how we want the robot to move.
            drive  = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            turn   = Range.clip(-headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;
            strafe = Range.clip(-yawError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

            myOpMode.telemetry.addData("Auto","Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);

            myOpMode.telemetry.update();

            // Apply desired axes motions to the drivetrain.
            moveRobotTag(drive, strafe, turn);
            sleep(10);
        }

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);


    }


    /**
     * Move robot according to desired axes motions
     * Used only by Vision AprilTag Navigation Mode
     * <p>
     * Positive X is forward
     * <p>
     * Positive Y is strafe left
     * <p>
     * Positive Yaw is counter-clockwise
     */
    public void moveRobotTag(double x, double y, double yaw) {

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Calculate wheel powers.
        double leftFrontPower    =  x -y -yaw;
        double rightFrontPower   =  x +y +yaw;
        double leftBackPower     =  x +y -yaw;
        double rightBackPower    =  x -y +yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send powers to the wheels.
        frontLeftMotor.setPower(leftFrontPower);
        frontRightMotor.setPower(rightFrontPower);
        backLeftMotor.setPower(leftBackPower);
        backRightMotor.setPower(rightBackPower);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void debugWait() {
        if (DEBUG) {
            sleep(5000);
        } else {
            sleep(1000);
        }
    }

}

////////////////////////////////////////////////////////////////////////////////////////////////