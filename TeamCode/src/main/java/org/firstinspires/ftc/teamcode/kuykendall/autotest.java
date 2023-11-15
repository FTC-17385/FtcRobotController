package org.firstinspires.ftc.teamcode.kuykendall;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.List;
import java.util.Locale;
@Disabled

@Autonomous(name = "autotest", group = "Concept")
public class autotest extends LinearOpMode {

    private static final double FORWARD_SPEED = 0.2;
    private static final double TURN_SPEED = 0.2;
    private static final String TFOD_MODEL_ASSET = "model_cube_props-102823.tflite";
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/model_20231015_125021.tflite";
    private static final String[] LABELS = { "Red Square", "Blue Square" };
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private VisionPortal visionPortal;


    private enum PixelPosition {
        UNKNOWN,
        LEFT,
        MIDDLE,
        RIGHT
    }

    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private Servo leftServo, rightServo, wristServo;
    private FtcDashboard dashboard;
    private TfodProcessor tfod;

    @Override
    public void runOpMode() {
        // Initialization
        initRobot();

        waitForStart();

        if (opModeIsActive()) {
            // Detect and act based on pixel position
            actBasedOnPixelPosition();
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initRobot() {
        // Initialize FTC Dashboard
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRight");

        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        wristServo = hardwareMap.servo.get("wristServo");

        wristServo.setPosition(0.12);
        leftServo.setPosition(0.35);
        rightServo.setPosition(0.25);

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize TFOD
        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);
        }
    }

    private void actBasedOnPixelPosition() {
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        PixelPosition pPosition = PixelPosition.UNKNOWN;

        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2;

            if (x < 214) {
                pPosition = PixelPosition.LEFT;
            } else if (x > 428) {
                pPosition = PixelPosition.RIGHT;
            } else {
                pPosition = PixelPosition.MIDDLE;
            }

            switch (pPosition) {
                case LEFT:
                    // Move backward and then turn right
                    moveRobot(2);
                    turnRobot("right", 0.5); // Assuming 0.5 seconds is approximately 30 degrees
                    break;
                case RIGHT:
                    // Move backward and then turn left
                    moveRobot(2);
                    turnRobot("left", 0.5); // Assuming 0.5 seconds is approximately 30 degrees
                    break;
                case MIDDLE:
                    // Just move backward
                    moveRobot(2);
                    break;
                case UNKNOWN:
                    break;
            }

            break;
        }
    }

    private void moveRobot(double time) {
        frontLeftMotor.setPower(-FORWARD_SPEED);
        backLeftMotor.setPower(-FORWARD_SPEED);
        frontRightMotor.setPower(-FORWARD_SPEED);
        backRightMotor.setPower(-FORWARD_SPEED);

        sleep((long) (time * 1000));

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    private void turnRobot(String direction, double time) {
        if ("left".equalsIgnoreCase(direction)) {
            frontLeftMotor.setPower(TURN_SPEED);
            backLeftMotor.setPower(TURN_SPEED);
            frontRightMotor.setPower(-TURN_SPEED);
            backRightMotor.setPower(-TURN_SPEED);
        } else if ("right".equalsIgnoreCase(direction)) {
            frontLeftMotor.setPower(-TURN_SPEED);
            backLeftMotor.setPower(-TURN_SPEED);
            frontRightMotor.setPower(TURN_SPEED);
            backRightMotor.setPower(TURN_SPEED);
        }

        sleep((long) (time * 1000));

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    // Additional methods as per your requirements
    private void moveArm() {
        wristServo.setPosition(0.15);
        leftServo.setPosition(0.35);
        rightServo.setPosition(0.25);
    }
}
