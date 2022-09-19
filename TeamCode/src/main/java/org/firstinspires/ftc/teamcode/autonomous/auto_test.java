package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

import java.util.List;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous (name = "Auto Test")

public class auto_test extends LinearOpMode {

    private DcMotor RightDrive;
    private DcMotor LeftDrive;
    //private DcMotor Arm;
    //private DcMotor Intake;

    private Detector tfDetector = null;
    //Create elapsed time variable and an instance of elapsed time
    private ElapsedTime runtime = new ElapsedTime();

    private static String MODEL_FILE_NAME = "model_unquant.tflite";
    private static String LABEL_FILE_NAME = "labels.txt";
    private static Classifier.Model MODEL_TYPE = Classifier.Model.FLOAT_EFFICIENTNET;

    //Convert from the counts per revolution of the encoder to counts per inch
    static final double HD_COUNTS_PER_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 20.15293;
    static final double WHEEL_CIRCUMFERENCE_MM = 90 * Math.PI;
    static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
    static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;



    // Drive function with 3 parameters
    private void drive(double power, double leftInches, double rightInches) {
        int rightTarget;
        int leftTarget;

        if (opModeIsActive()) {
            // Create target positions
            rightTarget = RightDrive.getCurrentPosition() + (int) (rightInches * DRIVE_COUNTS_PER_IN);
            leftTarget = LeftDrive.getCurrentPosition() + (int) (leftInches * DRIVE_COUNTS_PER_IN);

            // set target position
            LeftDrive.setTargetPosition(leftTarget);
            RightDrive.setTargetPosition(rightTarget);

            //switch to run to position mode
            LeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //run to position at the desiginated power
            LeftDrive.setPower(power);
            RightDrive.setPower(power);

            // wait until both motors are no longer busy running to position
            while (opModeIsActive() && (LeftDrive.isBusy() || RightDrive.isBusy())) {
            }

            // set motor power back to 0
            LeftDrive.setPower(0);
            RightDrive.setPower(0);

        }
    }



    @Override
    public void runOpMode() {

        RightDrive = hardwareMap.get(DcMotor.class, "RightMotor");
        LeftDrive = hardwareMap.get(DcMotor.class, "LeftMotor");
        //Arm = hardwareMap.get(DcMotor.class, "Arm");
        //Intake = hardwareMap.get(DcMotor.class, "Intake");


        LeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);


        try {
            try {
                tfDetector = new Detector(MODEL_TYPE, MODEL_FILE_NAME, LABEL_FILE_NAME, hardwareMap.appContext, telemetry);
                tfDetector.activate();
            } catch (Exception ex) {
                telemetry.addData("Error", String.format("Unable to initialize Detector. %s", ex.getMessage()));
                sleep(3000);
                return;
            }
            telemetry.addData("Detector", "Ready");
            telemetry.update();
            // Wait for the game to start (driver presses PLAY)
            waitForStart();
            runtime.reset();

            while (opModeIsActive()) {
                List<Classifier.Recognition> results = tfDetector.getLastResults();
                if (results == null || results.size() == 0) {
                    telemetry.addData("Info", "No results");
                } else {
                    for (Classifier.Recognition r : results) {
                        String item = String.format("%s", r.getTitle());
                        telemetry.addData("Found", item);
                        if (item.equals("A")){
                            drive(1.0, 90, 90);

                        }//else if (r.getTitle())
                    }
                }
                telemetry.update();
            }
        } catch (Exception ex) {
            telemetry.addData("Init Error", ex.getMessage());
            telemetry.update();
        } finally {
            if (tfDetector != null) {
                tfDetector.stopProcessing();
            }

        }

        waitForStart();





        }

    }

