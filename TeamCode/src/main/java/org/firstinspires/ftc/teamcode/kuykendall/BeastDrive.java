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

    //arm encoder values
    public static int PICKUP_POSITION_ENCODER = 0;
    public static int DROPOFF_POSITION_ENCODER = -3630;

    //driving scales
    public static double driveScale = .3;
    public static double strafeScale = .4;
    public static double rotateScale = .4;

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

    @Override
    public void loop() {
        // Introduce separate scales for forward and backward driving
        double forwardDriveScale = .5; // Set this to your desired scale for forward motion
        double backwardDriveScale = .3; // Set this to your desired scale for backward motion

        // Drivetrain logic
        double rawDrive = -gamepad1.left_stick_y;
        double drive;
        if (rawDrive > 0) { // Forward
            drive = rawDrive * forwardDriveScale;
        } else { // Backward or no motion
            drive = rawDrive * backwardDriveScale;
        }

        double strafe = gamepad1.left_stick_x * strafeScale;
        double rotate = gamepad1.right_stick_x * rotateScale;

        double frontLeftPower = drive + strafe + rotate;
        double frontRightPower = drive - strafe - rotate;
        double backLeftPower = drive - strafe + rotate;
        double backRightPower = drive + strafe - rotate;

        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));
        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        // Arm logic
        // Arm logic
        if (gamepad1.dpad_down) {
            armMotor.setPower(1.0); // Negative power to move the arm down
        } else if (gamepad1.a && !gamepad1.dpad_down) {
            armMotor.setTargetPosition(PICKUP_POSITION_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(1.0);
        } else if (gamepad1.y && !gamepad1.dpad_down) {
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
        packet.put("Front Left Power", frontLeft.getPower());
        packet.put("Front Right Power", frontRight.getPower());
        packet.put("Back Left Power", backLeft.getPower());
        packet.put("Back Right Power", backRight.getPower());
        packet.put("Arm Power", armMotor.getPower());
        packet.put("Wrist Servo Position", wristServo.getPosition() == PICKUP_POSITION ? "Pickup" : "Dropoff");
        packet.put("Gripper", leftServo.getPosition() == LEFT_SERVO_OPEN ? "Open" : "Closed");
        packet.put("Arm Encoder Value", armMotor.getCurrentPosition());
        dashboard.sendTelemetryPacket(packet);
    }
}
