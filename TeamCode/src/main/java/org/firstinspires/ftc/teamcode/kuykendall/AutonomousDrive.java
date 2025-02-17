package org.firstinspires.ftc.teamcode.kuykendall;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
        import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
        import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

        import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.ElapsedTime;

        import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
        import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
        import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
        import org.firstinspires.ftc.vision.VisionPortal;
        import org.firstinspires.ftc.vision.tfod.TfodProcessor;

        import java.util.List;
        import java.util.Locale;
/*
 * This OpMode illustrates the basics of TensorFlow Object Detection, using
 * the easiest way.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "Auto Vision Drive", group = "Auto")
public class AutonomousDrive extends LinearOpMode {

    private boolean DEBUG = false;
    private FtcDashboard dashboard; // Add this line for FTC Dashboard

    //private double MOVE_INDEX = 1;
    private double MOVE_MIDDLE = 3.4;
    private double MOVE_LEFT = 1.5;
    private double MOVE_RIGHT = 1.5;
    private double MOVE_BACK = 2.8;
    private double MOVE_SMB = 0.3;
    private double MOVE_SMF = -1;

    static final double     FORWARD_SPEED = -0.2;
    static final double     TURN_SPEED    = -0.2;

    private ElapsedTime runtime = new ElapsedTime();

    // Wrist positions
    public static double PICKUP_POSITION = 0.78;
    public static double DROPOFF_POSITION = 0.15;

    // Gripper positions
    public static double LEFT_SERVO_OPEN = 0.35;
    public static double LEFT_SERVO_CLOSE = 0.0;
    public static double RIGHT_SERVO_OPEN = 0.25;
    public static double RIGHT_SERVO_CLOSE = 0.3;

    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;
    private DcMotor armMotor = null;

    // Servos
    private Servo leftServo;
    private Servo rightServo;
    private Servo wristServo;


    private enum PixelPosition {
        UNKNOWN,
        LEFT,
        MIDDLE,
        RIGHT
    }


    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_cube_props-102823.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/model_20231015_125021.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "Red Square",
            "Blue Square"
    };


    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    private VisionPortal.Builder visionPortalBuilder;

    // Initialize threading elements
    private ExecutorService executor = Executors.newFixedThreadPool(2);
    private Object detectLock = new Object();

    @Override
    public void runOpMode() {

        // Initialize FTC Dashboard
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();


        double  drive           = 0;        // Desired forward power/speed (-1 to +1) +ve is forward
        double  turn            = 0;        // Desired turning power/speed (-1 to +1) +ve is CounterClockwise

        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRight");

        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        wristServo = hardwareMap.servo.get("wristServo");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        wristServo.setPosition(PICKUP_POSITION);
        leftServo.setPosition(LEFT_SERVO_OPEN);
        rightServo.setPosition(RIGHT_SERVO_CLOSE);

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);


       // initTfod();

        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        wristServo = hardwareMap.servo.get("wristServo");
        wristServo.setPosition(.05);
        leftServo.setPosition(.35); // Adjust the position value as needed
        rightServo.setPosition(0.25); // Adjust the position value as needed

        justWait(1000);

        leftServo.setPosition(0.35); // Adjust the position value as needed
        rightServo.setPosition(0.25); // Adjust the position value as needed

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

      //  armMotor.setTargetPosition(0);
        //armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //armMotor.setPower(1);
       // sleep(3000);
        initTfod();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryTfod();

                // Replace telemetry.update() with the following to send telemetry to the FTC Dashboard:
                TelemetryPacket packet = new TelemetryPacket();
                packet.put("Objects Detected", tfod.getRecognitions().size());
                // Add any other telemetry data you want to monitor
                dashboard.sendTelemetryPacket(packet);

                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                sleep(20);

                break; // Break out of the loop after the execution of tasks
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor the easy way.
        // tfod = TfodProcessor.easyCreateWithDefaults();
        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortalBuilder = new VisionPortal.Builder();
            visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
            visionPortalBuilder.setCameraResolution(new Size(640, 480));
            visionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
            visionPortalBuilder.enableLiveView(true);
            visionPortalBuilder.addProcessors(tfod);
            visionPortal = visionPortalBuilder.build();
            //visionPortal = VisionPortal.easyCreateWithDefaults(
            //    hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);

        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, tfod);
        }

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {
        // Existing declarations...
        ElapsedTime timer = new ElapsedTime();
        PixelPosition pPosition = PixelPosition.UNKNOWN;
        boolean pixelFound = false;
        boolean pixelPlaced = false;


        List<Recognition> currentRecognitions = null; // Initialize to null

        timer.reset();
        double detectWait = 10.0;

        while (timer.seconds() < detectWait) {
            // Update the list of recognitions inside the loop
            currentRecognitions = tfod.getRecognitions();
            if (currentRecognitions != null) {
                telemetry.addData("# Objects Detected", currentRecognitions.size());
                // Rest of your object detection logic...
            } else {
                telemetry.addData("# Objects Detected", 0);
            }

            if (currentRecognitions.size() > 0) {
                for (Recognition recognition : currentRecognitions) {

                    if(Arrays.asList(LABELS).contains(recognition.getLabel())) {
                        double x = (recognition.getLeft() + recognition.getRight()) / 2;
                        double y = (recognition.getTop() + recognition.getBottom()) / 2;

                        // put your left, right, middle logic using the x value
                        if (x < 100) {
                            pPosition = PixelPosition.LEFT;
                            telemetry.addData("Pixel Position: ", "Left Line");
                            telemetry.update();
                            justWait(1000);
                        } else if (x > 600) {
                            pPosition = PixelPosition.RIGHT;
                            telemetry.addData("Pixel Position: ", "Right Line");
                            telemetry.update();
                            justWait(1000);
                        } else {
                            pPosition = PixelPosition.MIDDLE;
                            telemetry.addData("Pixel Position: ", "Middle Line");
                            telemetry.update();
                            justWait(1000);
                        }
                    }
                }
            }
        }


            switch(pPosition) {
                case LEFT:
                    moveRobot("Mid Lines", MOVE_BACK);
                    wristServo.setPosition(PICKUP_POSITION);
                    turnRobot("left", MOVE_LEFT);
                    moveRobot("Small Back", MOVE_SMB);
                    armMotor.setTargetPosition(0);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1);
                    sleep(3000);
                    telemetry.addData("Turn: Left", "");
                    telemetry.update();
                    justWait(1000);
                    sleep(500);
                    leftServo.setPosition(LEFT_SERVO_CLOSE);
                    rightServo.setPosition(RIGHT_SERVO_OPEN);
                    sleep(1000);
                    armMotor.setTargetPosition(-2000);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1);
                    break;
                case RIGHT:
                    sleep(3000);
                    moveRobot("Mid Lines", MOVE_BACK);
                    wristServo.setPosition(PICKUP_POSITION);
                    turnRobot("right", MOVE_RIGHT);
                    moveRobot("Small Back", MOVE_SMB);
                    armMotor.setTargetPosition(0);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1);
                    telemetry.addData("Turn: Right", "");
                    telemetry.update();
                    justWait(1000);
                    sleep(500);
                    leftServo.setPosition(LEFT_SERVO_CLOSE);
                    rightServo.setPosition(RIGHT_SERVO_OPEN);
                    sleep(1000);
                    armMotor.setTargetPosition(-2000);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1);
                    break;
                case MIDDLE:
                    sleep(3000);
                    moveRobot("Center Line", MOVE_MIDDLE);
                    armMotor.setTargetPosition(0);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(1);
                    telemetry.addData("Move: Forward", "");
                    telemetry.update();
                    justWait(1000);
                    sleep(500);
                    moveArm();
                    break;
                case UNKNOWN:
                    break;
            }

          //  if(pixelFound) {
            //    break;
           // }
            // After processing all recognitions
            if (!pixelFound) {
                // Assume the right side is the correct one
                pPosition = PixelPosition.RIGHT;
                telemetry.addData("Assumption: ", "Right Line because nothing was detected");
                telemetry.update();
                justWait(1000);


                // Execute the default behavior since no object was detected
                defaultBehavior(); // This will execute the movement for the RIGHT side

                // Place the pixel here and set the flag
                moveArm();
                pixelPlaced = true; // Set the flag to true after placing the pixel

            }
            // After telemetry.addData lines, send the data to the FTC Dashboard:
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Objects Detected", tfod.getRecognitions().size());
            packet.put("Object Found", pixelFound ? "Square" : "None");
            // Add any other telemetry data from this method
            dashboard.sendTelemetryPacket(packet);
        }   // end for() loop
// At the end of the telemetryTfod() method, check if the pixel is placed
       // if (pixelPlaced) {
            // If the pixel is placed, stop the opMode
         //   requestOpModeStop();
        //}
    //}   // end method telemetryTfod()

    private void defaultBehavior() {
        moveRobot("Mid Lines", MOVE_BACK);
        turnRobot("right", MOVE_RIGHT);
        moveRobot("Small Back", MOVE_SMB);
        telemetry.addData("Turn: Right", "");
        telemetry.update();
        justWait(1000);
        moveArm();
    }

    private void moveRobot(String path, double time) {
        frontLeftMotor.setPower(-FORWARD_SPEED);
        backLeftMotor.setPower(-FORWARD_SPEED);
        frontRightMotor.setPower(-FORWARD_SPEED);
        backRightMotor.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "%s: %4.1f S Elapsed", path, runtime.seconds());
            telemetry.update();
        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    // tank turn
    // String direction = left or right
    private void turnRobot(String direction, double time) {
        switch(direction.toLowerCase(Locale.ROOT)) {
            case "left":
                frontLeftMotor.setPower(TURN_SPEED);
                backLeftMotor.setPower(TURN_SPEED);
                frontRightMotor.setPower(-TURN_SPEED);
                backRightMotor.setPower(-TURN_SPEED);
                runtime.reset();

                while (opModeIsActive() && (runtime.seconds() < time)) {
                    telemetry.addData("Path", "%s: %4.1f S Elapsed", direction, runtime.seconds());
                    telemetry.update();
                }
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                break;
            case "right":
                frontLeftMotor.setPower(-TURN_SPEED);
                backLeftMotor.setPower(-TURN_SPEED);
                frontRightMotor.setPower(TURN_SPEED);
                backRightMotor.setPower(TURN_SPEED);
                runtime.reset();
                while (opModeIsActive() && (runtime.seconds() < time)) {
                    telemetry.addData("Path", "%s: %4.1f S Elapsed", direction, runtime.seconds());
                    telemetry.update();
                }
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
                break;
        }
    }

    // 1 = camera position
    // 2 = pixel drop position
    private void moveArm() {
        telemetry.addData("Dropping Pixel", "");
        telemetry.update();
        wristServo.setPosition(PICKUP_POSITION);
        sleep(1000);
        leftServo.setPosition(LEFT_SERVO_CLOSE);
        rightServo.setPosition(RIGHT_SERVO_OPEN);
        telemetry.addData("Pixel Dropped", "");
        telemetry.update();
        sleep(500);
        armMotor.setTargetPosition(-1000);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(1);
    }


    private void justWait(int milliseconds) {
        double currTime = getRuntime();
        double waitUntil = currTime + (double)(milliseconds/1000);
        while (getRuntime() < waitUntil) {

        }
    }

}   // end class
