package org.firstinspires.ftc.teamcode.kuykendall;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="BeastDrive", group="TeleOp")
@Config
public class BeastDrive extends OpMode {

    // Drivetrain motors
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // Arm motor
    private DcMotor armMotor;

    // Wrist and gripper servos
    private Servo wristServo, leftServo, rightServo;

    // FTC Dashboard
    private FtcDashboard dashboard;

    // Wrist positions
    public static double PICKUP_POSITION = 0.7;
    public static double DROPOFF_POSITION = 0.15;

    // Gripper positions
    public static double LEFT_SERVO_OPEN = 0.35;
    public static double LEFT_SERVO_CLOSE = 0.0;
    public static double RIGHT_SERVO_OPEN = 0.25;
    public static double RIGHT_SERVO_CLOSE = 0.3;
    //arm encoder start
    public static int PICKUP_POSITION_ENCODER = 0; // dummy value, adjust through testing
    public static int DROPOFF_POSITION_ENCODER = -3630; // dummy value, adjust through testing

    //driving values
    public static double driveScale = 1.1;
    public static double strafeScale = 1.0;
    public static double rotateScale = 1.0;

    @Override
    public void init() {
        // Drivetrain initialization
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        // Arm initialization
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Wrist and gripper initialization
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        // FTC Dashboard initialization
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
    }

    // Power scaling factors for each motor
    public static double FRONT_LEFT_SCALE = 0.5;
    public static double FRONT_RIGHT_SCALE = 0.5;
    public static double BACK_LEFT_SCALE = 0.5;  // You can start with a value less than 1.0 for the lagging motors and adjust as needed
    public static double BACK_RIGHT_SCALE = 0.5;  // Adjust as per your observations

    @Override
    public void loop() {
        // Drivetrain logic
        double drive = gamepad1.left_stick_x * driveScale;
        double strafe = -gamepad1.left_stick_y * strafeScale;
        double rotate = gamepad1.right_stick_x * rotateScale;

        double frontLeftPower = drive + strafe + rotate;  // Physically back left
        double frontRightPower = drive - strafe - rotate;
        double backLeftPower = drive - strafe + rotate;   // Physically front left
        double backRightPower = drive + strafe - rotate;

        // Exponential scaling
        frontLeftPower = Math.signum(frontLeftPower) * Math.pow(Math.abs(frontLeftPower), 2);
        frontRightPower = Math.signum(frontRightPower) * Math.pow(Math.abs(frontRightPower), 2);
        backLeftPower = Math.signum(backLeftPower) * Math.pow(Math.abs(backLeftPower), 2);
        backRightPower = Math.signum(backRightPower) * Math.pow(Math.abs(backRightPower), 2);

        // Scale the power
        frontLeftPower *= FRONT_LEFT_SCALE;
        frontRightPower *= FRONT_RIGHT_SCALE;
        backLeftPower *= BACK_LEFT_SCALE;
        backRightPower *= BACK_RIGHT_SCALE;

        // Clamp the power values to be within the range [-0.5, 0.5]
        frontLeftPower = Range.clip(frontLeftPower, -.5, .5);
        frontRightPower = Range.clip(frontRightPower, -.5, .5);
        backLeftPower = Range.clip(backLeftPower, -.5, .5);
        backRightPower = Range.clip(backRightPower, -.5, .5);

        // Set the power to the motors
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);


    // Arm logic
        if (gamepad1.a) {
            armMotor.setTargetPosition(PICKUP_POSITION_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(1.0);
        } else if (gamepad1.y) {
            armMotor.setTargetPosition(DROPOFF_POSITION_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(1.0);
        } else {
            armMotor.setPower(0.0);
        }
        // Wrist logic
        if (gamepad1.left_bumper) {
            wristServo.setPosition(PICKUP_POSITION);
        } else if (gamepad1.right_bumper) {
            wristServo.setPosition(DROPOFF_POSITION);
        }

        // Gripper logic
        if (gamepad1.x) {
            leftServo.setPosition(LEFT_SERVO_OPEN);
            rightServo.setPosition(RIGHT_SERVO_OPEN);
        } else if (gamepad1.b) {
            leftServo.setPosition(LEFT_SERVO_CLOSE);
            rightServo.setPosition(RIGHT_SERVO_CLOSE);
        }
        // Resetting the armMotor Encoder
        if (gamepad1.dpad_left) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Front Left Power", frontLeftPower);
        packet.put("Front Right Power", frontRightPower);
        packet.put("Back Left Power", backLeftPower);
        packet.put("Back Right Power", backRightPower);
        packet.put("Arm Power", armMotor.getPower());
        packet.put("Wrist Servo Position", wristServo.getPosition() == PICKUP_POSITION ? "Pickup" : "Dropoff");
        packet.put("Gripper", leftServo.getPosition() == LEFT_SERVO_OPEN ? "Open" : "Closed");
        packet.put("Arm Encoder Value", armMotor.getCurrentPosition()); // Added this line
        dashboard.sendTelemetryPacket(packet);
    }
}