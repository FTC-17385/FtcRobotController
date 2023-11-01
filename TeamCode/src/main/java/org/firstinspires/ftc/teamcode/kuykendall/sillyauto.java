package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Silly Auto", group="Autonomous")
public class sillyauto extends LinearOpMode {

    // Gripper servos
    private Servo leftServo, rightServo, wristServo;

    // Gripper positions
    public static double LEFT_SERVO_CLOSE = 0.35;
    public static double RIGHT_SERVO_CLOSE = 0.25;

    // Wrist positions
    public static double DROPOFF_POSITION = 0.15;

    @Override
    public void runOpMode() {
        // Gripper initialization
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        // Wrist servo initialization
        wristServo = hardwareMap.get(Servo.class, "wristServo");

        // Close the gripper during initialization
        leftServo.setPosition(LEFT_SERVO_CLOSE);
        rightServo.setPosition(RIGHT_SERVO_CLOSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Set the wrist servo to DROPOFF_POSITION after pressing PLAY
        wristServo.setPosition(DROPOFF_POSITION);

        // Keep the OpMode running until manually stopped or autonomous period is over
        while (opModeIsActive()) {
            // You can add any telemetry or other commands you'd like to run continuously here
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
